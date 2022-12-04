package com.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("Charts"))
        tabLayout.addTab(tabLayout.newTab().setText("Raw Data"))

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setOnTabSelectedListener(
            object : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    //for selected tab, write your code here...
                }
            })

    }
}