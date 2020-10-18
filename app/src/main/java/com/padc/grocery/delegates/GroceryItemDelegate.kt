package com.padc.grocery.delegates

import android.graphics.Bitmap
import android.net.Uri
import com.padc.grocery.data.vos.GroceryVO

interface GroceryItemDelegate{
    fun onTapDeleteGrocery(name : String)
    fun onTapEditGrocery(name: String, description: String, amount: Int,image : String)
    //fun onTapFileUpload(grocery: GroceryVO)
}