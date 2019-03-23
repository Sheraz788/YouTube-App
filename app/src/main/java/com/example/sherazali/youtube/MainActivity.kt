package com.example.sherazali.youtube

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)


        fetchJSON()

    }

    fun fetchJSON(){

        println("Attemting to fetch JSON")

        var URL = "https://api.letsbuildthatapp.com/youtube/home_feed"

        val request = Request.Builder().url(URL).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

                println("Failed to execute request ...")

            }

            override fun onResponse(call: Call, response: Response) {

                var body = response.body()?.string()
               // println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(homeFeed)
                }


            }


        })






    }

}

class HomeFeed(val videos: List<Video>)

class Video(val id: Int, val name: String, val link: String, val imageUrl: String, val numberOfViews: String, val channel: Channel)

class Channel(val name: String, val profileImageUrl: String)
