package com.example.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    //For testing purposes I will leave API key here so you don't have problem running app
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
            .addQueryParameter("per_page", "20")
            .addQueryParameter("query", "movies")
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .addHeader(
                "Authorization",
                "Bearer v2/QmJVWWxHMVNOZmRHMkxZRHpnU1dydDNyV1V1MUlVV1UvNDE1MDIyNTIzL2N1c3RvbWVyLzQvVGI3dXNzX001cjVxa0tMNDR1czJVWHo0V0tEamtyT3JUZVpIWU9wazh1dVp4NXI4OUlrMFlGX1pyNWFjUkZPdDd5MVVNTU43MVg5bzBfWGlPOHZpN3plcE1UVUU0UDVpZmEtZExIak5DYXNjd2t4VVo2VWRJSGV0SEVUVDJTeHdVeVVKTmZScS14aUloQUExWkdmTW1xWmdsTnRXblBfR0Rad191QmhUZ2Z0UFNzWlQ4dEdTMGoxaFAtdUczSWxJLS1PbWJEV2Q3SVBsRDNKY2RTcVJXdy9MUDIyaEkzNnZKUUt0TlNfNVFyeTZR"
            )
            .addHeader("User-Agent", "ShutterstockWorkspace/1.1.30")
            .build()
        return chain.proceed(request)
    }
}
