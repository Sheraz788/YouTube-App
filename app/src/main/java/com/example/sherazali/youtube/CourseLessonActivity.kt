package com.example.sherazali.youtube

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_course_lesson.*

class CourseLessonActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_course_lesson)

        var intent = intent
        var courseLink = intent.getStringExtra(courseDetailActivity.CourseDetailViewHolder.COURSE_LESSON_KEY)
        Log.e("COurse Link", "$courseLink")
        Log.e("COurse Link", "Error")
        webview_courseLesson.settings.javaScriptEnabled = true
        webview_courseLesson.settings.loadWithOverviewMode = true
        webview_courseLesson.settings.useWideViewPort = true
        webview_courseLesson.webViewClient = WebViewClient()

        webview_courseLesson.loadUrl("$courseLink")

    }

}