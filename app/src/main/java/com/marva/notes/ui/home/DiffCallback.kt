package com.marva.notes.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.marva.notes.data.entity.Notes

class DiffCallback(val oldList: List<Notes>,private val newList: List<Notes>): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldList[oldItemPosition]
        val newData = newList[newItemPosition]
        return oldData.id == newData.id &&
                oldData.title == newData.title &&
                oldData.description == newData.description &&
                oldData.date == newData.date &&
                oldData.priority == newData.priority
    }
}