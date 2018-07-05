package com.example.tedmosby.kotlinyoutube

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telecom.Connection
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recyclerview_main.setBackgroundColor(Color.GRAY)

        recyclerview_main.layoutManager = LinearLayoutManager(this)
//      recyclerview_main.adapter = MainAdapter()

        fetchjson()

    }

    fun fetchjson(){
        val url = "http://api.letsbuildthatapp.com/youtube/home_feed"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val homefeed = gson.fromJson(body, HomeFeed::class.java)


                runOnUiThread {
                    recyclerview_main.adapter = MainAdapter(homefeed)
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute")
            }
        })
    }
}

class HomeFeed(val videos: List<Video>)

class Video(val id: Int , val name: String, val link: String , val imageUrl: String, val numberOfViews: Int, val channel: Channel)

class Channel(val name: String, val profileImageUrl: String)