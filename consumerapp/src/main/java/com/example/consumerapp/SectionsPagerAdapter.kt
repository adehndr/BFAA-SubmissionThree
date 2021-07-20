package com.example.consumerapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    var username: String? = null
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = username?.let { DetailInfoFragment.newInstance(it) }
            1 -> fragment = username?.let { DetailFollowingFragment.newInstance(it) }
            2 -> fragment = username?.let { DetailFollowerFragment.newInstance(it) }
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Profile"
            1 -> "Following"
            else -> "Follower"
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}