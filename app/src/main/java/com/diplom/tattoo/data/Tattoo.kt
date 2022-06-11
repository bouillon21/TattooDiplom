package com.diplom.tattoo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tattoo(
    val title: String = "",
    val des: String = "",
    val photoUrl: List<String> = listOf(""),
    val color: List<String> = listOf(""),
    val recommended: List<String> = listOf(""),
) : Parcelable
