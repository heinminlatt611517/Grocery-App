package com.padc.grocery.utils

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.network.CloudFirestoreFirebaseApiImpl
import java.io.ByteArrayOutputStream
import java.util.*

fun extUploadImageAndEditGrocery(image: Bitmap,onUploadImageSuccess : (task: Task<Uri>) -> Unit,onFailure : () -> Unit)
{
     val storage = FirebaseStorage.getInstance()
      val storageReference = storage.reference
    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val data = baos.toByteArray()

    val imageRef = storageReference.child("images/${UUID.randomUUID()}")
    val uploadTask = imageRef.putBytes(data)
    uploadTask.addOnFailureListener {
        //
    }.addOnSuccessListener { taskSnapshot ->
        //
    }

    val urlTask = uploadTask.continueWithTask {
        return@continueWithTask imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        onUploadImageSuccess(task)
    }
}


