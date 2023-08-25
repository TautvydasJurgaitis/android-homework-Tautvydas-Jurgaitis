package com.vinted.demovinted.ui.feed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinted.demovinted.data.interactors.CatalogLoaderInteractor
import com.vinted.demovinted.ui.utils.ListStatus
import com.vinted.demovinted.ui.utils.Loading
import com.vinted.demovinted.ui.utils.Success
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FeedViewModel @ViewModelInject constructor(
    private val catalogLoaderInteractor: CatalogLoaderInteractor
) : ViewModel() {

    val feedStatus = MutableLiveData<ListStatus>()
    private val disposables = CompositeDisposable()

    init {
        val disposable = catalogLoaderInteractor.dataLoaded
            .subscribeOn(Schedulers.io())
            .subscribe({
                feedStatus.postValue(Loading(true))
                when {
                    it.isOnNext -> feedStatus.postValue(Success(it.value!!))
                    it.isOnError -> feedStatus.postValue(com.vinted.demovinted.ui.utils.Error(it.error!!))
                }
            }, {
                feedStatus.postValue(Loading(false))
                feedStatus.postValue(com.vinted.demovinted.ui.utils.Error(it))
            })
        disposables.add(disposable)

        catalogLoaderInteractor.init()
    }

    fun requestMore() {
        catalogLoaderInteractor.requestMoreItems()
    }
}
