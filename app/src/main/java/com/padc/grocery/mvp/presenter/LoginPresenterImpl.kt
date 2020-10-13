package com.padc.grocery.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.data.models.AuthenticationModel
import com.padc.grocery.data.models.AuthenticationModelImpl
import com.padc.grocery.mvp.views.LoginView

class LoginPresenterImpl : LoginPresenter,AbstractBasePresenter<LoginView>() {

    private val mAuthenticatioModel: AuthenticationModel = AuthenticationModelImpl

    override fun onTapLogin(email: String, password: String) {
     mAuthenticatioModel.login(email,password,onSuccess = {
         mView.navigateToHomeScreen()
     },
     onFailure = {
         mView.showError(it)
     })
    }

    override fun onTapRegister() {
       mView.navigateToRegisterScreen()
    }

    override fun getCurrentUserName() : String {
      return  mAuthenticatioModel.getUserName()
    }

    override fun onUiReady(owner: LifecycleOwner) {

    }
}