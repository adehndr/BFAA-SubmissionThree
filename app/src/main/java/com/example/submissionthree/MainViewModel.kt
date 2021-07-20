package com.example.submissionthree

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionthree.dataclass.UserDetailItems
import com.example.submissionthree.dataclass.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject


class MainViewModel : ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<UserItems>>()
    private val listUsersFollowing = MutableLiveData<ArrayList<UserItems>>()
    private val listUsersFollowers = MutableLiveData<ArrayList<UserItems>>()
    private val detailUser = MutableLiveData<UserDetailItems>()

    fun setUsersProfile(username: String) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", BuildConfig.API_KEY)
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$username"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val responseObject = JSONObject(result)
                    val userdetailitems = UserDetailItems()
                    userdetailitems.userName = responseObject.getString("login")
                    userdetailitems.avatar = responseObject.getString("avatar_url")
                    userdetailitems.userFullName = responseObject.getString("name")
                    userdetailitems.location = responseObject.getString("location")
                    userdetailitems.company = responseObject.getString("company")
                    detailUser.postValue(userdetailitems)
                } catch (e: Exception) {
                    e.printStackTrace()
                    e.message?.let { Log.d("cekError", it) }
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                if (error != null) {
                    Log.d("onFailure", errorMessage)
                }
            }

        })
    }

    fun setUsersQuery(username: String) {
        val listItems = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", BuildConfig.API_KEY)
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=${username}"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val responseObject = JSONObject(result)
                    val jsonArray = responseObject.getJSONArray("items")

                    for (i in 0 until jsonArray.length()) {
                        val user = jsonArray.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.userName = user.getString("login")
                        userItems.avatar = user.getString("avatar_url")
                        listItems.add(userItems)
                    }
                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    e.printStackTrace()
                    e.message?.let { Log.d("cekError", it) }
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                if (error != null) {
                    Log.d("onFailure", errorMessage)
                }
            }

        })
    }

    fun setUsersFollowing(username: String) {
        val listItems = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", BuildConfig.API_KEY)
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$username/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.userName = item.getString("login")
                        userItems.avatar = item.getString("avatar_url")
                        listItems.add(userItems)
                    }
                    listUsersFollowing.postValue(listItems)
                } catch (e: Exception) {
                    e.printStackTrace()
                    e.message?.let { Log.d("cekError", it) }
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                if (error != null) {
                    Log.d("onFailure", errorMessage)
                }
            }

        })
    }

    fun setUsersFollower(username: String) {
        val listItems = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", BuildConfig.API_KEY)
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$username/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.userName = item.getString("login")
                        userItems.avatar = item.getString("avatar_url")
                        listItems.add(userItems)
                    }
                    listUsersFollowers.postValue(listItems)
                } catch (e: Exception) {
                    e.printStackTrace()
                    e.message?.let { Log.d("cekError", it) }
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                if (error != null) {
                    Log.d("onFailure", errorMessage)
                }
            }

        })
    }

    fun getUsersQuery(): LiveData<ArrayList<UserItems>> {
        return listUsers
    }

    fun getUsersFollowing(): LiveData<ArrayList<UserItems>> {
        return listUsersFollowing
    }

    fun getUsersFollower(): LiveData<ArrayList<UserItems>> {
        return listUsersFollowers
    }

    fun getUsersDetail(): LiveData<UserDetailItems> {
        return detailUser
    }

}