package com.payproapp.ui.home.send

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.databinding.FragmentSendConfirnTransBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.R
import com.payproapp.ui.home.HomeActivity
import com.payproapp.ui.home.HomeViewModel


class SendConfirnTransFragment : BaseFragment() {

    lateinit var binding: FragmentSendConfirnTransBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_send_confirn_trans, container, false)

        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            activity.title = getString(R.string.title_confirm_transaction)

            binding.sendBody = viewModel.sendBody
            binding.onNextListener = View.OnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("onSendBody", viewModel.sendBody)

                val intent = Intent(activity, SendProcessTransactionActivity::class.java)
                intent.putExtras(bundle)
                this.startActivityForResult(intent, 1)
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                (activity as HomeActivity).removeAllBackStack()
                (activity as HomeActivity).navigateToHome()
            }
            Activity.RESULT_FIRST_USER -> {
                showError(R.string.error_transaction, binding.root)
            }
            Activity.RESULT_CANCELED -> {

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SendConfirnTransFragment()
    }
}
