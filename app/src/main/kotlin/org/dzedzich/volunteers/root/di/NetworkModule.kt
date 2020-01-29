package org.dzedzich.volunteers.root.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.dzedzich.volunteers.root.di.scopes.AppScope
import org.dzedzich.volunteers.root.di.scopes.ApplicationContext
import org.dzedzich.volunteers.utils.Constants
import org.dzedzich.volunteers.utils.HeaderInterceptor
import org.dzedzich.volunteers.utils.PreferenceManager
import timber.log.Timber
import java.io.File


/**
 * Created by aleksejskrobot on 22.03.17.
 */
@Module(includes = arrayOf(ContextModule::class))
class NetworkModule {

    @Provides
    @AppScope
    fun logginInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.i(message) })
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    @AppScope
    fun headerInterceptor(preferenceManager: PreferenceManager): HeaderInterceptor {
        return HeaderInterceptor(preferenceManager.token())
    }


    @Provides
    @AppScope
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, Constants.CACHE_SIZE)
    }

    @Provides
    @AppScope
    fun gson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @AppScope
    fun cacheFile(@ApplicationContext context: Context): File {
        return File(context.cacheDir, Constants.OKHTTP_CACHE)
    }

    @Provides
    @AppScope
    fun preferencesManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }

    @Provides
    @AppScope
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, headerInterceptor: HeaderInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build()
    }

}

