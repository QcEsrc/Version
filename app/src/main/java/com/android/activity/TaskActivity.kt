package com.android.activity

import android.os.Bundle
import android.os.Build
import android.app.Dialog
import android.widget.Toast
import android.widget.ImageView
import android.view.View
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.view.Menu
import android.view.MenuItem
import android.view.LayoutInflater
import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import xyn.xyn.xyn.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import xyn.xyn.xyn.databinding.ActivityTaskBinding

public class TaskActivity : AppCompatActivity() {

companion object {
    init {
        System.loadLibrary("apply")
    }
    
}
    private lateinit var binding: ActivityTaskBinding
    
    private var navController: NavController? = null
    private var appBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

         navController = findNavController(R.id.nav_host_fragment_activity_task)
         appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_fragment_valo, R.id.navigation_fragment_task, R.id.navigation_fragment_save, R.id.navigation_fragment_just, R.id.navigation_fragment_fast
            )
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration!!)
        binding.navView.setupWithNavController(navController!!)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?):   Boolean {
          menuInflater.inflate(R.menu.menu_task, menu)
         return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
           return when (item.itemId) {
              R.id.action_task_1 -> {
            Toast.makeText(this, getString(R.string.xyn_c1_1), Toast.LENGTH_SHORT).show()
               true
           }
              R.id.action_task_2 -> {
                  Taskdialogs()
                true
           }
              R.id.action_task_3 -> {
                  Taskdialogs1()
                true
           }
             else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun Taskdialogs() {
    
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.update_log_title)
            .setMessage(R.string.update_log_message)
            .setPositiveButton("ok") { _, _ ->
        
    }
           
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    
    private fun Taskdialogs1() {
    
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.about_we)
            .setMessage(R.string.about_we_content)
            .setPositiveButton("ok") { _, _ ->
        
    }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        navController = null
        appBarConfiguration = null
    }
}