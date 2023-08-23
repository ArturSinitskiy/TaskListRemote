package com.example.tasklist

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    val fragmentsList = listOf<Fragment>(
        TasksFragment(), CompletedTasksFragment()
    )

    private lateinit var binding: ActivityMainBinding
    private val adapter = ViewPagerAdapter(this, fragmentsList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab, position ->
            tab.text = when(position){
                0 -> "Текущие задачи"
                else -> "Выполненные"
            }
        }.attach()
    }


}