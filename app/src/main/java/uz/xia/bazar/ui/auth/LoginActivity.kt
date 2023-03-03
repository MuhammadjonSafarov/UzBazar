package uz.xia.bazar.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.xia.bazar.R
import uz.xia.bazar.ui.auth.login.ILoginListener
import uz.xia.bazar.ui.auth.login.LoginFragment

class LoginActivity : AppCompatActivity(), ILoginListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            addFragment(SplashFragment.newInstance())
        }
    }

    override fun onToLogin() {
        addFragment(LoginFragment.newInstance())
    }

    override fun onToSmsConform(phoneNumber:String) {
        addFragment(SmsFragment.newInstance())
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

}