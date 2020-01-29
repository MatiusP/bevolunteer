package com.github.vkguests.utils.rx

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by aleksejskrobot on 12.10.16.
 */
class RxSchedulers : RxSchedulersAbs() {

    override fun getMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun getIOScheduler(): Scheduler {
        return Schedulers.io()
    }

    override fun getComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }

}