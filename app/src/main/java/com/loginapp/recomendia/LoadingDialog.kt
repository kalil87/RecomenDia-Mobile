package com.loginapp.recomendia

import android.app.Activity
import android.app.AlertDialog
import android.widget.TextView

class LoadingDialog(val myActivity: Activity) {
    private lateinit var isDialog: AlertDialog
    lateinit var message : TextView

    fun startLoading(messageString: String){
        val inflater = myActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)
        message = dialogView.findViewById(R.id.textLoading) as TextView
        message.text = messageString
        val builder = AlertDialog.Builder(myActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun stopLoading(){
        isDialog.dismiss()
    }
}