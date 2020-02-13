package com.payproapp.ui.home.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentAccountBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel
import com.payproapp.util.PagerAdapter

/**
 * A simple [BaseFragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AccountFragment : BaseFragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var viewModel: HomeViewModel

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment AccountFragment.
         */
        @JvmStatic
        fun newInstance() = AccountFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.viewPager.adapter = PagerAdapter(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            activity.title = getString(R.string.title_account)
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
        }
    }
}
