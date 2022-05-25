package com.marva.notes.ui.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lukman.notes.R
import com.lukman.notes.databinding.FragmentAddBinding
import com.marva.notes.data.entity.Notes
import com.marva.notes.ui.NotesViewModel
import com.marva.notes.utils.ExtentionFunctions.setActionBar
import com.marva.notes.utils.HelperFunctions
import com.marva.notes.utils.HelperFunctions.parseToPriority
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding

    //untuk mendapatkan akses dao
    private val addViewModel by viewModels<NotesViewModel>()

    //private var _addViewModel:NotesViewModel? = null
    //private val addViewModel get() = _addViewModel as NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        //_addViewModel = activity?.let { obtainViewModel(it) }

        binding.toolbarAdd.setActionBar(requireActivity())

        binding.spinnerPriorities.onItemSelectedListener =
            HelperFunctions.spinnerListener(requireContext(), binding.priorityIndicator)
    }

//    private fun obtainViewModel(activity: FragmentActivity): NotesViewModel {
//        val factory = ViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory)[NotesViewModel::class.java]
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        val action = menu.findItem(R.id.menu_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            insertNotes()
        }
    }

    private fun insertNotes() {
        binding.apply {
            val title = edtTitle.text.toString()
            val priority = spinnerPriorities.selectedItem.toString()
            val descriptionCompat = edtDescription.text.toString()


            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar)

            val note = Notes(
                0,
                title,
                parseToPriority(context, priority),
                descriptionCompat,
                date
            )
            if (edtTitle.text.isEmpty() || edtDescription.text.isEmpty()) {
                edtTitle.error = "Please fill field"
                edtDescription.error = "Please fill field"
            } else {
                addViewModel.insertData(note)
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
                Toast.makeText(context, "Successfully add note.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

}
