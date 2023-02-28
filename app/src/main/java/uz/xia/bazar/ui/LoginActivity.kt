package uz.xia.bazar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.xia.bazar.R
import uz.xia.bazar.ui.auth.ILoginListener
import uz.xia.bazar.ui.auth.LoginFragment
import uz.xia.bazar.ui.auth.SplashFragment

class LoginActivity : AppCompatActivity(),ILoginListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            addFragment(SplashFragment.newInstance())
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    override fun onToLogin() {
        addFragment(LoginFragment.newInstance())
    }

    override fun onClickLogin() {


    }
}