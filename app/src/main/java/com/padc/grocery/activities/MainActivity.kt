package com.padc.grocery.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.grocery.R
import com.padc.grocery.adapters.GroceryAdapter
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.dialogs.GroceryDialogFragment
import com.padc.grocery.dialogs.GroceryDialogFragment.Companion.BITMAP_IMAGE
import com.padc.grocery.dialogs.GroceryDialogFragment.Companion.BUNDLE_AMOUNT
import com.padc.grocery.dialogs.GroceryDialogFragment.Companion.BUNDLE_DESCRIPTION
import com.padc.grocery.dialogs.GroceryDialogFragment.Companion.BUNDLE_NAME
import com.padc.grocery.mvp.presenter.MainPresenter
import com.padc.grocery.mvp.presenter.MainPresenterImpl
import com.padc.grocery.mvp.views.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),MainView {

    private lateinit var mAdapter: GroceryAdapter

    private lateinit var mPresenter: MainPresenter


    companion object {
        const val PICK_IMAGE_REQUEST = 1111
        const val USER_NAME_EXTRA = "user name extra"

        var mainScreenViewType : Int? = null

        fun newIntent(context: Context,userName : String) : Intent{
            val intent = Intent(context,MainActivity::class.java)
            intent.putExtra(USER_NAME_EXTRA,userName)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        setUpPresenter()
        setUpActionListeners()

        mPresenter.onUiReady(this,this)

        setUpRecyclerView()
        addCrashButton()
//        val userName = (intent.getStringExtra(USER_NAME_EXTRA))
//        tvUserName.text= "Hello $userName"

    }

    private fun addCrashButton() {
        val crashButton = Button(this)
        crashButton.text = "Crash!"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    }


//
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

        when(mainScreenViewType){
            0 ->{
                mAdapter = GroceryAdapter(mPresenter, mainScreenViewType!!)
                rvGroceries.adapter = mAdapter
                rvGroceries.layoutManager =
                    LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            }
            1-> {
                mAdapter = GroceryAdapter(mPresenter,mainScreenViewType!!)
                rvGroceries.adapter = mAdapter
                rvGroceries.layoutManager =
                    GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false)
            }
            else ->{

                mAdapter = GroceryAdapter(mPresenter,mainScreenViewType!!)
                rvGroceries.adapter = mAdapter
                rvGroceries.layoutManager =
                    LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            }
        }


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
        Log.d("style", mainScreenViewType.toString())

        mAdapter.setNewData(groceryList)


    }

    override fun showErrorMessage(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG)
    }

    override fun showGroceryDialog(name: String, description: String, amount: String,image : String) {
        val groceryDialog = GroceryDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_NAME, name)
        bundle.putString(BUNDLE_DESCRIPTION,description)
        bundle.putString(BUNDLE_AMOUNT, amount)
        bundle.putString(BITMAP_IMAGE, image)
        groceryDialog.arguments = bundle
        groceryDialog.show(supportFragmentManager, GroceryDialogFragment.TAG_ADD_GROCERY_DIALOG)
    }


    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun showCurrentUserName(name: String) {
        tvUserName.text= "Hello $name"
    }

    override fun displayToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun displayMainScreenViewType(viewType: Int) {
       mainScreenViewType = viewType

    }

    override fun showError(error: String) {

    }
}