package com.github.vkguests.utils.rx

import rx.Observable
import rx.Scheduler
import rx.Single

/**
 * Created by aleksejskrobot on 12.10.16.
 */
abstract class RxSchedulersAbs {

    abstract fun getMainThreadScheduler(): Scheduler
    abstract fun getIOScheduler(): Scheduler
    abstract fun getComputationScheduler(): Scheduler

    fun <T> getIOToMainTransformer(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> { objectObservable -> objectObservable.subscribeOn(getIOScheduler()).observeOn(getMainThreadScheduler()) }
    }

    fun <T> getIOToMainTransformerSingle(): Single.Transformer<T, T> {
        return Single.Transformer<T, T> { objectObservable -> objectObservable.subscribeOn(getIOScheduler()).observeOn(getMainThreadScheduler()) }
    }

    fun <T> getComputationToMainTransformer(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> { objectObservable -> objectObservable.subscribeOn(getComputationScheduler()).observeOn(getMainThreadScheduler()) }
    }

    fun <T> getComputationToMainTransformerSingle(): Single.Transformer<T, T> {
        return Single.Transformer<T, T> { objectObservable -> objectObservable.subscribeOn(getComputationScheduler()).observeOn(getMainThreadScheduler()) }
    }


}