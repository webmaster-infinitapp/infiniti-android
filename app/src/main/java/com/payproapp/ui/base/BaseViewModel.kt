package com.payproapp.ui.base

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    //Disposite to do the requests
    val disposable = CompositeDisposable()

    //Int that will hold a string (int resource) when there is an error
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessageText: MutableLiveData<String> = MutableLiveData()

    //Boolean that will control when a dialog shows in the views
    val showDialog: MutableLiveData<Boolean> = MutableLiveData()

    //Boolean that will control when an specific action is finished
    val success: MutableLiveData<Boolean> = MutableLiveData()

    //Boolean that will control when a progress bar is shown in the view
    val showProgressBar: ObservableField<Boolean> = ObservableField(false)

    //When the viewmodel is cleared we clear the disposable
    override fun onCleared() {
        super.onCleared()
        if (disposable.isDisposed) {
            disposable.clear()
        }
    }
}