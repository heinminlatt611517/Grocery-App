package com.padc.grocery.mvp.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.mvp.views.BaseView

interface BasePresenter<V: BaseView> {
    fun onUiReady(context: Context, owner: LifecycleOwner)
    fun initPresenter(view: V)
}