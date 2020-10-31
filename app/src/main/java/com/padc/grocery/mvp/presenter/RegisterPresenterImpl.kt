package com.padc.grocery.mvp.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.analytics.PARAMETER_EMAIL
import com.padc.grocery.analytics.SCREEN_REGISTER
import com.padc.grocery.analytics.TAP_REGISTER
import com.padc.grocery.data.models.AuthenticationModel
import com.padc.grocery.data.models.AuthenticationModelImpl
import com.padc.grocery.mvp.views.RegisterView

class RegisterPresenterImpl : RegisterPresenter,AbstractBasePresenter<RegisterView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl


    override fun onTapRegister(context: Context,email: String, password: String, userName: String) {
        sendEventsToFirebaseAnalytics(context, TAP_REGISTER, PARAMETER_EMAIL, email)
        mAuthenticationModel.register(email,password,userName,onSuccess = {
            mView.navigateToLoginScreen()
        },
        onFailure = {
            mView.showError(it)
        })
    }

    override fun onUiReady(context: Context,owner: LifecycleOwner) {
        sendEventsToFirebaseAnalytics(context, SCREEN_REGISTER)
    }
}