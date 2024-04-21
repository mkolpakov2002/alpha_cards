package com.example.alpha.ui.auth

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.alpha.R
import com.example.alpha.data.api.AuthResponse
import com.example.alpha.data.api.AuthResult
import com.example.alpha.databinding.FragmentAuthBinding
import com.example.alpha.ui.permissions.permission.PermissionManager.Companion.setAuthIsGranted
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json


class AuthFragment : Fragment() {

    companion object {
        private const val AUTH_URL = "https://profile.miem.hse.ru/auth/realms/MIEM/protocol/openid-connect/auth?client_id=19230-prj&redirect_uri=https://1789.nas.helow19274.ru/auth/callback&response_type=code"
    }

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        webView = binding.webView
        progressBar = binding.progressBar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        // Enable private browsing mode
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.domStorageEnabled = false
        webSettings.databaseEnabled = false
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
        webView.clearCache(true)
        webView.clearHistory()

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                if (url.startsWith("https://1789.nas.helow19274.ru/auth/callback")) {
                    val code = Uri.parse(url).getQueryParameter("code")
                    onAuthorizationCodeReceived(code)
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress < 100) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
        showAuthWindow()
    }

    private fun onAuthorizationCodeReceived(code: String?) {
        if (code != null) {
            CoroutineScope(Dispatchers.Main).launch {
                authViewModel.setAuthResult(null)
                setAuthIsGranted(requireContext(), false)
                val authResponse = handleAuthorizationResponse(code)
                if(authResponse.user.user_type == 1) {
                    refreshWebView(message = "Authorization failed. Student account is not allowed.")
                } else {
                    val jwtToken = authResponse.jwt_token
                    val user = authResponse.user
                    val authResult = AuthResult(jwtToken, user)
                    authViewModel.setAuthResult(authResult)
                    setAuthIsGranted(requireContext(), true)
                    Navigation.findNavController(binding.root).navigate(R.id.navigation_home)
                }
            }
        } else {
            refreshWebView()
        }
    }

    fun refreshWebView(message: String = "Authentication failed. Please try again.") {
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
        webView.clearCache(true)
        webView.clearHistory()
        showAuthWindow(message)
    }

    private fun showAuthWindow(toastMessage: String? = null) {
        if (toastMessage != null) {
            Toast.makeText(requireContext(), toastMessage,
                Toast.LENGTH_LONG).show()
        }
        binding.frameLayoutWebView.visibility = View.VISIBLE
        webView.loadUrl(AUTH_URL)
    }

    private suspend fun handleAuthorizationResponse(code: String): AuthResponse {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        return client.get("https://1789.nas.helow19274.ru/auth/callback") {
            parameter("code", code)
        }.body<AuthResponse>()
    }
}