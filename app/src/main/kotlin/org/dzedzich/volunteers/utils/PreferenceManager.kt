package org.dzedzich.volunteers.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.dzedzich.volunteers.auth.data.models.User
import java.util.*

class PreferenceManager(private var context: Context) {

    private var helper: PreferencesHelper
    private var gson: Gson = GsonBuilder().create()

    init {
        this.helper = PreferencesHelper(context)
    }

    fun user(): User? {
        return gson.fromJson(helper.getString("user"), User::class.java)
    }

    fun saveUser(user: User) {
        helper.setString("user", gson.toJson(user))
    }

    fun saveToken(token: String?) {
        Log.d("PrefManager:saveToken", token)
        this.helper.setString(Constants.HEADERS.TOKEN, token)
    }

    fun token(): String {
        return this.helper.getString(Constants.HEADERS.TOKEN)
    }

    fun getSwitcherSetting(name: String): Boolean {
        return this.helper.getBool(name, true)
    }

    fun saveSwitcherSetting(name: String, enabled: Boolean?) {
        this.helper.setBool(name, enabled)
    }

    fun isSwitchWasChecked(name: String): Boolean {
        return this.helper.getBool("first_switch_$name")
    }

    fun WasChecked(name: String) {
        this.helper.setBool("first_switch_$name", true)
    }

    fun saveUsersAvatar(path: String?) {
        val user = user()
        if(user != null) {
            user.avatar = path
            saveUser(user)
        }
    }

    private inner class PreferencesHelper(context: Context) {

        private val pref: SharedPreferences = context.getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE)

        fun clear() {
            pref.edit().clear().apply()
        }

        fun getBool(name: String, default: Boolean = false): Boolean {
            return pref.getBoolean(name, default)
        }

        fun getBool(name: String): Boolean {
            return pref.getBoolean(name, false)
        }

        fun setBool(name: String, value: Boolean?) {
            pref.edit().putBoolean(name, value ?: true).apply()
        }

        fun setBool(name: String, value: Boolean) {
            pref.edit().putBoolean(name, value).apply()
        }

        fun getString(name: String): String {
            return pref.getString(name, Constants.EMPTY)
        }

        fun setString(name: String, value: String?) {
            pref.edit().putString(name, value).apply()
        }

        fun getInt(name: String): Int {
            return pref.getInt(name, 0)
        }

        fun getInt(name: String, def: Int): Int {
            return pref.getInt(name, def)
        }

        fun setInt(name: String, value: Int) {
            pref.edit().putInt(name, value).apply()
        }

        fun setDouble(name: String, value: Double) {
            pref.edit().putString(name, value.toString()).apply()
        }

        fun getDouble(name: String): Double {
            return pref.getString(name, "0").toDouble()
        }

        fun getStringSet(setName: String): MutableSet<String>? {
            return pref.getStringSet(setName, HashSet<String>())
        }

        fun setStringSet(setName: String, set: HashSet<String>) {
            pref.edit().putStringSet(setName, set).apply()
        }

    }


}
