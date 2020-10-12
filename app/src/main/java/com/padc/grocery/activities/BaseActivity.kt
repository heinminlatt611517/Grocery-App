package com.padc.grocery.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.padc.grocery.mvp.views.BaseView

abstract class BaseActivity : AppCompatActivity(),BaseView{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}