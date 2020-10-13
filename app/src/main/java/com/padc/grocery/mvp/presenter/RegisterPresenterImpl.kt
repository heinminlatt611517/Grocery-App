package com.padc.grocery.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.data.models.AuthenticationModel
import com.padc.grocery.data.models.AuthenticationModelImpl
import com.padc.grocery.mvp.views.RegisterView

class RegisterPresenterImpl : RegisterPresenter,AbstractBasePresenter<RegisterView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl


    override fun onTapRegister(email: String, password: String, userName: String) {
        mAuthenticationModel.register(email,password,userName,onSuccess = {
            mView.navigateToLoginScreen()
        },
        onFailure = {
            mView.showError(it)
        })
    }

    override fun onUiReady(owner: LifecycleOwner) {

    }
}