package com.example.consumerapp.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailItems(
    var avatar: String = "",
    var userFullName: String = "",
    var userName: String = "",
    var location: String = "",
    var company: String = "",
) : Parcelable
