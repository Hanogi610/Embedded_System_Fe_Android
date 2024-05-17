package com.example.embeddedsystem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.embeddedsystem.adapter.MainViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val houseId = intent.getIntExtra("houseId", 0)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        viewPager2 = findViewById(R.id.viewPager)
        val adapter = MainViewPagerAdapter(this,houseId)
        viewPager2.adapter = adapter
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    viewPager2.currentItem = 0
                    true
                }
                R.id.historyFragment -> {
                    viewPager2.currentItem = 1
                    true
                }
                else -> false
            }
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigationView.selectedItemId = R.id.homeFragment
                    1 -> bottomNavigationView.selectedItemId = R.id.historyFragment
                }
            }
        })
    }
}