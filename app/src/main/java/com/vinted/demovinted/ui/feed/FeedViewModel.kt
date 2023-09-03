package com.vinted.demovinted.ui.feed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinted.demovinted.data.interactors.CatalogLoaderInteractor
import com.vinted.demovinted.data.models.ItemBoxViewEntity
import com.vinted.demovinted.data.models.ItemSeenEvent
import com.vinted.demovinted.domain.use_case.PostItemSeenUseCase
import com.vinted.demovinted.ui.utils.ListStatus
import com.vinted.demovinted.ui.utils.Loading
import com.vinted.demovinted.ui.utils.Success
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FeedViewModel @ViewModelInject constructor(
    private val catalogLoaderInteractor: CatalogLoaderInteractor,
    private val postItemSeenUseCase: PostItemSeenUseCase
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

    fun sendSeenItems(items: List<ItemBoxViewEntity>, indexes: List<Int>) {
        val itemsSeen: List<ItemSeenEvent> = createImpressions(items, indexes)
        if (itemsSeen.isEmpty()) {
            return
        }

        postItemSeenUseCase.execute(itemsSeen).onEach {
            it.data.let {
                // received data
            }
            it.error.let {
                // Error
            }
        }.launchIn(viewModelScope)

    }

    fun createImpressions(items: List<ItemBoxViewEntity>, indexes: List<Int>): List<ItemSeenEvent> {
        val itemsSeen: MutableList<ItemSeenEvent> = mutableListOf()
        if (items.isNotEmpty() && indexes.isNotEmpty()) {
            for (i in indexes[0] .. indexes[indexes.size - 1]) {
                itemsSeen.add(ItemSeenEvent(System.currentTimeMillis(), items.get(i).itemId.toInt()))
            }
        }

        return itemsSeen
    }
}
