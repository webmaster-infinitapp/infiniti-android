package com.payproapp.ui.home.settings


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.payproapp.R
import com.payproapp.databinding.FragmentWebviewBinding
import com.payproapp.ui.base.BaseFragment


class WebViewFragment : BaseFragment() {
    private lateinit var binding: FragmentWebviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_webview, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            arguments?.let {
                setupWebView(it.getString(WEBVIEW_URL, getString(R.string.link_website)), binding.webview)
                activity.title = it.getString(FRAGMENT_TITLE, getString(R.string.terms_and_conditions))
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String, wb: WebView) {
        wb.settings.javaScriptEnabled = true;
        wb.settings.loadWithOverviewMode = true
        wb.settings.useWideViewPort = true
        wb.settings.builtInZoomControls = true
        wb.webViewClient = MyWebViewClient()
        wb.loadUrl(url)

        binding.showProgressBar = true
    }

    companion object {
        const val WEBVIEW_URL = "webview_url"
        const val FRAGMENT_TITLE = "fragment_title"

        @JvmStatic
        fun newInstance(name: String, url: String): WebViewFragment {
            val bundle = Bundle()
            bundle.putString(FRAGMENT_TITLE, name)
            bundle.putString(WEBVIEW_URL, url)

            val fragment = WebViewFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            binding.showProgressBar = false
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            binding.showProgressBar = false
        }
    }
}
