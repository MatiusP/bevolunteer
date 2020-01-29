package org.dzedzich.volunteers.auth.business

import org.dzedzich.volunteers.auth.data.AuthRepository
import org.dzedzich.volunteers.auth.data.models.SmsConfirmResponse
import rx.Observable


/**
 * Created by aleksejskrobot on 14.05.17.
 */
class ConfirmInteractor(val repository: AuthRepository) {

    fun confirm(code: String): Observable<SmsConfirmResponse> {
        return repository.confirm(code)
    }

}