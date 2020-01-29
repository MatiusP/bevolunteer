package org.dzedzich.volunteers.root.di

import android.util.Log
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.dzedzich.volunteers.utils.HeaderInterceptor
import org.dzedzich.volunteers.utils.PreferenceManager
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Created by aleksejskrobot on 22.03.17.
 */
@Module(includes = arrayOf(NetworkModule::class))
class RestModule {

    @Provides
    fun retrofit(@Named("url") url: String, okHttpClient: OkHttpClient, gson: Gson, preferenceManager: PreferenceManager): Retrofit {
        okHttpClient.interceptors().forEach {
            if(it is HeaderInterceptor) {
                it.setToken(preferenceManager.user()?.token)
            }
        }

        Log.d("back_url", url)

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(url)
                .build()
    }

    @Provides @Named("url")
    fun url(): String {
        return "https://beavolunteer.dzedzich.org/"
    }
}