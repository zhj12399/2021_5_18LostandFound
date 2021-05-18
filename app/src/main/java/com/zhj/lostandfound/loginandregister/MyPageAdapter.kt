package com.zhj.lostandfound.loginandregister

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

val NUM_PAGES = 3

class MyPageAdpater(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    //负责实例化特定位置上的Fragment
    override fun createFragment(position: Int): Fragment {
        return FirstLoginFragment(position)
    }
}