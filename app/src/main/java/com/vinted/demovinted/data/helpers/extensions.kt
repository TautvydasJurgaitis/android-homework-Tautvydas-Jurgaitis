package com.vinted.demovinted.data.helpers

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

fun Completable.retryWithDelay(
    @SuppressLint("RxSchedulerName") retryScheduler: Scheduler = Schedulers.computation(),
    delayDurationsMilliseconds: List<Long>,
    retryPredicate: (Throwable) -> Boolean = { true }
): Completable {
    return this.toObservable<Unit>()
        .retryWithDelay(retryScheduler, delayDurationsMilliseconds, retryPredicate)
        .ignoreElements()
}

fun <T> Observable<T>.retryWithDelay(
    @SuppressLint("RxSchedulerName") retryScheduler: Scheduler = Schedulers.computation(),
    delayDurationsMilliseconds: List<Long>,
    retryPredicate: (Throwable) -> Boolean = { true }
): Observable<T> {
    return this.retryWhen {
        val retries = AtomicInteger(0)
        it.flatMap { error ->
            val retry = retries.andIncrement
            if (delayDurationsMilliseconds.size <= retry || !retryPredicate(error)) {
                Observable.error<T>(error)
            } else {
                Observable.timer(delayDurationsMilliseconds[retry], TimeUnit.MILLISECONDS, retryScheduler)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <K, R> Map<K, R?>.filterValuesNotNull(): Map<K, R> {
    return filterValues { it != null } as Map<K, R>
}