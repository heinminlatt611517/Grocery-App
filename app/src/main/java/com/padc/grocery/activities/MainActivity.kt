package com.padc.grocery.activities

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.grocery.R
import com.padc.grocery.adapters.GroceryAdapter
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.dialogs.GroceryDialogFragment
import com.padc.grocery.dialogs.GroceryDialogFragment.Companion.BUNDLE_AMOUNT
import com.padc.grocery.dialogs.GroceryDialogFragment.Companion.BUNDLE_DESCRIPTION
import com.padc.grocery.dialogs.GroceryDialogFragment.Companion.BUNDLE_NAME
import com.padc.grocery.mvp.presenter.MainPresenter
import com.padc.grocery.mvp.presenter.MainPresenterImpl
import com.padc.grocery.mvp.views.MainView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : BaseActivity(),MainView {

    private lateinit var mAdapter: GroceryAdapter
    private lateinit var mPresenter: MainPresenter


    companion object {
        const val PICK_IMAGE_REQUEST = 1111
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        setUpPresenter()
        setUpRecyclerView()

        setUpActionListeners()

        mPresenter.onUiReady(this)

    }

//    @RequiresApi(Build.VERSION_CODES.P)
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
//            if (data == null || data.data == null) {
//                return
//            }
//
//            val filePath = data.data
//            try {
//
//                filePath?.let {
//                    if (Build.VERSION.SDK_INT >= 29) {
//                        val source: ImageDecoder.Source =
//                            ImageDecoder.createSource(this.contentResolver, filePath)
//
//                        val bitmap = ImageDecoder.decodeBitmap(source)
//                        mPresenter.onPhotoTaken(bitmap)
//                    } else {
//                        val bitmap = MediaStore.Images.Media.getBitmap(
//                            applicationContext.contentResolver, filePath
//                        )
//                        mPresenter.onPhotoTaken(bitmap)
//                    }
//                }
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }

    private fun setUpActionListeners() {
        fab.setOnClickListener {
            GroceryDialogFragment.newFragment()
                .show(supportFragmentManager, GroceryDialogFragment.TAG_ADD_GROCERY_DIALOG)
        }
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProviders.of(this).get(MainPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }


    private fun setUpRecyclerView() {
        mAdapter = GroceryAdapter(mPresenter)
        rvGroceries.adapter = mAdapter
        rvGroceries.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showGroceryData(groceryList: List<GroceryVO>) {
        Log.d("size",groceryList.size.toString())
        mAdapter.setNewData(groceryList)
    }

    override fun showErrorMessage(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG)
    }

    override fun showGroceryDialog(name: String, description: String, amount: String) {
        val groceryDialog = GroceryDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_NAME, name)
        bundle.putString(BUNDLE_DESCRIPTION,description)
        bundle.putString(BUNDLE_AMOUNT, amount)
        groceryDialog.arguments = bundle
        groceryDialog.show(supportFragmentManager, GroceryDialogFragment.TAG_ADD_GROCERY_DIALOG)
    }


    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
}