package com.kiluss.sonretexkotlin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    internal val title: String,
    internal val author: String,
    internal val datetime: String,
    internal val description: String,
) : Parcelable
