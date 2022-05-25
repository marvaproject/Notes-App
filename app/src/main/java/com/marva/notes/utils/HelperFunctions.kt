package com.marva.notes.utils

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.marva.notes.R
import com.marva.notes.data.entity.Notes
import com.marva.notes.data.entity.Priority
import java.text.SimpleDateFormat
import java.util.*

object HelperFunctions {

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parseToPriority(context: Context?, priority: String): Priority {
        val expectedPriority = context?.resources?.getStringArray(R.array.priorities)
        return when (priority) {
            expectedPriority?.get(0) -> Priority.HIGH
            expectedPriority?.get(1) -> Priority.MEDIUM
            expectedPriority?.get(2) -> Priority.LOW
            else -> Priority.LOW
        }
    }
    
    fun spinnerListener(context: Context?, priorityIndicator: CardView) : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            context?.let {
                when(position){
                    0 -> {
                        //untuk menentukan warna
                        val pink = ContextCompat.getColor(it, R.color.pink)
                        priorityIndicator.setBackgroundColor(pink)
                    }
                    1 -> {
                        val yellow = ContextCompat.getColor(it, R.color.yellow)
                        priorityIndicator.setBackgroundColor(yellow)
                    }
                    2 -> {
                        val green = ContextCompat.getColor(it, R.color.green)
                        priorityIndicator.setBackgroundColor(green)
                    }
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkIfDatabaseEmpty(data: List<Notes>) {
        emptyDatabase.value = data.isEmpty()
    }

    fun dateTodaySimpleFormat() : String {
        val date = Date()
        val simpleFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return simpleFormat.format(date.time)
    }

}