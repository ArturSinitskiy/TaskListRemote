package com.example.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tasklist.databinding.FragmentTasksBinding
import com.example.tasklist.databinding.ItemTaskFragmentBinding

class ViewPagerAdapter(fragmentActivity: FragmentActivity, val fragmentsToShow: List<Fragment>): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return fragmentsToShow.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsToShow[position]
    }


}