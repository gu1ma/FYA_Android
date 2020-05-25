package br.com.firstdecision.cowip.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(private val authToken: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
            .header("Authorization", authToken)
        builder.addHeader("Content-Type", "application/json")

        val request = builder.build()
        return chain.proceed(request)
    }
}