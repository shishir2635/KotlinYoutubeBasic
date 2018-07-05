package com.example.tedmosby.kotlinyoutube

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter(val homefeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>(){

    //val videotitles = listOf<String>("Downloading Itunes on Mac", "Downloading itunes on windows", "Air Mac Unboxing")

    override fun getItemCount(): Int {
        return homefeed.videos.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder{
        val layoutInflator = LayoutInflater.from(parent?.context)
        val cellforrow = layoutInflator.inflate(R.layout.video_row, parent , false)
        return CustomViewHolder(cellforrow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val video = homefeed.videos.get(position)
        holder.view.video_title.text = video.name
        holder.view.channelName.text = video.channel.name

        val videoimage = holder?.view?.videoview
        Picasso.with(holder?.view?.context).load(video.imageUrl).into(videoimage)

        val channelimage = holder?.view?.channelview
        Picasso.with(holder?.view?.context).load(video.channel.profileImageUrl).into(channelimage)

        holder?.video = video
    }
}

class CustomViewHolder(val view: View, var video: Video? = null): RecyclerView.ViewHolder(view) {

    companion object {
        val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context , CourseDetailActivity::class.java)

            intent.putExtra(VIDEO_TITLE_KEY, video?.name)
            intent.putExtra(VIDEO_ID_KEY, video?.id)

            view.context.startActivity(intent)
        }
    }

}