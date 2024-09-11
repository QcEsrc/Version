package com.android.activity

import android.os.Bundle
import android.os.Build
import android.app.Dialog

import android.widget.Toast
import android.widget.ImageView
import android.widget.TextView

import android.os.Handler
import android.os.Looper

import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.Menu
import android.view.MenuItem
import android.view.LayoutInflater
import android.view.Gravity

import android.content.DialogInterface
import android.content.Intent
import android.content.Context

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.core.content.ContextCompat
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher

import com.android.sdk.exec.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.android.sdk.exec.databinding.ActivityTaskBinding

import com.android.activity.LxynActivity

public class TaskActivity : AppCompatActivity() {

companion object {
    init {
        System.loadLibrary("cpu")
    }
    
}
    private lateinit var binding: ActivityTaskBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var androidxConstraintLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
       androidxConstraintLayout = binding.androidxConstraintLayout

        // 初始化NavController和AppBarConfiguration
        navController = findNavController(R.id.nav_host_fragment_activity_task)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_fragment_valo, 
                R.id.navigation_fragment_task,
                R.id.navigation_fragment_save, 
                R.id.navigation_fragment_just,
                R.id.navigation_fragment_fast
            )
        )
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
      binding.toolbar?.setNavigationOnClickListener {
          onBackPressed()
          //  finish()
          //  finishAffinity()
          // finishAndRemoveTask()
          // System.exit(0)
      } 
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
        }

        // 将BottomNavigationView与NavController关联
        binding.navView.setupWithNavController(navController)
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        
    }
    
    override fun onCreateOptionsMenu(menu: Menu?):   Boolean {
          menuInflater.inflate(R.menu.menu_task, menu)
    
         return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
           return when (item.itemId) {
              R.id.action_task_1 -> {
    val intent = Intent(this, LxynActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, getString(R.string.xyn_c1_2), Toast.LENGTH_SHORT).show()
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
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.decorView?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.task_dialog_background)
    }
    
    private fun Taskdialogs1() {
    
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.about_we)
            .setMessage(R.string.about_we_content)
            .setPositiveButton("ok") { _, _ ->
        
    }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.decorView?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.task_dialog_background)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
    }
}