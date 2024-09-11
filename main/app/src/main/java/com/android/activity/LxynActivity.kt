package com.android.activity

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.os.Handler
import android.os.Looper
import android.os.Environment

import com.android.sdk.exec.R
import com.android.sdk.exec.databinding.ActivityLxynBinding
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
import android.content.DialogInterface

import androidx.appcompat.widget.AppCompatSeekBar
import androidx.cardview.widget.CardView
import androidx.appcompat.widget.Toolbar

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
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.slider.Slider

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

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher

@Suppress("DEPRECATION")
public class LxynActivity : AppCompatActivity() {

companion object {
    init {
        System.loadLibrary("cpu")
    }
    
}
    private lateinit var binding: ActivityLxynBinding
    
    private lateinit var root1: TextView
    private lateinit var root2: TextView
    private lateinit var root3: TextView
    private lateinit var root4: TextView
    private lateinit var root1c: Slider
    private lateinit var root0a: Button
    
    private lateinit var androidxCardView1: CardView
    private lateinit var androidxCardView2: CardView
    private lateinit var androidxCardView3: CardView
    private lateinit var androidxCardView4: CardView
    private lateinit var androidxCardView5: CardView
    private lateinit var androidxCardView6: CardView
    
    private var activityWidth: Int = 0
    private var activityHeight: Int = 0
    
    private lateinit var timer: Timer
    private var checkInterval = 5000L
    
    private lateinit var refreshRateUpdateTimer: Timer
      
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLxynBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.myToolbar
        setSupportActionBar(toolbar)
     supportActionBar?.setDisplayHomeAsUpEnabled(true)
     supportActionBar?.setDisplayShowTitleEnabled(true)
     toolbar?.setNavigationOnClickListener {
            // 调用 OnBackPressed 的处理方法
              onBackPressed()
        }
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        lxynass()
        
         root1 = binding.root1
         root2 = binding.root2
         root3 = binding.root3
         root4 = binding.root4
         root1c = binding.root1c
         root0a = binding.root0a
         androidxCardView1 = binding.androidxCardView1
         androidxCardView2 = binding.androidxCardView2
         androidxCardView3 = binding.androidxCardView3
         androidxCardView4 = binding.androidxCardView4
         androidxCardView5 = binding.androidxCardView5
         androidxCardView6 = binding.androidxCardView6
         
        binding.root1c.valueFrom = -70f
        binding.root1c.valueTo = 255f
        binding.root1c.value = getScreenBrightness().toFloat()

        binding.root1c.addOnChangeListener { _, value, fromUser ->
        if (fromUser) {
            setScreenBrightness(value.toInt())
        }
    }
        
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
        
        androidxCardView4.setOnClickListener {
             lxynshellsu()
        }
        
        androidxCardView5.setOnClickListener {
              lxynroot()
        }
        
        androidxCardView2.setOnClickListener {
             lxynpermissions()
        }
        
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        
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
    
