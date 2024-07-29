package com.android.activity.application

import android.app.Application
import android.app.Dialog
import android.util.Log
import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import java.io.IOException
import java.io.BufferedReader
import xyn.xyn.xyn.R

public class Lxynapplication : Application() {
    override fun onCreate() {
        super.onCreate()
        addStartupDelay()
    }
    private fun addStartupDelay() {
        try {
            Thread.sleep(800)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
