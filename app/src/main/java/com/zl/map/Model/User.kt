package com.zl.map.Model

/**
 * Created by zhangli on 2018/6/21.
 */
class User() {

    private var name:String? = null
    private var pass:String? = null
    fun getName():String {
        return name!!
    }

    fun setName(name: String) {
        this.name = name
    }
    fun setPass(pass: String) {
        this.pass = pass
    }

    fun getPass():String{
        return pass!!
    }
}