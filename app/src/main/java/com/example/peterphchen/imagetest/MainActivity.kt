package com.example.peterphchen.imagetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var image:ImageView
    val OriginUrl = "http://ajaysblog.pixnet.net/blog/post/349007749"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.testImage)

        val Pixnet = PixnetUrl(OriginUrl)
        Log.d(TAG,"BaseUrl: "+Pixnet.baseUrl)
        Log.d(TAG,"SubUrl: "+Pixnet.subUrl)


        val retrofit = Retrofit.Builder()
            .addConverterFactory(PictureAdapter.Factory)
            .baseUrl(Pixnet.baseUrl)
            .build()

        retrofit.create(BlogAPI::class.java).getURLs(Pixnet.subUrl)
            .enqueue(object :Callback<ArrayList<String>>{
                override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                    Log.d(TAG,"Failed to retrieve urls. "+ t.message)
                }

                override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                    Picasso.with(this@MainActivity).load(response.body()!!.get(1)).into(image)
                }
            })

    }

    class PixnetUrl(rawUrl: String){
        val baseUrl:String
        val subUrl:String
        init {
            val index = rawUrl.indexOf("post/")+5
            baseUrl = rawUrl.substring(0,index).trim()
            subUrl = rawUrl.substring(index).trim()
        }
    }

}
