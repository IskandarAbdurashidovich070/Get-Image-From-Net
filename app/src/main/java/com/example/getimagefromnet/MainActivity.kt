package com.example.getimagefromnet

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.getimagefromnet.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var netWorkHelper: MyNetWorkHelper
    lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        netWorkHelper = MyNetWorkHelper(this)


        if (netWorkHelper.isNetworkConnected()){

            binding.tv.text = "Connected"

            requestQueue = Volley.newRequestQueue(this)

            fetchImageLoad()

//            featchObjectLoad()
        }else{
            binding.tv.text = "Disconnected"
        }

        binding.btn.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity2::class.java))
        }


    }

    private fun fetchImageLoad() {
        val imageRequest = ImageRequest("https://i.imgur.com/Nwk25LA.jpg",
            object : Response.Listener<Bitmap>{
                override fun onResponse(response: Bitmap?) {
                    binding.image.setImageBitmap(response)
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    binding.tv.text = error?.message
                }
            })

        requestQueue.add(imageRequest)
    }

    private fun featchObjectLoad() {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "http://ip.jsontest.com", null,
            object: Response.Listener<JSONObject>{//xatolik
            override fun onResponse(response: JSONObject?) {
                val strstring = response?.getString("ip")
                binding.tv.text = strstring
            }
            }, object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    binding.tv.text = error?.message
                }
            })
        requestQueue.add(jsonObjectRequest)
    }



}