package org.dzedzich.volunteers.root.di

import android.content.Context
import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.root.di.scopes.AppScope
import org.dzedzich.volunteers.root.di.scopes.ApplicationContext

/**
 * Created by aleksejskrobot on 22.03.17.
 */
@Module
class ContextModule(val context: Context) {

    @Provides
    @AppScope
    @ApplicationContext
    fun context(): Context {
        return context
    }

}