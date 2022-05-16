package com.example.i_calculator.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    var errorDialogWatcher = MutableLiveData<String>()

    var errorToastyWatcher = MutableLiveData<String>()

    private val mCompositeDisposable = CompositeDisposable()

    fun onViewCreated() {

    }

    /**
     * On destroy view
     */
    fun onDestroyView() {

    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    protected fun addCompositeDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
}