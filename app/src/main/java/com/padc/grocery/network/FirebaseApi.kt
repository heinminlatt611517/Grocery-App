package com.padc.grocery.network

import android.graphics.Bitmap
import com.padc.grocery.data.vos.GroceryVO

interface FirebaseApi {
    fun getGroceries(onSuccess: (groceries: List<GroceryVO>) -> Unit, onFialure: (String) -> Unit)

    fun addGrocery(name: String, description: String, amount: Int,image : Bitmap)

    fun removeCategory(name : String)

    //fun uploadImageAndEditGrocery(image : Bitmap, grocery: GroceryVO)
}