    private fun lxynshellsu() {
        val items = arrayOf(
              "Su: 15 Hz",
              "Su: 30 Hz",
              "Su: 45 Hz",
              "Su: 60 Hz", 
              "Su: 90 Hz", 
              "Su: 120 Hz", 
              "Su: 144 Hz", 
              "Su: 165 Hz", 
              "Su: 200 Hz", 
              "Su: 240 Hz",
              "Su: 300 Hz",
              "Su: 360 Hz",
              "Su: 720 Hz",
              "Su: 1080 Hz",
              "Su: 1200 Hz",
              "Su: 2400 Hz",
              "Su: 3600 Hz",
              "Su: 7200 Hz",
              "Su: 9999 Hz"
         )
        var selectedIndex = 0
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.xyn_main_root_4)
            .setSingleChoiceItems(items, selectedIndex) { dialog, which ->
            selectedIndex = which
           
        }
            .setPositiveButton(R.string.xyn_sui_root_1) { _, _ ->
                  setRefreshRate(selectedIndex)
                }
            .setNegativeButton(R.string.xyn_sui_root_2) { _, _ ->
             Toast.makeText(this, getString(R.string.xyn_sui_root_3), Toast.LENGTH_SHORT).show()
             
           }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.decorView?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.lxyn_dialog_background)
     }
     
    private fun setRefreshRate(index: Int) {
        when (index) {
           0 -> set15HzRefreshRate()
           1 -> set30HzRefreshRate()
           2 -> set45HzRefreshRate()
           3 -> set60HzRefreshRate()
           4 -> set90HzRefreshRate()
           5 -> set120HzRefreshRate()
           6 -> set144HzRefreshRate()
           7 -> set165HzRefreshRate()
           8 -> set200HzRefreshRate()
           9 -> set240HzRefreshRate()
           10 -> set300HzRefreshRate()
           11 -> set360HzRefreshRate()
           12 -> set720HzRefreshRate()
           13 -> set1080HzRefreshRate()
           14 -> set1200HzRefreshRate()
           15 -> set2400HzRefreshRate()
           16 -> set3600HzRefreshRate()
           17 -> set7200HzRefreshRate()
           18 -> set9999HzRefreshRate()
          else -> {
            
          }
       }
    }
    
    private fun getScreenBrightness(): Int {
         return try {
           Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
          } catch (e: Settings.SettingNotFoundException) {
           e.printStackTrace()
         0
       }
    }

    private fun setScreenBrightness(value: Int) {
        val brightness = when {
            value < -70 -> -70
            value > 255 -> 255
            else -> value
         }
         Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
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
        dialog.window?.decorView?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.lxyn_dialog_background)
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
        dialog.window?.decorView?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.lxyn_dialog_background)
    }
    
    private fun lxynuio() {
    
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.update_log_title)
            .setMessage(R.string.update_log_message)
            .setPositiveButton("ok") { _, _ ->
        
    }
        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.decorView?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.lxyn_dialog_background)
    }
    
    private fun lxynappos() {
    
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.about_we)
            .setMessage(R.string.about_we_content)
            .setPositiveButton("ok") { _, _ ->
        
    }
        val dialog = builder.create()
        dialog.setCancelable(true) 
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.decorView?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.lxyn_dialog_background)
    }
    
    private fun setSystemTimeFormat() {
         try {
            val commands = arrayOf(
            "mkdir -p /data/data/com.android.sdk.exec/data",
            "mkdir -p /data/data/com.android.sdk.exec/app",
            "mkdir -p /data/data/com.android.sdk.exec/apks"
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
    
    private fun set15HzRefreshRate() {
        val success = setRefreshRateViaCommand("15", "15", "15", "15", "15", "15")
        showToast(success, "15")
    }
    
    private fun set30HzRefreshRate() {
        val success = setRefreshRateViaCommand("30", "30", "30", "30", "30", "30")
        showToast(success, "30")
    }
    
    private fun set45HzRefreshRate() {
        val success = setRefreshRateViaCommand("45", "45", "45", "45", "45", "45")
        showToast(success, "45")
    }
    
    private fun set60HzRefreshRate() {
        val success = setRefreshRateViaCommand("60", "60", "60", "60", "60", "60")
        showToast(success, "60")
    }

    private fun set90HzRefreshRate() {
        val success = setRefreshRateViaCommand("90", "90", "90", "90", "90", "90")
        showToast(success, "90")
    }

    private fun set120HzRefreshRate() {
        val success = setRefreshRateViaCommand("120", "120", "120", "120", "120", "120")
        showToast(success, "120")
    }

    private fun set144HzRefreshRate() {
        val success = setRefreshRateViaCommand("144", "144", "144", "144", "144", "144")
        showToast(success, "144")
    }
    
    private fun set165HzRefreshRate() {
        val success = setRefreshRateViaCommand("165", "165", "165", "165", "165", "165")
        showToast(success, "165")
    }
    
    private fun set200HzRefreshRate() {
        val success = setRefreshRateViaCommand("200", "200", "200", "200", "200", "200")
        showToast(success, "200")
    }
    
    private fun set240HzRefreshRate() {
        val success = setRefreshRateViaCommand("240", "240", "240", "240", "240", "240")
        showToast(success, "240")
    }
    
    private fun set300HzRefreshRate() {
        val success = setRefreshRateViaCommand("300", "300", "300", "300", "300", "300")
        showToast(success, "300")
    }
    
    private fun set360HzRefreshRate() {
        val success = setRefreshRateViaCommand("360", "360", "360", "360", "360", "360")
        showToast(success, "360")
    }
    
    private fun set720HzRefreshRate() {
        val success = setRefreshRateViaCommand("720", "720", "720", "720", "720", "720")
        showToast(success, "720")
    }
    
    private fun set1080HzRefreshRate() {
        val success = setRefreshRateViaCommand("1080", "1080", "1080", "1080", "1080", "1080")
        showToast(success, "1080")
    }
    
    private fun set1200HzRefreshRate() {
        val success = setRefreshRateViaCommand("1200", "1200", "1200", "1200", "1200", "1200")
        showToast(success, "1200")
    }
    
    private fun set2400HzRefreshRate() {
        val success = setRefreshRateViaCommand("2400", "2400", "2400", "2400", "2400", "2400")
        showToast(success, "2400")
    }
    
    private fun set3600HzRefreshRate() {
        val success = setRefreshRateViaCommand("3600", "3600", "3600", "3600", "3600", "3600")
        showToast(success, "3600")
    }
    
    private fun set7200HzRefreshRate() {
        val success = setRefreshRateViaCommand("7200", "7200", "7200", "7200", "7200", "7200")
        showToast(success, "7200")
    }
    
    private fun set9999HzRefreshRate() {
        val success = setRefreshRateViaCommand("9999", "9999", "9999", "9999", "9999", "9999")
        showToast(success, "9999")
    }
    
private fun setRefreshRateViaCommand(
    userRefreshRate: String,
    refreshRateMode: String,
    miuiRefreshRate: String,
    peakRefreshRate: String,
    minRefrshRate: String,
    vivoRefreshRateMode: String
): Boolean {
    return try {
        val process1 =  Runtime.getRuntime().exec("su -c settings put global user_refresh_rate $userRefreshRate")
        val process2 =  Runtime.getRuntime().exec("su -c settings put global refresh_rate_mode $refreshRateMode")
        val process3 =  Runtime.getRuntime().exec("su -c settings put global miui_refresh_rate $miuiRefreshRate")
        val process4 =  Runtime.getRuntime().exec("su -c settings put global peak_refresh_rate $peakRefreshRate")
        val process5 =  Runtime.getRuntime().exec("su -c settings put global min_refresh_rate $minRefrshRate")
        val process6 =  Runtime.getRuntime().exec("su -c settings put global vivo_screen_refresh_rate_mode $vivoRefreshRateMode")
        val process7 =  Runtime.getRuntime().exec("su -c settings put secure user_refresh_rate $userRefreshRate")
        val process8 =  Runtime.getRuntime().exec("su -c settings put secure refresh_rate_mode $refreshRateMode")
        val process9 =  Runtime.getRuntime().exec("su -c settings put secure miui_refresh_rate $miuiRefreshRate")
        val process10 =  Runtime.getRuntime().exec("su -c settings put secure peak_refresh_rate $peakRefreshRate")
        val process11 =  Runtime.getRuntime().exec("su -c settings put secure min_refresh_rate $minRefrshRate")
        val process12 =  Runtime.getRuntime().exec("su -c settings put secure vivo_screen_refresh_rate_mode $vivoRefreshRateMode")
        val process13 =  Runtime.getRuntime().exec("su -c settings put system user_refresh_rate $userRefreshRate")
        val process14 =  Runtime.getRuntime().exec("su -c settings put system refresh_rate_mode $refreshRateMode")
        val process15 =  Runtime.getRuntime().exec("su -c settings put system miui_refresh_rate $miuiRefreshRate")
        val process16 =  Runtime.getRuntime().exec("su -c settings put system peak_refresh_rate $peakRefreshRate")
        val process17 =  Runtime.getRuntime().exec("su -c settings put system min_refresh_rate $minRefrshRate")
        val process18 =  Runtime.getRuntime().exec("su -c settings put system vivo_screen_refresh_rate_mode $vivoRefreshRateMode")

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
