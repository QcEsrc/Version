package com.android.activity

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.os.Handler
import android.os.Looper
import android.os.Environment

import xyn.xyn.xyn.R
import xyn.xyn.xyn.databinding.ActivityLxynBinding
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.hardware.display.DisplayManager
import android.util.DisplayMetrics

import android.content.res.Resources
import android.content.res.Configuration
import android.content.Intent
import android.content.Context
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.content.pm.PackageManager

import androidx.appcompat.widget.AppCompatSeekBar

import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.provider.Settings

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Icon
import android.graphics.drawable.Drawable

import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.Menu
import android.view.MenuItem
import android.view.animation.LinearInterpolator

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.card.MaterialCardView
import androidx.constraintlayout.widget.ConstraintLayout

import android.net.Uri
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import android.annotation.SuppressLint
import android.animation.AnimatorSet
import android.animation.ObjectAnimator

import java.security.Signature
import java.security.NoSuchAlgorithmException
import java.util.ArrayList
import java.util.Collections
import java.util.List
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

import com.android.activity.TaskActivity
import com.android.activity.service.LxynService90
import com.android.activity.service.LxynService120
import com.android.activity.service.LxynService144
import com.android.activity.service.LxynService165
import com.android.activity.service.LxynService200

public class LxynActivity : AppCompatActivity() {

companion object {
    init {
        System.loadLibrary("root")
    }
    
}
    private lateinit var binding: ActivityLxynBinding
    
    private lateinit var root1: TextView
    private lateinit var root2: TextView
    private lateinit var root3: TextView
    private lateinit var root4: TextView
    private lateinit var root1c: AppCompatSeekBar
    private lateinit var root0a: Button
    private lateinit var root2a: MaterialCardView
    private lateinit var root2b: MaterialCardView
    private lateinit var root2c: MaterialCardView
    private lateinit var root2d: MaterialCardView
    private lateinit var root2e: MaterialCardView
    private lateinit var root2f: MaterialCardView
    private lateinit var root2g: MaterialCardView
    private lateinit var root2j: MaterialCardView
    
    private var activityWidth: Int = 0
    private var activityHeight: Int = 0
    
    private lateinit var timer: Timer
    private var checkInterval = 5000L
    
    private lateinit var refreshRateUpdateTimer: Timer
      
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLxynBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        lxynass()
        
         root1 = binding.root1
         root2 = binding.root2
         root3 = binding.root3
         root4 = binding.root4
         root1c = binding.root1c
         root0a = binding.root0a
         root2a = binding.root2a
         root2b = binding.root2b
         root2c = binding.root2c
         root2d = binding.root2d
         root2e = binding.root2e
         root2f = binding.root2f
         root2g = binding.root2g
         root2j = binding.root2j
         
