package com.example.tedmosby.kotlinyoutube

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.coursedetail.view.*
import okhttp3.*
import java.io.IOException

class CourseDetailActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerview_main.layoutManager = LinearLayoutManager(this)

        // change navbar title

        //recyclerview_main.adapter = CourseDetailAdapter()

       val navbartitle =  intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navbartitle



        fetchJSON()
    }


    private fun fetchJSON(){
        val videoid = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY , 1)
        val coursedetailurl = "http://api.letsbuildthatapp.com/youtube/course_detail?id="+ videoid
        val client = OkHttpClient()
        val request = Request.Builder().url(coursedetailurl).build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                println("Program Failed to execute")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val coursefeed = gson.fromJson(body, Array<CourseLesson>::class.java)

                runOnUiThread {
                    recyclerview_main.adapter = CourseDetailAdapter(coursefeed)
                }




            }
        })

    }


    private class CourseDetailAdapter(val courses: Array<CourseLesson>): RecyclerView.Adapter<CourseDetailActivity.CourseDetailViewHolder>(){


        //val videotitles = listOf<String>("Downloading Itunes on Mac", "Downloading itunes on windows", "Air Mac Unboxing")
        override fun getItemCount(): Int {
            return courses.size
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDetailActivity.CourseDetailViewHolder {

            val layoutInflator = LayoutInflater.from(parent?.context)
            val customview = layoutInflator.inflate(R.layout.coursedetail, parent, false)
            return CourseDetailActivity.CourseDetailViewHolder(customview)
        }

        override fun onBindViewHolder(holder: CourseDetailActivity.CourseDetailViewHolder, position: Int) {

            val course = courses.get(position)

            holder.CustomView.coursename.text = course.name
            holder.CustomView.duration.text = course.duration

            val videoimage = holder?.CustomView.videoimage
            Picasso.with(holder?.CustomView.context).load(course.imageUrl).into(videoimage)

            holder?.courseLesson = course

        }
    }

     class CourseDetailViewHolder(val CustomView: View, var courseLesson: CourseLesson? = null): RecyclerView.ViewHolder(CustomView){

        companion object {
            val COURSE_LESSON_LINK_KEY = "COURSE_LESSON_LINK"
        }

        init{

            CustomView.setOnClickListener {
                val intent = Intent(CustomView.context, CourseLessonActivity:: class.java)
                intent.putExtra(COURSE_LESSON_LINK_KEY, courseLesson?.link)

                CustomView.context.startActivity(intent)
            }
        }

    }
}

class CourseLesson(val name: String , val duration: String , val number: Int , val imageUrl: String , val link: String)