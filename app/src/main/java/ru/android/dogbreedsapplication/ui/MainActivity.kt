package ru.android.dogbreedsapplication.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.Coordinator


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()!!.hide();
        initViews()
        Coordinator.onBreedsScreen(supportFragmentManager)
    }

    fun initViews(){
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.list -> {
                    Coordinator.onBreedsScreen(supportFragmentManager)
                    true
                }
                R.id.favourites -> {
                    Coordinator.onFavouriteBreedsScreen(supportFragmentManager)
                    true
                }
                else -> false
            }
        }
    }
}
