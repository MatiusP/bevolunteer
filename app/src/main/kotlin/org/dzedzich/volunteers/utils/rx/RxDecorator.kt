package org.dzedzich.volunteers.utils.rx

import com.github.vkguests.utils.rx.RxSchedulers
import rx.Observable

/**
 * Created by aleksejskrobot on 26.11.16.
 */
class RxDecorator<T> {

    fun decorate(observable: Observable<T>): Observable<T> {
        return observable.subscribeOn(RxSchedulers().getIOScheduler()).observeOn(RxSchedulers().getMainThreadScheduler())
    }

}