package com.salanevich.githubapp.ui.screen.detail

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun DetailScreen(login: String) {
    val viewModel: DetailViewModel = koinViewModel()
    val state = viewModel.collectAsState()

    DetailScreen("https://www.github.com/$login", state, viewModel::reduce)

    LaunchedEffect(key1 = Unit) {
        viewModel.reduce(DetailAction.DataLoading)
    }
}

@Composable
fun DetailScreen(
    url: String,
    state: State<DetailState>,
    onPageLoaded: (DetailAction.DataLoaded) -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { WebView(it) }) { webView ->
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    onPageLoaded(DetailAction.DataLoaded)
                }
            }
            webView.loadUrl(url)
        }
        if (state.value.loading) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    Surface {
        val state = object : State<DetailState> {
            override val value: DetailState
                get() = DetailState()
        }
        DetailScreen(url = "google.com", state, {})
    }
}