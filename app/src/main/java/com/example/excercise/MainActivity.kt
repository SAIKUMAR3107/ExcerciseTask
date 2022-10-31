package com.example.excercise

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    var id : String? = null
    var farm : String? = null
    var server : String? = null
    var secret : String? = null
    var image : ImageView? = null
    var title : String? = null
    var urlList :ArrayList<String>? = arrayListOf()
    var titleList : ArrayList<String>? = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CallAPIloginAsyncTask().execute()


    }


    private inner class CallAPIloginAsyncTask():AsyncTask<Any,Void,String>(){

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            responseData(result)
        }
        override fun doInBackground(vararg params: Any?): String {
            var result :String
            var connection : HttpsURLConnection? = null
            try {
                val url = URL("https://www.flickr.com/services/rest/?method=flickr.galleries.getPhotos&api_key=f9736f4d370f9c7115a952951b506569&gallery_id=66911286-72157647277042064&format=json&nojsoncallback=1")
                connection = url.openConnection() as HttpsURLConnection
                connection.doInput = true
                connection.doOutput = true

                val httpResult : Int = connection.responseCode
                if(httpResult == HttpsURLConnection.HTTP_OK)
                {
                    val inputStream = connection.inputStream
                    val reader  = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line : String?
                    try {
                        while(reader.readLine().also { line= it } != null){
                            stringBuilder.append(line+"\n")
                        }
                    }catch (e : IOException){
                        e.printStackTrace()
                    }finally {
                        try {
                            inputStream.close()
                        }catch (e: IOException){
                            e.printStackTrace()
                        }
                    }
                    result = stringBuilder.toString()
                }
                else{
                    result = connection.responseMessage
                }
            }catch (e :SocketTimeoutException){
                result = "Connection TimeOut"
            }catch (e  : Exception){
                result = "Error " + e.message
            }finally {
                connection?.disconnect()
            }
            return result
        }

    }

    fun responseData(result : String?) {
        val responseData = Gson().fromJson(result,ResponseData::class.java)
        for(item in responseData.photos.photo.indices){
            id = responseData.photos.photo[item].id
            farm = responseData.photos.photo[item].farm.toString()
            server = responseData.photos.photo[item].server
            secret = responseData.photos.photo[item].secret
            title = responseData.photos.photo[item].title
            var url = "https://farm" +farm.toString()+ ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg"
            Log.d("image","$url")
            image?.let { Glide.with(this).load(url).into(it) }
            urlList?.add(url)
            titleList?.add(title!!)
        }
        val adaptor = urlList?.let { titleList?.let { it1 -> GridItemAdaptor(it, it1) } }
        val gridLayout = GridLayoutManager(this,2)
        gridItem.layoutManager = gridLayout
        gridItem.adapter = adaptor
        Log.d("List","$urlList")
        Log.d("titleList","$titleList")


    }

}