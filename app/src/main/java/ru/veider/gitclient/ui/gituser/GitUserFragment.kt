package ru.veider.gitclient.ui.gituser

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.FragmentUserBinding

class GitUserFragment : Fragment(R.layout.fragment_user) {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"

    private var isPageLoaded = false
    companion object {
        private lateinit var gotoUrl: String

        fun newInstance(url: String): GitUserFragment {
            gotoUrl = url
            return GitUserFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentView = view
        FragmentUserBinding.bind(view).webView.apply {
            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    if (!isPageLoaded) {
                        FragmentUserBinding.bind(fragmentView).progress.visibility = View.VISIBLE
                    }
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    FragmentUserBinding.bind(fragmentView).progress.visibility = View.GONE
                    isPageLoaded = true
                    super.onPageFinished(view, url)
                }
            }
            if (gotoUrl.isNotEmpty()) {
                loadUrl(gotoUrl)
            }

        }
    }

}