        root1c.min = -70
        root1c.max = 255
        root1c.progress = getScreenBrightness()
        root1c.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    setScreenBrightness(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                setScreenBrightness(root1c?.progress ?: 0)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                
            }
        })
        
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        val widthPixels = size.x
        val heightPixels = size.y
        val screenResolutionText2 = String.format(
          resources.getString(R.string.xyn_sxl_root_2),
            widthPixels,
            heightPixels
        )
        root4.text = screenResolutionText2

        updateScreenRefreshRateDisplay()

        refreshRateUpdateTimer = Timer()
        refreshRateUpdateTimer.schedule(object : TimerTask() {
        override fun run() {
            updateScreenRefreshRateDisplay()
        }
    }, 3000, 3000)

        val supportedRefreshRates = windowManager.defaultDisplay.supportedRefreshRates
        var supportedRefreshRatesText = getString(R.string.xyn_sxl_root_1)
        for ((index, rate) in supportedRefreshRates.withIndex()) {
            supportedRefreshRatesText += "$rate Hz"
            if (index < supportedRefreshRates.size - 1) {
                supportedRefreshRatesText += "、 "
            }
        }
        root2.text = supportedRefreshRatesText
        
       startPermissionCheckTimer()
       
       root0a.setOnClickListener {
            requestRootPermission()
        }
        
        root2a.setOnClickListener {
             lxynshell60()
        }
        
        root2b.setOnClickListener {
             lxynshell90()
        }
        
        root2c.setOnClickListener {
             lxynshell120()
        }
        
        root2d.setOnClickListener {
             lxynshell144()
        }
        
        root2e.setOnClickListener {
             lxynshell165()
        }
        
        root2f.setOnClickListener {
              lxynroot()
        }
        
        root2g.setOnClickListener {
             lxynshell200()
        }
        
        root2j.setOnClickListener {
             lxynpermissions()
        }
        
    }
    
    private fun lxynpermissions() {
       val permissions = arrayOf(
          Manifest.permission.POST_NOTIFICATIONS
        )

       val permissionGranted = permissions.all {
         ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
      }

        if (!permissionGranted) {
          ActivityCompat.requestPermissions(this, permissions, 1)
        }
    }
    
    private fun lxynshell60() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_main_root_14)
            .setMessage(R.string.xyn_main_root_15)
            .setPositiveButton("shell") { _, _ ->
                  set60HzRefreshRate()
                }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
     }
    
    private fun lxynshell90() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_main_root_4)
            .setMessage(R.string.xyn_main_root_5)
            .setPositiveButton("Service") { _, _ ->
                startLxynService90()
             }
            .setNegativeButton("shell") { _, _ ->
                set90HzRefreshRate()     
           }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
     }
     
     private fun lxynshell120() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_main_root_6)
            .setMessage(R.string.xyn_main_root_7)
            .setPositiveButton("Service") { _, _ ->
                 startLxynService120()
              }
            .setNegativeButton("shell") { _, _ ->
                 set120HzRefreshRate()
           }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
     }
     
     private fun lxynshell144() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_main_root_8)
            .setMessage(R.string.xyn_main_root_9)
            .setPositiveButton("Service") { _, _ ->
                  startLxynService144()
               }
            .setNegativeButton("shell") { _, _ ->
                  set144HzRefreshRate()
           }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
     }
     
     private fun lxynshell165() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_main_root_10)
            .setMessage(R.string.xyn_main_root_11)
            .setPositiveButton("Service") { _, _ ->
                  startLxynService165()
              }
            .setNegativeButton("shell") { _, _ ->
                  set165HzRefreshRate()
           }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
     }
     
     private fun lxynshell200() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_main_root_12)
            .setMessage(R.string.xyn_main_root_13)
            .setPositiveButton("Service") { _, _ ->
                   startLxynService200()
               }
            .setNegativeButton("shell") { _, _ ->
                   set200HzRefreshRate()
           }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
     }
    
    private fun startLxynService90() {
        val intent = Intent(this, LxynService90::class.java)
         startForegroundService(intent)
    }
    
    private fun startLxynService120() {
        val intent = Intent(this, LxynService120::class.java)
         startForegroundService(intent)
    }
    
    private fun startLxynService144() {
        val intent = Intent(this, LxynService144::class.java)
          startForegroundService(intent)
    }
    
    private fun startLxynService165() {
        val intent = Intent(this, LxynService165::class.java)
         startForegroundService(intent)
    }
    
    private fun startLxynService200() {
        val intent = Intent(this, LxynService200::class.java)
         startForegroundService(intent)
    }
    
    private fun getScreenBrightness(): Int {
        return runCommand("settings get system screen_brightness").toIntOrNull() ?: 0
    }

    private fun setScreenBrightness(value: Int) {
        val brightness = when {
            value < -70 -> -70
            value > 255 -> 255
            else -> value
        }
        runCommand("settings put system screen_brightness $brightness")
    }

    private fun runCommand(command: String): String {
        return try {
            val process = Runtime.getRuntime().exec("su -c $command")
            val inputStream = process.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
    
    private fun lxynass() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_main_root_1)
            .setMessage(R.string.xyn_main_root_2)
            .setIcon(R.drawable.ic_xyn_main)
            .setPositiveButton("ok") { _, _ ->
                Toast.makeText(this, getString(R.string.xyn_main_root_3), Toast.LENGTH_SHORT).show()
                }
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    
    private fun lxynroot() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_systems_root_1)
            .setMessage(R.string.xyn_systems_root_2)
            .setPositiveButton(R.string.xyn_systems_root_3) { _, _ ->
                setSystemTimeFormat()
                }
             .setNegativeButton(R.string.xyn_systems_root_4) { _, _ ->
             Toast.makeText(this, getString(R.string.xyn_systems_root_5), Toast.LENGTH_SHORT).show()
             
           }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true) 
        dialog.show()
    }
    
    private fun lxynuio() {
    
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
    
    private fun lxynappos() {
    
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
    
    private fun setSystemTimeFormat() {
         try {
            val commands = arrayOf(
            "settings put system screen_brightness_mode 2",
            "settings put system screen_brightness_mode 2",
            "settings put system screen_brightness_mode 2"
         )
        
         for (command in commands) {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
            process.waitFor()
            if (process.exitValue() != 0) {
                Toast.makeText(this, getString(R.string.xyn_toast_1), Toast.LENGTH_SHORT).show()
                return
            }
        }
        
             Toast.makeText(this, getString(R.string.xyn_toast_2), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
             Toast.makeText(
                      this,
               getString(R.string.xyn_toast_3, e.message),
           Toast.LENGTH_SHORT
          ).show()
       }
   }
    
    override fun onDestroy() {
        super.onDestroy()
        stopPermissionCheckTimer()
        refreshRateUpdateTimer.cancel()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?):   Boolean {
    menuInflater.inflate(R.menu.menu_xyn, menu)
    return true
}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.action_xyn_1 -> {
   val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_xyn_2 -> {
            lxynuio()
            true
        }
        R.id.action_xyn_3 -> {
            lxynappos()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
    
    private fun requestRootPermission() {
        try {
            val process = Runtime.getRuntime().exec("su")
            val outputStream = process.outputStream
            val command = "sudo root"
            outputStream.write(command.toByteArray())
            outputStream.flush()
            outputStream.close()
            process.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
    
    private fun updateScreenRefreshRateDisplay() {
       val currentRefreshRate = windowManager.defaultDisplay.refreshRate
        runOnUiThread {
        root1.text = getString(R.string.xyn_refresh_1, currentRefreshRate.toString())
      }
   }

    private fun startPermissionCheckTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                checkPermissionStatus()
            }
        }, 0, checkInterval)
    }

    private fun stopPermissionCheckTimer() {
        timer.cancel()
    }

    private fun checkPermissionStatus() {
        runOnUiThread {
            val (adbEnabled, isRooted) = checkAdbAndRootStatus(this)
            
            root3.text = when {
               adbEnabled -> getString(R.string.xyn_adb_1)
               isRooted -> getString(R.string.xyn_root_1)
               else -> getString(R.string.xyn_adb_2)
             }
        }
    }

    private fun checkAdbAndRootStatus(context: Context): Pair<Boolean, Boolean> {
        val adbEnabled = checkAdbStatus(context)
        val isRooted = checkRootStatus()
        return Pair(adbEnabled, isRooted)
    }
    
    private fun checkAdbStatus(context: Context): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("settings get global adb_enabled")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = reader.readLine()
            output.toInt() == 1
        } catch (e: Exception) {
            false
        }
    }

    private fun checkRootStatus(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su")
            val writer = process.outputStream.bufferedWriter()
            writer.write("id\n")
            writer.flush()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = reader.readLine()
            output.contains("uid=0")
        } catch (e: Exception) {
            false
        }
    }
    
    private fun set60HzRefreshRate() {
        val success = setRefreshRateViaCommand("60", "60", "60", "60", "60")
        showToast(success, "60")
    }

    private fun set90HzRefreshRate() {
        val success = setRefreshRateViaCommand("90", "90", "90", "90", "90")
        showToast(success, "90")
    }

    private fun set120HzRefreshRate() {
        val success = setRefreshRateViaCommand("120", "120", "120", "120", "120")
        showToast(success, "120")
    }

    private fun set144HzRefreshRate() {
        val success = setRefreshRateViaCommand("144", "144", "144", "144", "144")
        showToast(success, "144")
    }
    
    private fun set165HzRefreshRate() {
        val success = setRefreshRateViaCommand("165", "165", "165", "165", "165")
        showToast(success, "165")
    }
    
    private fun set200HzRefreshRate() {
        val success = setRefreshRateViaCommand("200", "200", "200", "200", "200")
        showToast(success, "200")
    }
    
