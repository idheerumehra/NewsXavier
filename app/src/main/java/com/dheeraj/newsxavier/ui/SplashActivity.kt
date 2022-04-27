package com.dheeraj.newsxavier.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.*
import com.dheeraj.newsxavier.R
import com.dheeraj.newsxavier.preferences.AppPreferences
import com.dheeraj.newsxavier.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity(millisInFuture: Long) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val prefrences =
            AppPreferences(context = this)

        prefrences.theme.asLiveData().observe(this, Observer{
            if(it.equals("Light")) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else if (it.equals("Dark")) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        })

        object: CountDownTimer(2000, 1000){
            override fun onFinish() {
                val goToMainActivity = Intent(applicationContext, MainActivity::class.java)
                startActivity(goToMainActivity)

                lifecycle.addObserver(object : LifecycleEventObserver {
                    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                        if (event == Lifecycle.Event.ON_STOP) {
                            lifecycle.removeObserver(this)
                            finish()
                        }
                    }
                })

            }

            override fun onTick(millisUntilFinished: Long) {

            }

        } .start()
    }
}