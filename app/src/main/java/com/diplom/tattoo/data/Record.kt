package com.diplom.tattoo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Record(
    val UID: String = "",
    val master: String = "",
    val tattoo: String = "",
    val data: String = "",
    val time: String = "",
    val photoUrl: String = ""
) : Parcelable
