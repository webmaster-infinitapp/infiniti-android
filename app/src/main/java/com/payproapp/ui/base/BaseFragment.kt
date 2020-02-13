package com.payproapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.payproapp.di.Injectable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val disposable = CompositeDisposable()

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    fun loadViewModel(viewModel: BaseViewModel, view: View?) {
        viewModel.showDialog.observe(this, Observer { show ->
            show?.let {
                if (it) {
                    hideKeyboard()
                    showProgressDialog()
                } else {
                    dismissProgressDialog()
                }
            }
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                hideKeyboard()
                showError(errorMessage, view)
            }
        })
    }

    fun setupUI(view: View) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideKeyboard()
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    fun showProgressDialog() {
        activity?.let {
            (activity as BaseActivity).showProgressDialog()
        }
    }

    fun dismissProgressDialog() {
        activity?.let {
            (activity as BaseActivity).dismissProgressDialog()
        }
    }

    fun showKeyBoard(view: View?) {
        activity?.let {
            (it as BaseActivity).showKeyBoard(view)
        }
    }

    fun hideKeyboard() {
        activity?.let {
            (it as BaseActivity).hideKeyboard()
        }
    }

    fun showError(@StringRes errorMessage: Int, view: View?) {
        activity?.let {
            (it as BaseActivity).showError(errorMessage, view)
        }
    }
}