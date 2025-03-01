package com.amirreza.osmiumproject.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amirreza.osmiumproject.ui.fragment.CellInfoFragment
import com.amirreza.osmiumproject.ui.fragment.CellLocationFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CellInfoFragment()
            1 -> CellLocationFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
