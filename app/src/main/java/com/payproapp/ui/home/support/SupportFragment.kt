package com.payproapp.ui.home.support


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentSupportBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel

/**
 * A simple [BaseFragment] subclass.
 * Use the [SupportFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SupportFragment : BaseFragment() {

    private lateinit var binding: FragmentSupportBinding
    private lateinit var viewModel: HomeViewModel

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SupportFragment.
         */
        @JvmStatic
        fun newInstance() = SupportFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_support, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            activity.title = getString(R.string.title_support)
            binding.acceptListener = View.OnClickListener { viewModel.launchIntercomChat() }
        }
    }


}
