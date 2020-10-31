package com.padc.grocery.mvp.presenter

import android.content.Context
import com.padc.grocery.mvp.views.RegisterView

interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(context: Context,email: String, password: String, userName: String)
}