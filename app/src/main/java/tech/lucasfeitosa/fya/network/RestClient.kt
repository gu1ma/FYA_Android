package br.com.firstdecision.cowip.util

import android.text.TextUtils
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

object RestClient {
    const val BASE_URL = "https://lucasfeitosa.tech/"


    private var REST_CLIENT: RetrofitInterface? = null
    var retrofit: Retrofit? = null
        private set
    private val NEW_URL: String? = null

    private var AUTH_TOKEN = ""

    fun get(): RetrofitInterface? {
        setupRestClient()
        return REST_CLIENT
    }

    fun getWithToken(authToken: String): RetrofitInterface? {
        AUTH_TOKEN = authToken
        setupRestClient()
        return REST_CLIENT
    }

    fun resetRestClient() {
        REST_CLIENT = null
        AUTH_TOKEN = ""
    }

    private fun setupRestClient() {

        //https://fnid1f60cca1.us2.hana.ondemand.com/gagf/services/java/service.xsjs/checkin
        val builder = OkHttpClient.Builder()

        if (TextUtils.isEmpty(AUTH_TOKEN)) {
            AUTH_TOKEN = "Basic aW90NGRlY2lzaW9uOkZpcnN0QDIwMTc="//Basic eWFyZDp5YXJk
        }

        try {
            builder.sslSocketFactory(TLSSocketFactory())
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        val httpLoggingInterceptor = HttpLoggingInterceptor()

        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.networkInterceptors().add(httpLoggingInterceptor)
        builder.addNetworkInterceptor(StethoInterceptor())

        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.connectTimeout(30, TimeUnit.SECONDS)

        val interceptor = AuthenticationInterceptor(AUTH_TOKEN)

        if (!builder.interceptors().contains(interceptor)) {
            builder.addInterceptor(interceptor)
        }

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        val rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())

        val url = if (TextUtils.isEmpty(NEW_URL)) BASE_URL else NEW_URL

        val retrofit = Retrofit.Builder()
            .client(builder.build())
            .baseUrl(url!!)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .build()

        this.retrofit = retrofit
        REST_CLIENT = retrofit.create(RetrofitInterface::class.java)
    }

}