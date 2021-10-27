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

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var contactArray : ArrayList<Contact>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val listView : ListView = root.findViewById(R.id.contact_list) as ListView
        val queue = Volley.newRequestQueue(activity)
        val url = "https://api.github.com/users"
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

                    listView.adapter = ContactAdapter(requireActivity(), contactArray)
                },
                { Log.i("request status", "error") })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

        return root
    }
}