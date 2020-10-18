package com.padc.grocery.mvp.views

import android.graphics.Bitmap
import android.net.Uri
import com.padc.grocery.data.vos.GroceryVO

interface MainView : BaseView {
    fun showGroceryData(groceryList : List<GroceryVO>)
    fun showErrorMessage(message : String)
    fun showGroceryDialog(name: String, description: String, amount: String,image : String)
    fun openGallery()
    fun showCurrentUserName(name : String)

    fun displayToolbarTitle(title : String)
    fun displayMainScreenViewType(viewType  :Int)
}