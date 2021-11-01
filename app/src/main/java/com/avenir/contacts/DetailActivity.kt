package com.avenir.contacts

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        val contact : Contact? = intent.getParcelableExtra("contact")

        val name : TextView = findViewById((R.id.name))
        val type : TextView = findViewById((R.id.type))

        if (contact != null) {
            name.text = contact.name
            type.text = contact.type
            DownloadImageFromInternet(findViewById(R.id.profile_pic) as ImageView, applicationContext).execute(contact.photo)
        }
    }
}