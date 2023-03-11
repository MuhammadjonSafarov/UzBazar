package uz.xia.bazar.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import uz.xia.bazar.R
import uz.xia.bazar.databinding.ActivityMainBinding
import uz.xia.bazar.utils.lazyFast

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazyFast {
        Navigation.findNavController(
            this, R.id.nav_host_fragment_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController, destination: NavDestination, arguments: Bundle?
    ) {
        if (destination.id in navMenuList) {
            binding.navView.visibility = View.VISIBLE
        } else {
            binding.navView.visibility = View.GONE
        }
    }
}

val navMenuList = listOf(
    R.id.navigation_home, R.id.navigation_search, R.id.navigation_profile
)