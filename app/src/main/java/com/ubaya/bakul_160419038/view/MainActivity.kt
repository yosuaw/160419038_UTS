package com.ubaya.bakul_160419038.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ubaya.bakul_160419038.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu_list.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        // Set up navigation view agar dihandle oleh navController
        NavigationUI.setupWithNavController(navView, navController)

        // Membuat koneksi antara navController dengan bottom bar
        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }

    fun starClicked(v: View) {
        if(v is ImageButton) {
            when(v.id) {
                R.id.imageButtonStar1 -> {
                    v.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar2.setImageResource(R.drawable.ic_baseline_star_border_24)
                    imageButtonStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    imageButtonStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    imageButtonStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                R.id.imageButtonStar2 -> {
                    imageButtonStar1.setImageResource(R.drawable.ic_baseline_star_24)
                    v.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                    imageButtonStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    imageButtonStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                R.id.imageButtonStar3 -> {
                    imageButtonStar1.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar2.setImageResource(R.drawable.ic_baseline_star_24)
                    v.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                    imageButtonStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                R.id.imageButtonStar4 -> {
                    imageButtonStar1.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar2.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar3.setImageResource(R.drawable.ic_baseline_star_24)
                    v.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                R.id.imageButtonStar5 -> {
                    imageButtonStar1.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar2.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar3.setImageResource(R.drawable.ic_baseline_star_24)
                    imageButtonStar4.setImageResource(R.drawable.ic_baseline_star_24)
                    v.setImageResource(R.drawable.ic_baseline_star_24)
                }
            }
        }
    }
}