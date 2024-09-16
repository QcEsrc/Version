package com.os.version

import android.os.Build
import android.os.Bundle
import android.app.ActivityManager
import android.content.Context
import android.provider.Settings
import android.view.Display
import android.view.WindowManager
import android.util.DisplayMetrics
import android.content.res.Resources
import kotlin.math.sqrt
import androidx.appcompat.app.AppCompatActivity
import com.os.version.databinding.ActivityMainBinding
import androidx.cardview.widget.CardView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import java.text.SimpleDateFormat
import java.util.Locale
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    private var androidxNestedScrollView: NestedScrollView? = null
    private var androidxCardView1: CardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        androidxCardView1 = binding.androidxCardView1
        androidxNestedScrollView = binding.androidxNestedScrollView

        // Build
        val hardwareName = Build.HARDWARE
        val buildTime = Build.TIME
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(buildTime)
        val boardName = Build.BOARD
        val brand = Build.BRAND
        val systemName = "${Build.MANUFACTURER} ${Build.MODEL}"
        val androidVersion = Build.VERSION.RELEASE
        val sdkVersion = Build.VERSION.SDK_INT
        val securityPatch = Build.VERSION.SECURITY_PATCH
        val systemVersion = Build.FINGERPRINT
        val systemArchitecture = Build.SUPPORTED_ABIS.joinToString(", ")
        val kernelVersion = System.getProperty("os.version") ?: "N/A"
        val host = Build.HOST
        val radioVersion = Build.getRadioVersion()
        val manufacturer = Build.MANUFACTURER
        
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()

        val width: Int
        val height: Int

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    val windowMetrics = windowManager.currentWindowMetrics
    val bounds = windowMetrics.bounds
    width = bounds.width()
    height = bounds.height()
} else {
    @Suppress("DEPRECATION")
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    width = displayMetrics.widthPixels
    height = displayMetrics.heightPixels
}

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val deviceConfigurationInfo = activityManager.deviceConfigurationInfo

      val gpuName = deviceConfigurationInfo.glEsVersion
      
      val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        // TextView 
        binding.textViewHello.text = getString(R.string.hello_version)
        binding.textViewBoard.text = "Motherboard: $boardName"
        binding.textViewDevice.text = "Device: $systemName"
        binding.textViewAndroidVersion.text = "Android Version: $androidVersion"
        binding.textViewSdkVersion.text = "SDK Version: $sdkVersion"
        binding.textViewSecurityPatch.text = "Security Patch: $securityPatch"
        binding.textViewSystemVersion.text = "System Version: $systemVersion"
        binding.textViewSystemArchitecture.text = "System Architecture: $systemArchitecture"
        binding.textViewKernelVersion.text = "Kernel Version: $kernelVersion"
        binding.textViewBrands.text = "Brand: $brand"
        binding.textViewManufacturer.text = "Manufacturer: $manufacturer"
        binding.textViewHost.text = "Host: $host"
        binding.textViewRadioVersion.text = "Radio Version: $radioVersion"
        binding.textViewResolution.text = "Resolution: ${width}x${height}"
        binding.textViewBuildTime.text = "Build Time: $formattedDate"
        binding.textViewHardwareName.text = "Hardware Name: $hardwareName"
        binding.textViewgpuName.text = "OpenGL ES: $gpuName"
        binding.textViewAndroidId.text = "Android ID: $androidId"
        
        val supportedRefreshRates = getSupportedRefreshRates(this)
        val sortedRefreshRates = supportedRefreshRates.sorted()
        val refreshRateText = sortedRefreshRates.joinToString(", ") { "$it Hz" }
        binding.textViewRefreshRates.text = "Supported Refresh Rates: $refreshRateText"
    }
    
    private fun getSupportedRefreshRates(context: Context): List<Float> {
        val refreshRates = mutableListOf<Float>()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = windowManager.defaultDisplay

        val supportedRefreshRates = display.supportedModes
        for (mode in supportedRefreshRates) {
            refreshRates.add(mode.refreshRate)
        }

        return refreshRates
    }
    
    override fun onDestroy() {
        super.onDestroy()
        androidxNestedScrollView = null
        androidxCardView1 = null
    }
}
