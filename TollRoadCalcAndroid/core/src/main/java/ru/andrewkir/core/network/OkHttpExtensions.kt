package ru.andrewkir.core.network

import android.annotation.SuppressLint
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import okhttp3.OkHttpClient


private const val SSL_PROTOCOL = "SSL"

internal fun OkHttpClient.Builder.disableSslSocketFactory(): OkHttpClient.Builder {
    val trustAllCerts: X509TrustManager =
        @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {

            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) =
                Unit

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) =
                Unit

            override fun getAcceptedIssuers(): Array<X509Certificate> =
                emptyArray()
        }

    val sslContext: SSLContext = SSLContext.getInstance(SSL_PROTOCOL)
    sslContext.init(null, arrayOf(trustAllCerts), SecureRandom())
    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

    return sslSocketFactory(sslSocketFactory, trustAllCerts)
        .hostnameVerifier { _, _ -> true }
}