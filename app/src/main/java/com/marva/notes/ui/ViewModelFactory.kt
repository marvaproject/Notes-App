package com.marva.notes.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory private constructor(private val myApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NotesViewModel::class.java) -> NotesViewModel(myApplication) as T
            else -> throw IllegalAccessException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

        companion object {
            @Volatile
            private var INSTANCE : ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application) : ViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }
}