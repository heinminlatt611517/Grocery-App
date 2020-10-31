package com.padc.grocery.mvp.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.analytics.PARAMETER_EMAIL
import com.padc.grocery.analytics.SCREEN_LOGIN
import com.padc.grocery.analytics.TAP_LOGIN
import com.padc.grocery.data.models.AuthenticationModel
import com.padc.grocery.data.models.AuthenticationModelImpl
import com.padc.grocery.data.models.GroceryModel
import com.padc.grocery.data.models.GroceryModelImpl
import com.padc.grocery.mvp.views.LoginView

class LoginPresenterImpl : LoginPresenter,AbstractBasePresenter<LoginView>() {

    private val mAuthenticatioModel: AuthenticationModel = AuthenticationModelImpl

    private val mGroceryModel : GroceryModel = GroceryModelImpl

    override fun onTapLogin(context: Context,email: String, password: String) {
        sendEventsToFirebaseAnalytics(context, TAP_LOGIN, PARAMETER_EMAIL, email)
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

    override fun onUiReady(context : Context,owner: LifecycleOwner) {
        sendEventsToFirebaseAnalytics(context, SCREEN_LOGIN)
        mGroceryModel.setUpRemoteConfigWithDefaultValue()
        mGroceryModel.fetchRemoteConfigs()
    }
}