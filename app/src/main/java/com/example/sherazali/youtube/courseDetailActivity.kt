package com.example.sherazali.youtube

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.courses_row.view.*
import okhttp3.*
import java.io.IOException

class courseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        recyclerView_main.layoutManager = LinearLayoutManager(this)

        supportActionBar?.title = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE)

        fetchJSON()

    }

    private fun fetchJSON(){

        try {

            var courseId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)

            var courseURL = "https://api.letsbuildthatapp.com/youtube/course_detail?id=$courseId"


            var client = OkHttpClient()


            var request = Request.Builder().url(courseURL).build()


            client.newCall(request).enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {

                    var body = response.body()?.string()

                    Log.e("Body", "$body")
                    var gson = GsonBuilder().create()


                    var courseLesson = gson.fromJson(body, Array<CourseLesson>::class.java)

                    runOnUiThread {

                        recyclerView_main.adapter = CourseDetailAdapter(courseLesson)

                    }


                }

                override fun onFailure(call: Call, e: IOException) {

                }

            })
        }catch (e: Exception){

            Log.e("Course Detail Exception", "$e")

        }



    }

    class CourseLesson(var name: String, var duration: String, var number: Int, var imageUrl: String, var link: String)

    private class CourseDetailAdapter(var courseLesson: Array<CourseLesson>) : RecyclerView.Adapter<CourseDetailViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, position: Int): CourseDetailViewHolder {


            var inflater = LayoutInflater.from(parent.context)
            var courseView = inflater.inflate(R.layout.courses_row, parent, false)

            return CourseDetailViewHolder(courseView)

        }

        override fun getItemCount(): Int {
            return courseLesson.size
        }

        override fun onBindViewHolder(holder: CourseDetailViewHolder, position: Int) {

            var course = courseLesson[position]

            holder.view.textView_course_title.text = course.name
            holder.view.textView_course_duration.text = course.duration

            var imageId = holder.view.imageView_course_thumbnail
            Picasso.get().load(course.imageUrl).into(imageId)


            holder.courseLesson = course
        }


    }


     class CourseDetailViewHolder(val view: View, var courseLesson: CourseLesson? = null) : RecyclerView.ViewHolder(view){


        companion object {

            var COURSE_LESSON_KEY = "COURSE_LESSON_LINK"

        }

        init {


           view.setOnClickListener {

               var intent = Intent(view.context, CourseLessonActivity::class.java)
               intent.putExtra(COURSE_LESSON_KEY, courseLesson?.link)
               view.context.startActivity(intent)

           }



        }

    }


}