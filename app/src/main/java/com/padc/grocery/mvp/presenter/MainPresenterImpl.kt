package com.padc.grocery.mvp.presenter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.data.models.AuthenticationModel
import com.padc.grocery.data.models.AuthenticationModelImpl
import com.padc.grocery.data.models.GroceryModelImpl
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.mvp.views.MainView

class MainPresenterImpl : MainPresenter, AbstractBasePresenter<MainView>() {

    val mGroceryModel = GroceryModelImpl
    private var mChosenGroceryForFileUpload: GroceryVO? = null
    private val mAuthenticatioModel: AuthenticationModel = AuthenticationModelImpl

    override fun onTapAddGrocery(
        name: String, description: String, amount: Int,
        image: Bitmap
    ) {
        mGroceryModel.addGrocery(name,description,amount,image)
    }






    override fun onUiReady(owner: LifecycleOwner) {


        mGroceryModel.getGroceries(
            onSuccess = {
                mView.showGroceryData(it)
                mView.showCurrentUserName(mAuthenticatioModel.getUserName())
            },
            onFaiure = {
                mView.showErrorMessage(it)
            }
        )

        mView.displayToolbarTitle(mGroceryModel.getAppNameFromRemoteConfig())

        mView.displayMainScreenViewType(mGroceryModel.getMainScreenViewType())
    }

    override fun onTapDeleteGrocery(name: String) {
        mGroceryModel.removeCategory(name)
    }

    override fun onTapEditGrocery(name: String, description: String, amount: Int,image : String) {
        mView.showGroceryDialog(name, description, amount.toString(),image)
    }

    //    override fun onPhotoTaken(bitmap: Bitmap) {
//        mChosenGroceryForFileUpload?.let {
//            mGroceryModel.uploadImageAndUpdateGrocery(it,bitmap)
//        }
//    }

//    override fun onTapFileUpload(grocery: GroceryVO) {
//        mChosenGroceryForFileUpload = grocery
//        mView?.openGallery()
//    }
}