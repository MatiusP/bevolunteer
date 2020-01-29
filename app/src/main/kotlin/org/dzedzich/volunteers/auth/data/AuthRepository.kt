package org.dzedzich.volunteers.auth.data

import android.util.Log
import com.haipclick.app.root.structure.ServiceRepository
import org.dzedzich.volunteers.auth.data.models.GoogleUserProfile
import org.dzedzich.volunteers.auth.data.models.SmsConfirmResponse
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.auth.data.network.AuthRSI
import org.dzedzich.volunteers.profile.data.models.Region
import org.dzedzich.volunteers.utils.rx.RxDecorator
import rx.Observable
import java.util.*

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class AuthRepository : ServiceRepository<AuthRSI>(AuthRSI::class.java) {

    fun auth(name: String?, lname: String?, token: String, googleToken: String?, region: String?, country: String?): Observable<User> {

        val map: MutableMap<String, String?> = HashMap()
        map.put("fname", name)
        map.put("lname", lname)
        map.put("token", token)
        map.put("google_token", googleToken)
        map.put("region", region)
        map.put("country", country)

        val res = service.auth(map)
        return RxDecorator<User>().decorate(res)
                .flatMap {
                    preferences.saveUser(it)
                    return@flatMap Observable.just(it)
                }
    }

    fun confirm(code : String): Observable<SmsConfirmResponse> {
        return RxDecorator<SmsConfirmResponse>().decorate(service.confirm(code))
    }

    fun googleUserProfile(token : String): Observable<GoogleUserProfile> {
        return RxDecorator<GoogleUserProfile>().decorate(service.getGoogleUserProfile("json", token))
    }

    fun regions(): Observable<List<Region>>? {
        return RxDecorator<List<Region>>().decorate(service.regions(true))
    }

    fun manualRegionList(): List<Region> {
        val list: MutableList<Region> = ArrayList()
        list.add(Region("brestskaya-oblast", "Брестская область"))
        list.add(Region("g-minsk", "Минск"))
        list.add(Region("gomelskaya-oblast", "Гомельская область"))
        list.add(Region("grodnenskaya-oblast", "Гродненская область"))
        list.add(Region("minskaya-oblast", "Минская область"))
        list.add(Region("mogilevskaya-oblast", "Могилёвская область"))
        list.add(Region("vitebskaya-oblast", "Витебская область"))
        list.add(Region("all", "Вся беларусь"))

        return list
    }
}