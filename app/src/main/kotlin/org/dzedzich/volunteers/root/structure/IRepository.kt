package org.dzedzich.volunteers.root.structure

import org.dzedzich.volunteers.utils.PreferenceManager
import retrofit2.Retrofit

/**
 * Created by alexscrobot on 21.04.17.
 */
interface IRepository {

    val retrofit: Retrofit
    val preferences: PreferenceManager

}