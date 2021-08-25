package com.spsoft.rawgames.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message:String,time:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,message,time).show()
}