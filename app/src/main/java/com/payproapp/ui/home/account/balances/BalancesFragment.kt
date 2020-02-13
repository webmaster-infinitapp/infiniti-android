package com.payproapp.ui.home.account.balances


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.payproapp.R
import com.payproapp.databinding.FragmentBalanceBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel

/**
 * A simple [BaseFragment] subclass.
 * Use the [BalancesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BalancesFragment : BaseFragment() {

    private lateinit var binding: FragmentBalanceBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var balanceAdapter: BalanceAdapter

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment AccountFragment.
         */
        @JvmStatic
        fun newInstance() = BalancesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_balance, container, false)
        binding.addNewTokenListener = View.OnClickListener {
            context?.let { context ->
                AddNewTokenActivity.start(context)
            }

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //viewModel.retrieveBalances()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            binding.viewModel = viewModel
            viewModel.retrieveBalances()

            viewModel.balances.observe(this, Observer { balances ->
                balanceAdapter.addAll(balances)
            })

            setupBalances()
        }
    }

    private fun setupBalances() {
        balanceAdapter = BalanceAdapter()
        binding.balancesRecycler.layoutManager = LinearLayoutManager(context)
        binding.balancesRecycler.adapter = balanceAdapter
    }
}
