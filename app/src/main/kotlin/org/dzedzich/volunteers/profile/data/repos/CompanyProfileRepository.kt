package org.dzedzich.volunteers.profile.data.repos

import com.haipclick.app.root.structure.ServiceRepository
import org.dzedzich.volunteers.profile.data.models.Company
import org.dzedzich.volunteers.profile.data.network.CompanyProfileRSI
import org.dzedzich.volunteers.utils.rx.RxDecorator
import rx.Observable

/**
 * Created by aleksejskrobot on 15.05.17.
 */
class CompanyProfileRepository : ServiceRepository<CompanyProfileRSI>(CompanyProfileRSI::class.java) {

    fun getCompanyProfile(id: Int?): Observable<Company> {
        return RxDecorator<Company>().decorate(service.getCompany(id))
    }

}