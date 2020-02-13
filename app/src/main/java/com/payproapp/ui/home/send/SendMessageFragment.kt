package com.payproapp.ui.home.send


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentSendMessageBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel

class SendMessageFragment : BaseFragment() {

    lateinit var binding: FragmentSendMessageBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_send_message, container, false)
        setupUI(binding.root)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            activity.title = getString(R.string.title_send_message)

            binding.sendBody = viewModel.sendBody
            binding.onNextListener = View.OnClickListener { checkForm() }
            binding.txtMessage.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkForm()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun checkForm() {
        if (viewModel.sendBody.description.isEmpty()) {
            binding.txtMessage.error = getString(R.string.error_required_field)
            return
        }

        hideKeyboard()
        viewModel.confirmTransactionState()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SendMessageFragment()
    }
}
