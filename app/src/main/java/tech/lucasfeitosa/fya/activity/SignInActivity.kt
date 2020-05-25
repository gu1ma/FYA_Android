package tech.lucasfeitosa.fya.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import tech.lucasfeitosa.fya.R
import tech.lucasfeitosa.fya.activity.base.BaseActivity

class SignInActivity : BaseActivity(){

    private var fab:FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        fab = findViewById(R.id.fab_next_step)
        fab?.setOnClickListener {
            goToActivity(MainActivity::class.java)
        }
    }
}
