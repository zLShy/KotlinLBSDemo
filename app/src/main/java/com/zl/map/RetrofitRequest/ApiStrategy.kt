package com.zl.map.RetrofitRequest

import android.text.TextUtils
import com.google.protobuf.Api
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.autonavi.amap.mapcore.tools.GLFileUtil.getCacheDir
import com.zl.map.MyApplication
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import okhttp3.CacheControl




/**
 * Created by zhangli on 2018/6/14.
 */
class ApiStrategy {
    var baseUrl = "http://runmate.runtogether.cn/runmate-paoban/v1/"
    //读超时长，单位：毫秒
    val READ_TIME_OUT:Long = 7676
    //连接时长，单位：毫秒
    val CONNECT_TIME_OUT:Long = 7676


    var apiService: APiService? = null


    fun getMyApiService(): APiService {
        if (apiService == null) {
            synchronized(Api::class.java) {
                if (apiService == null) {
                    ApiStrategy()
                }
            }
        }
        return this.apiService!!
    }

    private fun ApiStrategy(){
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //缓存
        val cacheFile = File(MyApplication.getContext().getCacheDir(), "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 100) //100Mb
        //增加头部信息
        val headerInterceptor = object : Interceptor{
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val build = chain.request().newBuilder()
                        //.addHeader("Content-Type", "application/json")//设置允许请求json数据
                        .build()
                return chain.proceed(build)
            }
        }

        //创建一个OkHttpClient并设置超时时间
        val client = OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build()

        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                //适配RxJava2.0,RxJava1.x则为RxJavaCallAdapterFactory.create()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        apiService = retrofit.create(APiService::class.java)
    }

    /**
     * 设缓存有效期为两天
     */
    private val CACHE_STALE_SEC = (60 * 60 * 24 * 2).toLong()

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private val mRewriteCacheControlInterceptor = Interceptor { chain ->
        var request = chain.request()
        val cacheControl = request.cacheControl().toString()
        if (!NetWorkUtils.isNetConnected(MyApplication.getContext())) {
            request = request.newBuilder()
                    .cacheControl(if (TextUtils.isEmpty(cacheControl))
                        CacheControl
                                .FORCE_NETWORK
                    else
                        CacheControl.FORCE_CACHE)
                    .build()
        }
        val originalResponse = chain.proceed(request)
        if (NetWorkUtils.isNetConnected(MyApplication.getContext())) {
            originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    //                        .addHeader("application/json", "charset=utf-8")
                    .removeHeader("Pragma")
                    .build()
        } else {
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    //                        .addHeader("application/json", "charset=utf-8")
                    .removeHeader("Pragma")
                    .build()
        }
    }
}