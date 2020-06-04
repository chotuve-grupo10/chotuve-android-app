package com.example.chotuve_android_client.data.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Video (
    val id: String,
    val title: String,
    val url: String
) : Parcelable {
    // Acá expongo lo que necesito exponer
}