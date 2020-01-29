package org.dzedzich.volunteers.root.structure

import com.haipclick.app.root.VolunteersApp
import org.dzedzich.volunteers.utils.PreferenceManager
import retrofit2.Retrofit

/**
 * Created by alexscrobot on 21.04.17.
 */
abstract class BaseRepository : IRepository {

    final override var retrofit: Retrofit = VolunteersApp.component.retrofit()
    final override var preferences: PreferenceManager = VolunteersApp.component.preferencesManager()

}