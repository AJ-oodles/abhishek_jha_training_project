package com.example.abhishek_jha_training_project

import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val base_url = "https://jsonplaceholder.typicode.com/"
    lateinit var myAdapter: MyAdapter
    lateinit var linerLayoutManager: LinearLayoutManager
    lateinit var recyclerview: RecyclerView // Declare recyclerview here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview = findViewById(R.id.recycleview) // Initialize recyclerview here
        recyclerview.setHasFixedSize(true)
        linerLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linerLayoutManager

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url)
            .build()
            .create(ApiService::class.java)

        val retrofitdata = retrofitBuilder.getData()
        retrofitdata.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val responseBody = response.body()!!
                myAdapter = MyAdapter(baseContext, responseBody)
                myAdapter.notifyDataSetChanged()
                recyclerview.adapter = myAdapter // Now recyclerview is accessible here
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailuer:" + t.message)
            }
        })
    }
}
