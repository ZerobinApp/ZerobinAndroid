package com.shop.zerobin.ui.common

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException


class MySharedPreference(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)



    fun setStringArrayPref(
        key: String,
        values: ArrayList<Int>
    ) {
        val editor = prefs.edit()
        val a = JSONArray()
        for (i in 0 until values.size) {
            a.put(values[i])
        }
        if (values.isNotEmpty()) {
            editor.putString(key, a.toString())
        } else {
            editor.putString(key, null)
        }
        editor.apply()
    }

    fun getStringArrayPref(
        key: String
    ): ArrayList<Int>? {
        val json = prefs.getString(key, null)
        val urls = ArrayList<Int>()
        if (json != null) {
            try {
                val a = JSONArray(json)
                for (i in 0 until a.length()) {
                    val url = a.optInt(i)
                    urls.add(url)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return urls
    }
}


