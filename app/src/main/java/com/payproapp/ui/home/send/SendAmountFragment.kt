package com.payproapp.ui.home.send


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.payproapp.databinding.FragmentSendAmountBinding
import com.payproapp.model.Balance
import com.payproapp.R
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel
import com.payproapp.util.preferences.PreferencesManager
import javax.inject.Inject


class SendAmountFragment : BaseFragment() {
    @Inject
    lateinit var preferencesManager: PreferencesManager

    lateinit var binding: FragmentSendAmountBinding
    lateinit var viewModel: HomeViewModel

    var tokens: MutableList<Balance>? = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_send_amount, container, false)
        setupUI(binding.root)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            activity.title = getString(R.string.title_send_amount)

            tokens = preferencesManager.getTokens()

            binding.sendBody = viewModel.sendBody
            binding.onNextListener = View.OnClickListener { checkForm() }
            binding.txtAmount.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkForm()
                    true
                } else {
                    false
                }
            }

            loadTokensSpinner(activity)
        }
    }

    private fun loadTokensSpinner(context: Context) {
        tokens?.let {
            val tokensString = mutableListOf<String>()

            for (balance in it) {
                tokensString.add(balance.symbol)
            }

            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, tokensString)
            binding.spinnerTokenAmount.adapter = adapter

            binding.spinnerTokenAmount.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    getSelectedToken(position)?.let { balance ->
                        loadTokenAmount(balance)
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {

                }
            }
        }
    }

    private fun loadTokenAmount(balance: Balance) {
        binding.tokenValue.text = String.format(getString(R.string.available_amount), (balance.balance + " " + balance.symbol))
    }

    private fun getSelectedToken(position: Int): Balance? {
        var tokenSymbol = binding.spinnerTokenAmount.getItemAtPosition(position)

        if (tokenSymbol != null) {
            tokens?.let {
                for (token in it) {
                    if (token.symbol == tokenSymbol) {
                        return token
                    }
                }
            }
        }
        return null
    }

    private fun checkForm() {
        if (viewModel.sendBody.amount.isEmpty()) {
            binding.txtAmount.error = getString(R.string.error_required_field)
            return
        } else {
            val insertedValue = binding.txtAmount.text.toString().toDouble()

            getSelectedToken(binding.spinnerTokenAmount.selectedItemPosition)?.let {
                if (it.balance.toDouble() < insertedValue) {
                    binding.txtAmount.error = getString(R.string.not_enough)
                    return
                }
            }
        }

        viewModel.sendBody.tokenSymbol = binding.spinnerTokenAmount.selectedItem as String

        hideKeyboard()
        viewModel.sendMessageState()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SendAmountFragment()
    }
}
