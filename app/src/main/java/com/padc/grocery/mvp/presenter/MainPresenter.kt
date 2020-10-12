package com.padc.grocery.mvp.presenter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.padc.grocery.delegates.GroceryItemDelegate
import com.padc.grocery.mvp.views.MainView

interface MainPresenter : BasePresenter<MainView>,GroceryItemDelegate{
    fun onTapAddGrocery(
        name: String, description: String, amount: Int,
        image: Bitmap
    )
    //fun onPhotoTaken(bitmap: Bitmap)
}
