package com.milovanjakovljevic.targetvideo.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    //For testing purposes I will leave API key here so you don't have problem running app
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
            .addQueryParameter("per_page", "20")
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .addHeader(
                "Authorization",
                "Bearer v2/OFBUZXFaQ0p3T3ZQbGVQbFA0OFBLb3p2Z2JjOTFyVDcvNDE0NjE3MzAzL2N1c3RvbWVyLzQvNGVEalJPZTVmV1E2TFdPbXR2d0dMSHNoTC1zTVFvYkdVaFdFU3FHQ3ZQUmFUamd2V3BXRktXZnhmM2FPRUJkS0IyZDAtVmlfZ193YU9zRENDTllkcTdYb2NtNlQwbjRHSGkxd0tPM3p1WXpwVndkcHAwZmlvbzF4QkdGT2NQai1WbFkwdWlCaDBJUGNLZGVSRHhPM3VsT1ktQ2lRczNzUWotc094N0V6UE1CNTBUallBVWxwNVZBc3lTTTlNT0NDbkJ4cXJuSWFyRFZCQVRpVHhFRVp6US9NNW9PaUhQeFRoRWlmMHpURUJidlJn"
            )
            .addHeader("User-Agent", "ShutterstockWorkspace/1.1.30")
            .build()
        return chain.proceed(request)
    }
}
