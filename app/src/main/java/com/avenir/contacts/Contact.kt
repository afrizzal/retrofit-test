package com.avenir.contacts

import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.parcelize.Parcelize

@Parcelize
class Contact(var name: String, var id: String, var photo: String, var type: String) : Parcelable{
}