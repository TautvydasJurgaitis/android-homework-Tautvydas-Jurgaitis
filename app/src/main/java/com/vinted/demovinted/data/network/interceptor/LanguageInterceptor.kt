package com.vinted.demovinted.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class LanguageInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(HTTP_HEADER_ACCEPT_LANGUAGE, Locale.ENGLISH.toLanguageTag())
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val HTTP_HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    }
}