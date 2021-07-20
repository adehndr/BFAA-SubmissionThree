package com.example.submissionthree

import android.database.Cursor
import com.example.submissionthree.dataclass.UserItems
import java.lang.Exception

object MappingHelper {
    fun mapCursorToObject(notesCursor: Cursor?): UserItems {
        var user = UserItems()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColums._ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColums.USERNAME))
            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColums.AVATAR))
            user = UserItems(id, avatar, username)
        }

        return user
    }

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserItems> {
        val usersList = ArrayList<UserItems>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColums._ID))
                val username =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColums.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColums.AVATAR))
                usersList.add(UserItems(id, avatar, username))
            }
        }
        return usersList
    }
}