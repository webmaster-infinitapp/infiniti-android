package com.payproapp.ui.home.receive


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payproapp.R
import com.payproapp.databinding.FragmentReceiveBinding
import com.payproapp.extensions.getDimensionInPx
import com.payproapp.ui.base.BaseFragment
import com.payproapp.ui.home.HomeViewModel


/**
 * A simple [BaseFragment] subclass.
 * Use the [ReceiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ReceiveFragment : BaseFragment() {

    private lateinit var binding: FragmentReceiveBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receive, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            activity.title = getString(R.string.title_receive)
            viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel::class.java)
            val size = activity.getDimensionInPx(R.dimen.qr_code).toInt()

            viewModel.qrCode.observe(this, Observer { qrBitmap ->
                binding.imageBitmap = qrBitmap
            })

            viewModel.publicKey.observe(this, Observer { qrCode ->
                viewModel.generateQR(
                        qrCode,
                        size,
                        size,
                        ContextCompat.getColor(activity, R.color.secondaryDarkColor),
                        ContextCompat.getColor(activity, R.color.android_default_background)
                )

                binding.textBitmap = qrCode
                binding.setOnCopyQrListener {
                    copyToClipboard(qrCode)
                }
            })

            viewModel.checkPublicKey()
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ReceiveFragment.
         */
        @JvmStatic
        fun newInstance() = ReceiveFragment()
    }
}
