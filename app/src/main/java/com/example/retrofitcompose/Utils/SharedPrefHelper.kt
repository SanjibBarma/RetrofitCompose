package com.example.retrofitcompose.Utils


import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String){
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun deleteData(key: String){
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clearData(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun getAllUsers(): List<Pair<String, String>> {
        val allUsers = mutableListOf<Pair<String, String>>()
        val keys = sharedPreferences.all.keys
        for (key in keys) {
            val userData = sharedPreferences.getString(key, null)
            userData?.let {
                allUsers.add(Pair(key, it))
            }
        }
        return allUsers
    }
}
