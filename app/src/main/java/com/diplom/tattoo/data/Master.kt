package com.diplom.tattoo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Master(
    val firstName: String = "",
    val lastName: String = "",
    val des:String = "",
    val photoUrl: String = "null"

) : Parcelable