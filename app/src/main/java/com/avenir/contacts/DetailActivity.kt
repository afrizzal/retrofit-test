package com.avenir.contacts

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.avenir.contacts.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayout
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        val contact : Contact? = intent.getParcelableExtra("contact")

        val queue = Volley.newRequestQueue(applicationContext)
        val url = "https://api.github.com/users/"+ (contact?.name ?: "mojombo")
        val name : TextView = findViewById((R.id.name))
        val type : TextView = findViewById((R.id.type))

        val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    val json = JSONTokener(response).nextValue() as JSONObject
                    val location = json.getString("location")
                    val login_name = json.getString("name")

                    name.text = login_name
                    type.text = location

                    if (contact != null) {
                        DownloadImageFromInternet(findViewById(R.id.profile_pic) as ImageView, applicationContext).execute(contact.photo)

                        var viewPager : ViewPager = findViewById(R.id.viewpager) as ViewPager
                        var tabLayout : TabLayout = findViewById(R.id.tablayout) as TabLayout

                        val fragmentAdapter = RelationFragmentAdapter(supportFragmentManager)

                        val followerFragment = HomeFragment();
                        followerFragment.setUrl(json.getString("followers_url"))

                        val followingFragment = HomeFragment();
                        followingFragment.setUrl("https://api.github.com/users/"+contact.name+"/following")

                        fragmentAdapter.addFragment(followerFragment, "Follower")
                        fragmentAdapter.addFragment(followingFragment, "Following")

                        viewPager.adapter = fragmentAdapter
                        tabLayout.setupWithViewPager(viewPager)
                    }
//                    for (i in 0 until jsonArray.length()) {
//                        // ID
//                        val login = jsonArray.getJSONObject(i).getString("login")
//                        val id = jsonArray.getJSONObject(i).getString("id")
//                        val avatar = jsonArray.getJSONObject(i).getString("avatar_url")
//                        val type = jsonArray.getJSONObject(i).getString("type")
//                        val contact = Contact(login, id, avatar, type)
//
//                        contactArray.add(contact)
//                    }
//                    listView.layoutManager = layoutManager
//                    listView.adapter = ContactAdapter(requireActivity(), contactArray)
//
                },
                { Log.i("request status", "error") })
        queue.add(stringRequest)
    }
}