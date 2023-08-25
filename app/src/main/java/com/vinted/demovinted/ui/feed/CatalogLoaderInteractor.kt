package com.vinted.demovinted.data.interactors

import com.vinted.demovinted.data.helpers.DominantBrandResolver
import com.vinted.demovinted.data.models.FilteringProperties
import com.vinted.demovinted.data.network.api.Api
import com.vinted.demovinted.data.network.responses.FetchItemsResult
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.internal.functions.Functions.toFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.UnicastSubject
import javax.inject.Inject

class CatalogLoaderInteractor @Inject constructor(api: Api) {

    private val dominantBrandResolver = DominantBrandResolver()

    private val itemProvider: ItemProvider =
        CatalogItemProvider(
            api = api,
            filteringProperties = FilteringProperties.Default(),
            itemsPerPage = ITEMS_PER_PAGE,
            dominantBrandResolver = dominantBrandResolver
        )

    private val itemsChangedObservable = UnicastSubject.create<Notification<FetchItemsResult>>()
    private val loadNextPage = PublishSubject.create<Unit>()

    private var flow: Disposable? = null

    val dataLoaded: Observable<Notification<FetchItemsResult>> =
        itemsChangedObservable.publish().autoConnect()

    private fun initializeFlowableSequence(): Disposable {
        return Flowable.zipArray(
            toFunction(BiFunction<FetchItemsResult, Unit, FetchItemsResult> { contentList, _ ->
                contentList
            }),
            true,
            1,
            itemProvider.getItemFlow().subscribeOn(Schedulers.io()),
            loadNextPage.toFlowable(BackpressureStrategy.BUFFER)
        )
            .subscribeBy(
                onNext = {
                    itemsChangedObservable.onNext(Notification.createOnNext(it))
                },
                onError = {
                    itemsChangedObservable.onNext(Notification.createOnError(it))
                }
            )
    }

    fun init() {
        flow = initializeFlowableSequence()
        requestMoreItems()
    }

    fun requestMoreItems() {
        loadNextPage.onNext(Unit)
    }

    companion object {
        const val ITEMS_PER_PAGE = 20
    }
}