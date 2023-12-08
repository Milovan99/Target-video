package com.milovanjakovljevic.targetvideo.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    //For testing purposes I will leave API key here so you don't have problem running app
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
            .addQueryParameter("key", "AIzaSyBhO3e306IG8K0n3QZF3_THIcsC3ygPKLs").build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
