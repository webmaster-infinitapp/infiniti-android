package com.payproapp.ui.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.payproapp.R
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.payproapp.extensions.withTextColor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected val disposable = CompositeDisposable()

    private var alertDialog: AlertDialog? = null

    //When the activity is destroyed we clear the disposable
    override fun onDestroy() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        super.onDestroy()
    }

    //Method to load showDialog and errorMessage observables from the associated viewModel to show loading/error dialogs
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

        viewModel.errorMessageText.observe(this, Observer { errorMessage ->
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

    //Method that will show a progress dialog
    fun showProgressDialog() {
        if (alertDialog == null) {
            val dialogBuilder = AlertDialog.Builder(this)
            val view = LayoutInflater.from(this)
                    .inflate(R.layout.dialog_loading, null)
            dialogBuilder.setView(view)
            dialogBuilder.setCancelable(false)
            alertDialog = dialogBuilder.create()
        }

        alertDialog?.show()
    }

    //Method that will dismiss the progress dialog
    fun dismissProgressDialog() {
        alertDialog?.dismiss()
    }

    //Method that will show the keyboard
    fun showKeyBoard(view: View?) {
        view?.let {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    //Method that will show an error message with a snackbar
    fun showError(@StringRes errorMessage: Int, view: View?) {
        view?.let {
            Snackbar.make(it, errorMessage, Snackbar.LENGTH_LONG)
                    .withTextColor(Color.WHITE)
                    .show()
        }
    }

    fun showError(@StringRes errorMessage: String, view: View?) {
        view?.let {
            Snackbar.make(it, errorMessage, Snackbar.LENGTH_LONG)
                    .withTextColor(Color.WHITE)
                    .show()
        }
    }
}