package com.example.tedmosby.kotlinyoutube

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activitycourselesson.*

class CourseLessonActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activitycourselesson)

        val courselink = intent.getStringExtra(CourseDetailActivity.CourseDetailViewHolder.COURSE_LESSON_LINK_KEY)

        coursewebview.settings.javaScriptEnabled = true
        coursewebview.settings.loadWithOverviewMode = true
        coursewebview.settings.useWideViewPort = true

        coursewebview.loadUrl(courselink)
    }
}