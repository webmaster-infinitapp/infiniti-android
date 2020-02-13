package com.payproapp.ui.home.settings.private_key


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentPrivateKeyBinding
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel


class PrivateKeyFragment : BaseFragment() {
    private lateinit var binding: FragmentPrivateKeyBinding
    private lateinit var viewModel: HomeViewModel

    private var privateKey: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_private_key, container, false)
        binding.onCopyPrivateKey = View.OnClickListener { copyToClipboard(privateKey) }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            activity.title = getString(R.string.label_private_key)
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            binding.viewModel = viewModel
            viewModel.retrievePrivateKey()

            viewModel.privateKey.observe(this, Observer {
                privateKey = it
                binding.privateKey = it
            })
        }
    }

    private fun copyToClipboard(text: String) {
        activity.let {
            val clipboardManager = it?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val copiedValue = ClipData.newPlainText("copied", text)
            clipboardManager.primaryClip = copiedValue

            Toast.makeText(it, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PrivateKeyFragment()
    }
}
