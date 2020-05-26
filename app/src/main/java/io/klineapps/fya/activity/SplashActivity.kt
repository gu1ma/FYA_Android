package io.klineapps.fya.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import io.klineapps.fya.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler: Handler = Handler()
        val runnable = Runnable {
            val i = Intent(this,SignInActivity::class.java)
            startActivity(i)
            finish()
        }
        handler.postDelayed(runnable,1000)
    }
}
