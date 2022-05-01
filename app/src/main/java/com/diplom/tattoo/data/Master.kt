package com.diplom.tattoo.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Master(
    val title: String,
    @DrawableRes val imageAndroid: Int
) : Parcelable