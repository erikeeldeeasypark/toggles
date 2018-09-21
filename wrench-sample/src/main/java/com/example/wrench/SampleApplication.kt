package com.example.wrench

import android.content.Context
import android.os.StrictMode
import android.util.Log

import com.example.wrench.di.AppInjector
import com.example.wrench.di.DaggerAppComponent
import com.izettle.wrench.preferences.WrenchPreferences

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SampleApplication : DaggerApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        AppInjector.init(this)

        if (WrenchPreferences(this).getBoolean("Use strict mode", false)) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())

            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())
        }


        Log.e("WrenchProvider", "sample app: current thread: " + Thread.currentThread() + " main thread: " + getMainLooper().getThread())

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
