package com.android.sdk.exec

import android.app.Activity
import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.android.sdk.exec.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

public class AppLifecycleProvider : ContentProvider() {

companion object {
    init {
        System.loadLibrary("cpu")
    }
    
}
    private val appLifecycleEvents = mutableListOf<AppLifecycleEvent>()
    private val applicationCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            logAppLifecycleEvent(activity, "onCreate")
        }

        override fun onActivityStarted(activity: Activity) {
            logAppLifecycleEvent(activity, "onStart")
        }

        override fun onActivityResumed(activity: Activity) {
            logAppLifecycleEvent(activity, "onResume")
        }

        override fun onActivityPaused(activity: Activity) {
            logAppLifecycleEvent(activity, "onPause")
        }

        override fun onActivityStopped(activity: Activity) {
            logAppLifecycleEvent(activity, "onStop")
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {
            logAppLifecycleEvent(activity, "onDestroy")
        }
    }

    override fun onCreate(): Boolean {
        val application = context?.applicationContext as Application
        application.registerActivityLifecycleCallbacks(applicationCallbacks)
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor = MatrixCursor(arrayOf("timestamp", "eventType", "componentName"))
        appLifecycleEvents.forEach { event ->
            cursor.addRow(arrayOf(event.timestamp, event.eventType, event.componentName))
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    private fun logAppLifecycleEvent(activity: Activity, eventType: String) {
        val componentName = activity.componentName.flattenToString()
        val event = AppLifecycleEvent(System.currentTimeMillis(), eventType, componentName)
        appLifecycleEvents.add(event)
        if (BuildConfig.DEBUG) {
            Log.d("AppLifecycleProvider", "Event: $eventType, Component: $componentName")
        }
    }
}

data class AppLifecycleEvent(
    val timestamp: Long,
    val eventType: String,
    val componentName: String?
) {
    companion object {
        val EVENT_TYPES = listOf(
            "onCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy"
        )
    }
}