package com.avenir.contacts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ContactAdapter(private val context: Activity, private val contactList: ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.ConcreteViewHolder>() {
//    @SuppressLint("ViewHolder", "InflateParams")
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val inflater : LayoutInflater = LayoutInflater.from(context)
//        val view : View = inflater.inflate(R.layout.contact_row, null)
//        DownloadImageFromInternet(view.findViewById(R.id.profile_pic) as ImageView).execute(contactList[position].photo)
//        val username : TextView = view.findViewById(R.id.name)
//        val userType : TextView = view.findViewById(R.id.user_type)
//
//        username.text = contactList[position].name
//        userType.text = contactList[position].type
//        return view
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
//        init {
//            Toast.makeText(context, "Please wait, it may take a few minute...",     Toast.LENGTH_SHORT).show()
//        }
//        override fun doInBackground(vararg urls: String): Bitmap? {
//            val imageURL = urls[0]
//            var image: Bitmap? = null
//            try {
//                val `in` = java.net.URL(imageURL).openStream()
//                image = BitmapFactory.decodeStream(`in`)
//            }
//            catch (e: Exception) {
//                Log.e("Error Message", e.message.toString())
//                e.printStackTrace()
//            }
//            return image
//        }
//        override fun onPostExecute(result: Bitmap?) {
//            imageView.setImageBitmap(result)
//        }
//    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcreteViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.contact_row, parent, false)
        return ConcreteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.count()
    }

    override fun onBindViewHolder(holder: ConcreteViewHolder, position: Int) {
        holder.username.text = contactList[position].name
        holder.userType.text = contactList[position].type
        DownloadImageFromInternet(holder.view.findViewById(R.id.profile_pic) as ImageView, context).execute(contactList[position].photo)
        Log.v("testing====", contactList[position].name)
        holder.view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                Log.v("intent====", "Keluar")
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("contact", contactList[position])
                context.startActivity(intent)
            }

        })
    }

    inner class ConcreteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var username : TextView
        var userType : TextView
        var view : View
        init {
            username = itemView.findViewById(R.id.name)
            userType = itemView.findViewById(R.id.user_type)
            view = itemView
        }
    }
}