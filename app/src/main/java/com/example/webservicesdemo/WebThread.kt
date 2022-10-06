package com.example.webservicesdemo

import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import java.util.ArrayList

class WebThread(
    var handler : Handler?
):AsyncTask<String,Any?,ArrayList<User>?>(){
    override fun doInBackground(vararg params: String?): ArrayList<User>? {
       return WebUtil.getAllUsers()
    }

    override fun onPostExecute(result: ArrayList<User>?) {
        super.onPostExecute(result)
        var message = Message()
        message.obj = result

        handler?.sendMessage(message)
    }
}