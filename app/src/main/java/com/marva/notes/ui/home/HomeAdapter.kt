package com.marva.notes.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukman.notes.databinding.RowItemNotesBinding
import com.marva.notes.data.entity.Notes

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private val listNotes = ArrayList<Notes>()

    inner class MyViewHolder(val binding: RowItemNotesBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listNotes[position]
        holder.binding.apply {
            mNotes = data
            executePendingBindings()
        }
//            tvTitle.text = data.title
//            tvDescription.text = data.description
//            tvDate.text = data.date
//
//            val it = priorityIndicator.context
//            when(data.priority){
//                Priority.HIGH -> {
//                    //untuk menentukan warna
//                    val pink = ContextCompat.getColor(it, R.color.pink)
//                    priorityIndicator.setCardBackgroundColor(pink)
//                }
//                Priority.MEDIUM -> {
//                    val yellow = ContextCompat.getColor(it, R.color.yellow)
//                    priorityIndicator.setCardBackgroundColor(yellow)
//                }
//                Priority.LOW -> {
//                    val green = ContextCompat.getColor(it, R.color.green)
//                    priorityIndicator.setCardBackgroundColor(green)
//                }
//            }
//        }
    }

    override fun getItemCount() = listNotes.size

    fun setData(data: List<Notes>?){
        if (data == null) return
        val diffCallback = DiffCallback(listNotes, data)
        val diffCallbackResult = DiffUtil.calculateDiff(diffCallback)
        listNotes.clear()
        listNotes.addAll(data)
        diffCallbackResult.dispatchUpdatesTo(this
        )
    }
}
