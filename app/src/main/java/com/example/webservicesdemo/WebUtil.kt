package com.example.webservicesdemo

import android.util.Log
import android.widget.Toast
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import javax.net.ssl.HttpsURLConnection

class WebUtil {
    companion object{
        fun simpleHTTPRequest(targetURL : String){
            var url = URL(targetURL)
            var httpsURLConnection = url.openConnection() as HttpsURLConnection

            httpsURLConnection.connect()

            mt("response code: ${httpsURLConnection.responseCode}")
            mt("response message : ${httpsURLConnection.responseMessage}")
            mt("Content Length ${httpsURLConnection.contentLength}")
            mt("Encoding : ${httpsURLConnection.contentEncoding}")

            var inStream = httpsURLConnection.inputStream

            var data = ByteArray(1024 * 1)
            var count = 0
            var buffer = StringBuffer()

            count = inStream.read(data)
            while (count != -1){
                buffer.append(String(data,0,count))
                count = inStream.read(data)
            }

            inStream.close()

            Log.e("tag",buffer.toString())

        }

        private fun mt(text: String){
            Log.e("tag",text)
        }

        fun getAllUsers() : ArrayList<User>? {
            var url = URL("https://reqres.in/api/users?page=1")
            var httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()

            Log.e("tag","Res Code ${httpURLConnection.responseCode}")

            if(httpURLConnection.responseCode != 200){
                return null
            }

            var responseBuffer = StringBuffer()
            var count = 0
            var data = ByteArray(1024 * 1)
            var inStream = httpURLConnection.inputStream

            count = inStream.read(data)
            while(count != -1){
                responseBuffer.append(String(data,0,count))
                count = inStream.read(data)
            }
            inStream.close()

            mt(responseBuffer.toString())

            var jsonRoot = JSONObject(responseBuffer.toString())
            if(jsonRoot.has("page")){
                mt("page : ${jsonRoot.getInt("page")}")
            }

            mt("total pages: ${jsonRoot.getInt("total_pages")}")

            var jUserArray = jsonRoot.getJSONArray("data")
            var users = ArrayList<User>()

            for(i in 0 until jUserArray.length()-1){
                var jEachUser = jUserArray.getJSONObject(i)
                users.add(
                    User(
                        jEachUser.getInt("id"),
                        jEachUser.getString(("email")),
                        jEachUser.getString("first_name"),
                        jEachUser.getString("last_name"),
                        jEachUser.getString("avatar")
                    )
                )
            }
            return  users
        }
    }
}