private fun setRefreshRateViaCommand(
    userRefreshRate: String,
    refreshRateMode: String,
    miuiRefreshRate: String,
    peakRefreshRate: String,
    minRefrshRate: String
): Boolean {
    return try {
        val process1 =  Runtime.getRuntime().exec("su -c settings put global user_refresh_rate $userRefreshRate")
        val process2 =  Runtime.getRuntime().exec("su -c settings put global refresh_rate_mode $refreshRateMode")
        val process3 =  Runtime.getRuntime().exec("su -c settings put global miui_refresh_rate $miuiRefreshRate")
        val process4 =  Runtime.getRuntime().exec("su -c settings put global peak_refresh_rate $peakRefreshRate")
        val process5 =  Runtime.getRuntime().exec("su -c settings put global min_refresh_rate $minRefrshRate")
        val process6 =  Runtime.getRuntime().exec("su -c settings put secure user_refresh_rate $userRefreshRate")
        val process7 =  Runtime.getRuntime().exec("su -c settings put secure refresh_rate_mode $refreshRateMode")
        val process8 =  Runtime.getRuntime().exec("su -c settings put secure miui_refresh_rate $miuiRefreshRate")
        val process9 =  Runtime.getRuntime().exec("su -c settings put secure peak_refresh_rate $peakRefreshRate")
        val process10 =  Runtime.getRuntime().exec("su -c settings put secure min_refresh_rate $minRefrshRate")
        val process11 =  Runtime.getRuntime().exec("su -c settings put system user_refresh_rate $userRefreshRate")
        val process12 =  Runtime.getRuntime().exec("su -c settings put system refresh_rate_mode $refreshRateMode")
        val process13 =  Runtime.getRuntime().exec("su -c settings put system miui_refresh_rate $miuiRefreshRate")
        val process14 =  Runtime.getRuntime().exec("su -c settings put system peak_refresh_rate $peakRefreshRate")
        val process15 =  Runtime.getRuntime().exec("su -c settings put system min_refresh_rate $minRefrshRate")

     process1.waitFor() == 0 && process2.waitFor() == 0 && process3.waitFor() == 0 && process4.waitFor() == 0 && process5.waitFor() == 0 && process6.waitFor() == 0 && process7.waitFor() == 0 && process8.waitFor() == 0 && process9.waitFor() == 0 && process10.waitFor() == 0 && process11.waitFor() == 0 && process12.waitFor() == 0 && process13.waitFor() == 0 && process14.waitFor() == 0 && process15.waitFor() == 0
    } catch (e: IOException) {
        false
    }
}
    
    private fun showToast(success: Boolean, rate: String) {
        Toast.makeText(
              this,
      if (success) getString(R.string.xyn_toast_4, rate.toString())
             else getString(R.string.xyn_toast_5),
          Toast.LENGTH_SHORT
       ).show()
    }

    private fun getRefreshRateFromCommand(command: String): String {
        return try {
            val process = Runtime.getRuntime().exec(command)
            val inputStream = process.inputStream
            val output = inputStream.bufferedReader().use { it.readText() }
            output.trim()
        } catch (e: IOException) {
            "N/A"
        }
    }
}
