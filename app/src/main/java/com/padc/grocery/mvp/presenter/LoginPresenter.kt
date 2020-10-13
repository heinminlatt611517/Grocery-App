package com.padc.grocery.mvp.presenter

import com.padc.grocery.mvp.views.LoginView

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(email: String, password: String)
    fun onTapRegister()
    fun getCurrentUserName() : String
}