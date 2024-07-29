package com.android.activity.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import xyn.xyn.xyn.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.RuntimeException
import kotlinx.coroutines.delay

import android.os.IBinder
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.RemoteException

import android.content.pm.PackageManager
import android.content.ComponentName
import android.content.ServiceConnection

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat

import android.app.Service
import android.app.PendingIntent
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager

import android.net.Uri
import android.net.ConnectivityManager

public class LxynService200 : Service() {

companion object {
    init {
        System.loadLibrary("shell")
    }
    private const val NOTIFICATION_ID = 200
}
    private lateinit var notification: Notification
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var isServiceRunning = false

    override fun onCreate() {
        super.onCreate()
        val notificationManager = getSystemService(NotificationManager::class.java)
    val channel = NotificationChannel("200hz_Foreground_Service", "200hz Foreground Service", NotificationManager.IMPORTANCE_HIGH)
    notificationManager.createNotificationChannel(channel)

    notification = NotificationCompat.Builder(this, "200hz_Foreground_Service")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentTitle("200hz Foreground Service")
        .setSmallIcon(R.drawable.ic_xyn_main)
        .build()
        
        isServiceRunning = true
        startPeakRefreshRateCommand()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    startForeground(NOTIFICATION_ID, notification)
    return super.onStartCommand(intent, flags, startId)
   }
    
    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        isServiceRunning = false
        coroutineScope.cancel()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun startPeakRefreshRateCommand() {
        coroutineScope.launch {
            while (isServiceRunning) {
                executePeakRefreshRateCommand()
                delay(7000) // 延迟 7 秒钟
            }
        }
    }

    private suspend fun executePeakRefreshRateCommand() {
            val commands = listOf(
                "su -c settings put secure refresh_rate_mode 200",
                "su -c settings put secure miui_refresh_rate 200",
                "su -c settings put secure user_refresh_rate 200",
                "su -c settings put secure peak_refresh_rate 200",
                "su -c settings put secure min_refresh_rate 200",
                "su -c settings put system refresh_rate_mode 200",
                "su -c settings put system miui_refresh_rate 200",
                "su -c settings put system user_refresh_rate 200",
                "su -c settings put system peak_refresh_rate 200",
                "su -c settings put system min_refresh_rate 200"
            )

            for (command in commands) {
                val process = Runtime.getRuntime().exec(command)
                val exitCode = process.waitFor()
                 if (exitCode == 0) {
                val outputStream = process.inputStream.bufferedReader().use { it.readText() }
                Log.d("PeakRefreshRateService", "Command output: $outputStream")
               } else {
                Log.e("PeakRefreshRateService", "Error executing command: $command, exit code: $exitCode")
            }
        }
    }
}