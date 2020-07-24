package ru.bey_sviatoslav.android.dogbreedsapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.bey_sviatoslav.android.dogbreedsapplication.businesslogic.http.DogApiService
import ru.bey_sviatoslav.android.dogbreedsapplication.utils.Coordinator
import ru.bey_sviatoslav.android.dogbreedsapplication.vo.Result


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
