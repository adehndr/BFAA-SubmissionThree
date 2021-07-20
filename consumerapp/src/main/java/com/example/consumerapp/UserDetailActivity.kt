package com.example.consumerapp

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.consumerapp.DatabaseContract.UserColums.Companion.CONTENT_URI
import com.example.consumerapp.databinding.ActivityUserDetailBinding
import com.example.consumerapp.dataclass.UserItems
import com.google.android.material.tabs.TabLayout

class UserDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private var statusFavorite: Boolean = false
    private lateinit var uriWithUserName: Uri
    private lateinit var user: UserItems
    private lateinit var userFound: UserItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle()
        user = intent.getParcelableExtra<UserItems>(EXTRA_PERSON) as UserItems
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        sectionsPagerAdapter.username = user.userName
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        uriWithUserName = Uri.parse(CONTENT_URI.toString() + "/" + user.userName)
        Log.d("Cekisicursorsatu", uriWithUserName.toString())
        val cursor = contentResolver.query(uriWithUserName, null, null, null, null)
        if (cursor != null) {
            try {
                userFound = MappingHelper.mapCursorToObject(cursor)
                if (userFound.userName == user.userName) {
                    statusFavorite = true
                }
            } catch (e: Exception) {
                e.stackTrace
            }
            cursor.close()
        } else {
            Log.d("Cekisicursordua", "cursor kosong atau tidak bisa diakses " + cursor.toString())
        }
        setStatusButtonFavorite(statusFavorite)
        binding.fabFavorite.setOnClickListener {
            statusFavorite = if (statusFavorite) {
                contentResolver.delete(uriWithUserName, null, null)
                sectionsPagerAdapter.notifyDataSetChanged()
                false
            } else {
                val values = ContentValues()
                values.put(DatabaseContract.UserColums.AVATAR, user.avatar)
                values.put(DatabaseContract.UserColums.USERNAME, user.userName)
                contentResolver.insert(CONTENT_URI, values)
                sectionsPagerAdapter.notifyDataSetChanged()
                true
            }
            setStatusButtonFavorite(statusFavorite)
        }
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setStatusButtonFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    private fun setActionBarTitle() {
        supportActionBar?.title = "Profile"
    }
}