package com.zl.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zl.map.present.IMPresenterCompl
import com.zl.map.view.IIMView
import io.rong.imkit.RongIM

class IMActivity : BaseActivity(),IIMView {
    var mIMPresenterCompl: IMPresenterCompl? = null
    override fun showConnectInfo(t: Any) {
        Log.e("TGA",t.toString())
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_im
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mIMPresenterCompl = IMPresenterCompl(this@IMActivity)
        mIMPresenterCompl!!.doConnect()
    }



}
