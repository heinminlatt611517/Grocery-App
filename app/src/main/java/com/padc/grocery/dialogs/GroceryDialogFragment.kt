package com.padc.grocery.dialogs

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.padc.grocery.R
import com.padc.grocery.activities.MainActivity
import com.padc.grocery.mvp.presenter.MainPresenter
import com.padc.grocery.mvp.presenter.MainPresenterImpl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_add_grocery.*
import kotlinx.android.synthetic.main.dialog_add_grocery.view.*
import java.io.File
import java.io.IOException


class GroceryDialogFragment : DialogFragment() {

    companion object {

        const val PICK_IMAGE_REQUEST = 1111

        const val TAG_ADD_GROCERY_DIALOG = "TAG_ADD_GROCERY_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BITMAP_IMAGE = "BITMAP_IMAGE"

        fun newFragment(): GroceryDialogFragment {
            return GroceryDialogFragment()
        }
    }


    private lateinit var mPresenter: MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_grocery, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()




        view.etGroceryName?.setText(arguments?.getString(BUNDLE_NAME))
        view.etDescription?.setText(arguments?.getString(BUNDLE_DESCRIPTION))
        view.etAmount?.setText(arguments?.getString(BUNDLE_AMOUNT))

        Log.d("imageLink",arguments?.getString(BITMAP_IMAGE).toString())
        if (!arguments?.getString(BITMAP_IMAGE).toString().isNullOrEmpty()){

        }
        else{
           Picasso.with(context)
               .load("https://"+arguments?.getString(BITMAP_IMAGE).toString())
               .into(ivGroceryImage)
        }


            view.btnAddGrocery.setOnClickListener {
            mPresenter.onTapAddGrocery(
                etGroceryName.text.toString(),
                etDescription.text.toString(),
                etAmount.text.toString().toInt(),
                ivGroceryImage.drawable.toBitmap()
            )
            dismiss()
        }


        btnChooseImage.setOnClickListener {
            /***
             * Open Gallery
             */
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                MainActivity.PICK_IMAGE_REQUEST
            )
        }


    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data
            try {

                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(activity!!.contentResolver, filePath)


                        val bitmap = ImageDecoder.decodeBitmap(source)
                        ivGroceryImage.setImageBitmap(bitmap)

                       // mPresenter.onPhotoTaken(bitmap)
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            context?.contentResolver, filePath
                        )
                        ivGroceryImage.setImageBitmap(bitmap)

                       // mPresenter.onPhotoTaken(bitmap)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun setUpPresenter() {
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(MainPresenterImpl::class.java)
        }
    }


}