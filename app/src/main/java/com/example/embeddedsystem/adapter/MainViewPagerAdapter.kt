package com.example.embeddedsystem.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.embeddedsystem.fragment.HistoryFragment
import com.example.embeddedsystem.fragment.HomeFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity,private val houseId : Int) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance(houseId)
            1 -> HistoryFragment.newInstance(houseId)
            else -> HomeFragment.newInstance(houseId)
        }
    }
}