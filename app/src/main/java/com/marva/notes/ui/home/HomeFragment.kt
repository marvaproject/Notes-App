package com.marva.notes.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lukman.notes.R
import com.lukman.notes.databinding.FragmentHomeBinding
import com.marva.notes.data.entity.Notes
import com.marva.notes.ui.NotesViewModel
import com.marva.notes.utils.ExtentionFunctions.setActionBar
import com.marva.notes.utils.HelperFunctions
import com.marva.notes.utils.HelperFunctions.checkIfDatabaseEmpty

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val homeViewModel by viewModels<NotesViewModel>()

    private val homeAdapter by lazy { HomeAdapter() }

    private var _currentData: List<Notes>? = null

    private val currentData get() = _currentData as List<Notes>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.mHelperFunctions = HelperFunctions

        setHasOptionsMenu(true)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.apply {
            toolbarHome.setActionBar(requireActivity())

            fab.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            }
        }

        setupRecycleView()

    }

    private fun setupRecycleView() {
        binding.rvNotes.apply {
            homeViewModel.getAllData().observe(viewLifecycleOwner) {
                checkIfDatabaseEmpty(it)
                homeAdapter.setData(it)
                _currentData = it
            }
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            swipeToDelete(this)
        }
    }

//    private fun checkIsDataEmpty(data: List<Notes>) {
//        binding.apply {
//            if (data.isEmpty()) {
//                imgNoData.visibility = View.VISIBLE
//                rvNotes.visibility = View.INVISIBLE
//            } else {
//                imgNoData.visibility = View.INVISIBLE
//                rvNotes.visibility = View.VISIBLE
//            }
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val search = menu.findItem(R.id.menu_search)
        val searchAction = search.actionView as? SearchView
        searchAction?.setOnQueryTextListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_priority_high -> homeViewModel.shortByHighPriority().observe(this){ dataHigh ->
                homeAdapter.setData(dataHigh)
            }
            R.id.menu_priority_low -> homeViewModel.shortByLowPriority().observe(this){ dataLow ->
                homeAdapter.setData(dataLow)
            }
            R.id.menu_delete_all -> confirmDeleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteAll() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Everything?")
            .setMessage("Are you sure want to remove everything?")
            .setPositiveButton("Yes") { _, _ ->
                homeViewModel.deleteAllData()
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed Everything",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("No", null)
            .create()
            .show()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val querySearch = "%$query%"
        query?.let {
            homeViewModel.searchByQuery(querySearch).observe(this) {
                homeAdapter.setData(it)
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val querySearch = "%$newText%"
        newText?.let {
            homeViewModel.searchByQuery(querySearch).observe(this) {
                homeAdapter.setData(it)
            }
        }
        return true
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {

        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = currentData[position]
                homeViewModel.deleteNote(note)
                restoreData(viewHolder.itemView, note)
                Toast.makeText(requireContext(), "Successfully deleted note", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreData(view: View, note: Notes) {
        val snackbar = Snackbar.make(
            view, "Deleted ${note.title}", Snackbar.LENGTH_LONG
        )
        snackbar.setTextColor(ContextCompat.getColor(view.context, R.color.black))
        snackbar.setAction("Undo") {
            homeViewModel.insertData(note)
            Toast.makeText(requireContext(), "Successfully restored data", Toast.LENGTH_SHORT)
                .show()
        }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}