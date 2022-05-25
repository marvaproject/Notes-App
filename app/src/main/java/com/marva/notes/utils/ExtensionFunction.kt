package com.marva.notes.utils

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.marva.notes.R
import com.marva.notes.ui.MainActivity

object ExtentionFunctions {
    fun Toolbar.setActionBar(requireActivity: FragmentActivity) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        (requireActivity as MainActivity).setSupportActionBar(this)
        setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.updateFragment -> setNavigationIcon(R.drawable.ic_left_arrow)
                R.id.detailFragment -> setNavigationIcon(R.drawable.ic_left_arrow)
                R.id.addFragment -> setNavigationIcon(R.drawable.ic_left_arrow)
            }
        }
    }
}