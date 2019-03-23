package com.example.sherazali.youtube

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.videos.view.*

class MainAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {

    val videosTitle = listOf("First Title", "Second Title", "Third Title")

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        var row = inflater.inflate(R.layout.videos, parent, false)

        return CustomViewHolder(row)

    }

    override fun getItemCount(): Int {
        return homeFeed.videos.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        var video = homeFeed.videos.get(position)
        holder.view.title_txt.text = video.name
        holder.view.channel_name_txt.text = video.channel.name + " ・  20K Views\n" + "4 days ago"

        val videoImageThumbnail = holder.view.main_video_image
        Picasso.get().load(video.imageUrl).into(videoImageThumbnail)

        val channelImage = holder.view.channel_profile_image
        Picasso.get().load(video.channel.profileImageUrl).into(channelImage)


        holder.video = video


    }


}

class CustomViewHolder(var view: View, var video : Video? = null) : RecyclerView.ViewHolder(view){

    companion object {

        var VIDEO_TITLE = "VIDEO_TITLE"
        var VIDEO_ID_KEY = "VIDEO_ID"

    }

    init {
        view.setOnClickListener {

            var intent = Intent(view.context, courseDetailActivity::class.java)

            intent.putExtra(VIDEO_TITLE, video?.name)
            intent.putExtra(VIDEO_ID_KEY, video?.id)

            Log.e("VIDEO ID", "${video?.id}")

            view.context.startActivity(intent)

        }
    }

}