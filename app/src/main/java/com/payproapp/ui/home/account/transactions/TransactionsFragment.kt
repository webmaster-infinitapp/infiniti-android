package com.payproapp.ui.home.account.transactions


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.payproapp.R
import com.payproapp.databinding.FragmentTransactionBinding
import com.payproapp.model.Transaction
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel
import com.payproapp.util.OnTransactionClick
import com.payproapp.util.preferences.PreferencesManager
import javax.inject.Inject

/**
 * A simple [BaseFragment] subclass.
 * Use the [TransactionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TransactionsFragment : BaseFragment() {

    private lateinit var binding: FragmentTransactionBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var transactionAdapter: TransactionsAdapter

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            binding.viewModel = viewModel

            preferencesManager.getPublicKey()?.let {
                viewModel.retrieveTransactions(it)
            }

            viewModel.transactions.observe(this, Observer { transactions ->
                transactionAdapter.addAll(transactions)
            })

            setupTransactions()
        }
    }

    private fun setupTransactions() {
        transactionAdapter = TransactionsAdapter(onTransactionClick = object : OnTransactionClick {
            override fun onTransactionClick(transaction: Transaction) {
                context?.let {
                    TransactionDetailsActivity.start(it, transaction)
                }
            }
        })
        binding.transactionRecycler.layoutManager = LinearLayoutManager(context)
        binding.transactionRecycler.adapter = transactionAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionsFragment()
    }

}
