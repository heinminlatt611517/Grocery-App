package com.padc.grocery.network

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.utils.extUploadImageAndEditGrocery
import java.io.ByteArrayOutputStream
import java.util.*

object CloudFirestoreFirebaseApiImpl : FirebaseApi {

    private val database = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    override fun getGroceries(
        onSuccess: (groceries: List<GroceryVO>) -> Unit,
        onFialure: (String) -> Unit
    ) {
//       database.collection("groceries")
//           .get()
//           .addOnSuccessListener { result ->
//                   val groceriesLists: MutableList<GroceryVO> = arrayListOf()
//                   for (document in result) {
//                       val data = document.data
//                       var grocery = GroceryVO()
//                       grocery.name = data["name"] as String
//                       grocery.description = data["description"] as String
//                       grocery.amount = (data["amount"] as Long).toInt()
//                       groceriesLists.add(grocery)
//                   }
//               onSuccess(groceriesLists)
//           }
//           .addOnFailureListener { exception ->
//               onFialure(exception.message ?: "Please Check Connection")
//           }

        /***
         * cloud fireStore with realtime database observer
         */
        database.collection("groceries")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFialure(it.message ?: "Please check connection")
                } ?: run {

                    val groceriesLists: MutableList<GroceryVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()

                    for (document in result){
                        val data = document.data
                       var grocery = GroceryVO()
                       grocery.name = data?.get("name") as String
                       grocery.description = data["description"] as String
                       grocery.amount = (data["amount"] as Long).toInt()
                        grocery.image = data["image"] as String?
                       groceriesLists.add(grocery)
                    }
                    onSuccess(groceriesLists)
                }
            }
    }

    override fun addGrocery(name: String, description: String, amount: Int,image : Bitmap) {
        extUploadImageAndEditGrocery(image,onUploadImageSuccess = {
            val imageUrl = it.result?.toString()
            val groceryMap = hashMapOf(
                "name" to name,
                "description" to description,
                "amount" to amount.toLong(),
                "image" to imageUrl
            )
            database.collection("groceries")
                .document(name)
                .set(groceryMap)
                .addOnSuccessListener { Log.d("Success","Successfully added grocery") }
                .addOnFailureListener { Log.d("Fail","Failed to add grocery") }
        },
            onFailure = {})
    }

    override fun removeCategory(name: String) {
        database.collection("groceries")
            .document(name)
            .delete()
            .addOnSuccessListener { Log.d("Success","Successfully delete grocery") }
            .addOnFailureListener { Log.d("Fail","Failed to delete grocery") }
    }

//    override fun uploadImageAndEditGrocery(image: Bitmap, grocery: GroceryVO) {
//       extUploadImageAndEditGrocery(image,onUploadImageSuccess = {
//           val imageUrl = it.result?.toString()
//           addGrocery(
//               grocery.name ?: "",
//               grocery.description ?: "",
//               grocery.amount ?: 0,
//               imageUrl ?: ""
//           )
//       },
//       onFailure = {})
//    }

}