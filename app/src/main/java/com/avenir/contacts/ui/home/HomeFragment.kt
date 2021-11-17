package com.avenir.contacts.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.avenir.contacts.Contact
import com.avenir.contacts.ContactAdapter
import com.avenir.contacts.R
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class HomeFragment() : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var contactArray : ArrayList<Contact>
    private var url : String = "https://api.github.com/users"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val itemView = inflater.inflate(R.layout.contact_row, container, false)
        val listView : RecyclerView = root.findViewById(R.id.contact_list) as RecyclerView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
        val queue = Volley.newRequestQueue(activity)
        Log.i("URL==================", url.toString())

//        "https://api.github.com/users"
        contactArray = ArrayList()

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    val jsonArray = JSONTokener(response).nextValue() as JSONArray
                    for (i in 0 until jsonArray.length()) {
                        // ID
                        val login = jsonArray.getJSONObject(i).getString("login")
                        val id = jsonArray.getJSONObject(i).getString("id")
                        val avatar = jsonArray.getJSONObject(i).getString("avatar_url")
                        val type = jsonArray.getJSONObject(i).getString("type")
                        val contact = Contact(login, id, avatar, type)

                        contactArray.add(contact)
                    }
                    listView.layoutManager = layoutManager
                    listView.adapter = ContactAdapter(requireActivity(), contactArray)
//
                },
                { Log.i("request status", "error") })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

        return root
    }

    public fun setUrl(url : String){
        this.url = url
    }
}