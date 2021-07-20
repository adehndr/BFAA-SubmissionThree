package com.example.consumerapp.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItems(
    var id: Int = 0,
    var avatar: String = "",
    var userName: String = ""
) : Parcelable