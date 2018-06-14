package com.zl.map.RetrofitRequest

import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.GET
import android.graphics.Movie
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query


/**
 * Created by zhangli on 2018/6/14.
 */
interface APiService {


//    @GET("start")
//    fun getBanner(): Observable<BannerBean>
//
//    @GET("start")
//    fun getBanner1(@Query("start") start: Int, @Query("count") count: Int): Observable<BannerBean>

    @POST("/stats/start")
    fun getIndex(@Body map: ByteArray): Call<ResponseBody>

    @POST("/stats/start")
    fun getStart(@Body map: ByteArray): Observable<Response<ResponseBody>>

    @GET("top250")
    fun getTopMovie(@Query("start") start: Int, @Query("count") count: Int): Observable<Movie>

//    @GET("score")
//    fun getMoney(@Query("guid") guid: String): Observable<RunClassBean>
//
    @POST("paoban/add")
    fun getAdd(@Body requestBody: CreatRunClass): Observable<Response<ResponseBody>>
}