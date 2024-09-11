package com.android.sdk

import android.Manifest
import android.graphics.Color
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.view.WindowInsets
import android.annotation.SuppressLint

import android.os.IBinder
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.RemoteException

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.ComponentName
import android.content.ServiceConnection
import android.content.BroadcastReceiver

import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationCompat.MessagingStyle
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.util.ObjectsCompat
import androidx.core.util.ObjectsCompat.requireNonNull
import androidx.lifecycle.LifecycleService

import android.app.Service
import android.app.PendingIntent
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationChannelGroup

import android.net.Uri
import android.net.ConnectivityManager

import android.provider.Settings
import java.text.DecimalFormat
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.android.sdk.NotificationService
import com.android.sdk.exec.R

public class NotificationService : LifecycleService() {

    companion object {
        init {
           System.loadLibrary("cpu")
        }
    }

    private val NOTIFICATION_ID = 1230
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        
        return START_STICKY
    }

    private fun createNotification(): Notification {
        // 创建通知渠道
        val channelId = "android"
        val channelName = "Notification Service"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelName, importance)

        // 注册通知渠道
        notificationManager.createNotificationChannel(notificationChannel)
         val titleText = "๑>؂<๑"
         val message = "😂😂😂😂😂😂😂"
        // 创建通知构建器，并应用自定义布局和图标
    val builder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.notification_icon)
        .setContentTitle(titleText) // 通知标题
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true) // 设置为可取消
        .setOngoing(true)

        return builder.build()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

   /* override fun onBind(intent: Intent?): IBinder? {
        return null
    } */
}