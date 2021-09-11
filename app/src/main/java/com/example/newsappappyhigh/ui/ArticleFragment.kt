package com.example.newsappappyhigh.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.newsappappyhigh.R
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() {

    val args:ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

}