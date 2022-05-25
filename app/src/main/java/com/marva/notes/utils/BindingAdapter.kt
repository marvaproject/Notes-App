package com.marva.notes.utils

import android.view.View
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.card.MaterialCardView
import com.marva.notes.R
import com.marva.notes.data.entity.Notes
import com.marva.notes.data.entity.Priority
import com.marva.notes.ui.home.HomeFragmentDirections

object BindingAdapter {

    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColor(cardView: MaterialCardView, priority: Priority){
        when(priority){
            Priority.LOW -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
            Priority.MEDIUM -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
            Priority.HIGH -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink))
        }
    }

    @BindingAdapter("android:sendDataToDetail")
    @JvmStatic
    fun sendDataToDetail(view: ConstraintLayout, currentItem: Notes){
        view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    @BindingAdapter("android:parsePriorityToInt")
    @JvmStatic
    fun parsePriorityToInt(view: Spinner, priority: Priority){
        when(priority){
            Priority.LOW -> view.setSelection(2)
            Priority.MEDIUM -> view.setSelection(1)
            Priority.HIGH -> view.setSelection(0)
        }
    }

    @BindingAdapter("android:emptyDatabase")
    @JvmStatic
    fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
        if(emptyDatabase.value == true){
            view.visibility = View.VISIBLE
        }else{
            view.visibility = View.INVISIBLE
        }
    }
}