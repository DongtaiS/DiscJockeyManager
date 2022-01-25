package com.example.discjockeymanager.mainpages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.discjockeymanager.R
import com.example.discjockeymanager.databinding.ActivityHomepageBinding

class HomepageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setup navigation controller
        val navController = findNavController(R.id.navhostFrag)
        NavigationUI.setupWithNavController(binding.navView, navController)

        //When menu button is clicked, open nav drawer
        binding.btnMainHomeMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }

        //When nav drawer opens, slide page content with it to the right
        val drawerToggle = object : ActionBarDrawerToggle(this, binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                binding.contentMainHome.translationX = slideX
            }
        }
        binding.drawerLayout.addDrawerListener(drawerToggle)
    }
}