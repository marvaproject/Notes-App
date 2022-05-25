package com.marva.notes.ui.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lukman.notes.R
import com.lukman.notes.databinding.FragmentUpdateBinding
import com.marva.notes.data.entity.Notes
import com.marva.notes.ui.NotesViewModel
import com.marva.notes.utils.ExtentionFunctions.setActionBar
import com.marva.notes.utils.HelperFunctions.parseToPriority
import com.marva.notes.utils.HelperFunctions.spinnerListener
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {

    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding

    private val savedArgs: UpdateFragmentArgs by navArgs()
    private val updateViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //instalasi variabel data binding yang ada di XML
        binding.saveArgs = savedArgs

        setHasOptionsMenu(true)

//        val navController = findNavController()
//        val appBarConfig = AppBarConfiguration(navController.graph)
//        binding.toolbarUpdate.setActionBar(requireActivity())

        //binding.toolbarUpdate.setActionBar()
        binding.apply {
            toolbarUpdate.setActionBar(requireActivity())
            spinnerPrioritiesUpdate.onItemSelectedListener =
                spinnerListener(context, binding.priorityIndicator)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save,menu)
        val action = menu.findItem(R.id.menu_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            //findNavController().navigate(R.id.action_updateFragment_to_detailFragment)
            Toast.makeText(context, "Note has been update", Toast.LENGTH_LONG).show()
            updateNote()
        }
    }

    private fun updateNote() {
        with(binding) {
            val title = edtTitleUpdate.text.toString()
            val priority = spinnerPrioritiesUpdate.selectedItem.toString()
            val descriptionCompat = edtDescriptionUpdate.text.toString()

            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar)

            val note = Notes(
                savedArgs.currentItem.id,
                title,
                parseToPriority(context, priority),
                descriptionCompat,
                date
            )
            if (edtTitleUpdate.text.isEmpty() || edtDescriptionUpdate.text.isEmpty()) {
                edtTitleUpdate.error = "Please fill field"
                edtDescriptionUpdate.error = "Please fill field"
            } else {
                updateViewModel.updateNote(note)
                val action = UpdateFragmentDirections.actionUpdateFragmentToDetailFragment(note)
                findNavController().navigate(action)
                Toast.makeText(context, "Successfully add note.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}