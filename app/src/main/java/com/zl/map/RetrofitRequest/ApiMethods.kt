package com.zl.map.RetrofitRequest

import okhttp3.ResponseBody
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


/**
 * Created by zhangli on 2018/6/14.
 */
class ApiMethods{
    /**
     * 封装线程管理和订阅的过程
     */
//    init {
//                observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer)
//    }
     fun ApiSubscribe(observable: Observable<Response<ResponseBody>>, observer: Observer<Response<ResponseBody>>) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }
    /**
     * 用于获取豆瓣电影Top250的数据
     *
     * @param observer 由调用者传过来的观察者对象
     * @param start    起始位置
     * @param count    获取长度
     */
    /*public static void getTopMovie(Observer<Movie> observer, int start, int count) {
        ApiSubscribe(Api.getApiService().getTopMovie(start, count), observer);
    }*/
    fun getTopMovie(observer: Observer<Response<ResponseBody>>, start: Int, count: Int) {
        ApiSubscribe(ApiStrategy().getMyApiService().getTopMovie(start, count), observer)
    }
//
//    fun getTopmoney(observer: Observer<RunClassBean>, start: String) {
//        ApiSubscribe(ApiStrategy.getApiService().getMoney(start), observer)
//    }

    fun getAdd(observer: Observer<Response<ResponseBody>>, start: CreatRunClass) {
        ApiSubscribe(ApiStrategy().getMyApiService().getAdd(start), observer)
    }
}