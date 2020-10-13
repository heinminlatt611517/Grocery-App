package com.padc.grocery.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.padc.grocery.R
import com.padc.grocery.mvp.presenter.LoginPresenter
import com.padc.grocery.mvp.presenter.LoginPresenterImpl
import com.padc.grocery.mvp.views.LoginView
import kotlinx.android.synthetic.main.activity_login_activty.*

class LoginActivty : BaseActivity(),LoginView {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivty::class.java)
        }
    }

    private lateinit var mPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activty)


        setUpPresenter()
        setUpActionListeners()

    }

    private fun setUpActionListeners() {
        btnLogin.setOnClickListener {
            mPresenter.onTapLogin(etEmail.text.toString(), etPassword.text.toString())
        }

        btnRegister.setOnClickListener {
            mPresenter.onTapRegister()
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(LoginPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun navigateToHomeScreen() {
        val userName = mPresenter.getCurrentUserName()
        startActivity(MainActivity.newIntent(this,userName.toString()))
    }

    override fun navigateToRegisterScreen() {
        startActivity(RegisterActivity.newIntent(this))
    }

}