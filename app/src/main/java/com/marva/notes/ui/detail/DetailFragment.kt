package com.marva.notes.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.marva.notes.ui.MainActivity
import com.lukman.notes.R
import com.marva.notes.data.entity.Notes
import com.lukman.notes.databinding.FragmentDetailBinding
import com.marva.notes.ui.NotesViewModel
import com.marva.notes.utils.ExtentionFunctions.setActionBar

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val navArgs by navArgs<DetailFragmentArgs>()

    private val detailViewMode by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        //Todo inisalisasi variabel safeArgs dari binding
        binding.safeArgs = navArgs
        binding.toolbarDetail.setActionBar(requireActivity())

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbarDetail.apply {
            (requireActivity() as MainActivity).setSupportActionBar(this)
            setupWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                val action = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(
                    Notes(
                        navArgs.currentItem.id,
                        navArgs.currentItem.title,
                        navArgs.currentItem.priority,
                        navArgs.currentItem.description,
                        navArgs.currentItem.date
                    )
                )
                findNavController().navigate(action)
            }
            R.id.action_delete -> confirmDeleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteNote() {
        AlertDialog.Builder(context)
            .setTitle("Delete '${navArgs.currentItem.title}'?")
            .setMessage("Are you sure want to remove '${navArgs.currentItem.title}?")
            .setPositiveButton("Yes") { _, _ ->
                detailViewMode.deleteNote(navArgs.currentItem)
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                Toast.makeText(context, "Successfully delete Note", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}