package uz.xia.bazar.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.xia.bazar.R
import uz.xia.bazar.ui.auth.SmsFragment
import uz.xia.bazar.ui.auth.SplashFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            addFragment(SmsFragment.newInstance())
        }
        Handler().postDelayed(Runnable {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }, 1_000L)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }
}