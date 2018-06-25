package com.zl.map

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.widget.Toast
import com.zl.map.Model.User
import com.zl.map.present.ILoginPresenterCompl
import com.zl.map.view.ILoginView

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity(),ILoginView {

    private var mILoginPresenterCompl: ILoginPresenterCompl? = null
    override fun onClearText() {
        password.setText("")
    }

    override fun getUserName(): String {
        return email.text.toString().trim()
    }

    override fun getUserPass(): String {
        return password.text.toString().trim()
    }

    override fun showSuccessMsg() {

        Toast.makeText(this@LoginActivity,"cg",Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMsg() {
        Toast.makeText(this@LoginActivity,"sb",Toast.LENGTH_SHORT).show()

    }

    override fun showLoading() {
        login_progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        login_progress.visibility = View.GONE

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)


        mILoginPresenterCompl = ILoginPresenterCompl(this)

        email_sign_in_button.setOnClickListener { view -> mILoginPresenterCompl!!.doLogin() }


    }


}
