package com.example.consumerapp

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.DatabaseContract.UserColums.Companion.CONTENT_URI
import com.example.consumerapp.UserDetailActivity.Companion.EXTRA_PERSON
import com.example.consumerapp.databinding.ActivityFavoriteBinding
import com.example.consumerapp.dataclass.UserItems

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle()

        binding.rvFavoriteUsers.setHasFixedSize(true)
        adapter = UserAdapter()

        binding.rvFavoriteUsers.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                val intent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                intent.putExtra(EXTRA_PERSON, data)
                finish()
                startActivity(intent)
            }
        })

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUsers()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        loadUsers()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setActionBarTitle() {
        supportActionBar?.title = "Favorited Users"
    }

    private fun loadUsers() {
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        val users = MappingHelper.mapCursorToArrayList(cursor)
        adapter.mData = users

    }

}