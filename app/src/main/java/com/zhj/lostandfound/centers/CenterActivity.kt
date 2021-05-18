package com.zhj.lostandfound.centers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zhj.lostandfound.R
import kotlinx.android.synthetic.main.activity_center.*

class CenterActivity : AppCompatActivity() {
    val fragmentList = mutableListOf<Fragment>(
       FragmentCenter1(),  FragmentCenter2(),  FragmentCenter3(), FragmentCenter4()
    )
    val titleList = mutableListOf<String>("失物大厅", "发布信息", "消息", "我")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center)

        val adapter= CenterTabsAdapter(this,fragmentList,titleList)
        viewPager.adapter=adapter
        TabLayoutMediator(tabs,viewPager){
                tab,position->
            tab.text=adapter.getPageTitle(position)
        }.attach()
    }
}