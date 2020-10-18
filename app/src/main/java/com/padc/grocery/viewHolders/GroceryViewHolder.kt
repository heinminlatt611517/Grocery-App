package com.padc.grocery.viewHolders

import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.delegates.GroceryItemDelegate
import com.zg.burgerjoint.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.view_holder_grocery_item.view.*

class GroceryViewHolder(private val mDelegate : GroceryItemDelegate,itemView: View) : BaseViewHolder<GroceryVO>(itemView) {

    override fun bindData(data: GroceryVO) {
        itemView.tvTitle.text = data.name
        itemView.tvDescription.text = data.description
        itemView.tvCount.text = "x ${data.amount.toString()}"

        itemView.btnDelete.setOnClickListener {
            mDelegate.onTapDeleteGrocery(data.name ?: "")
        }

        itemView.btnEdit.setOnClickListener {
            mDelegate.onTapEditGrocery(data.name ?: "", data.description ?: "", data.amount ?: 0,data.image.toString())
        }

        itemView.btnFileUpload.setOnClickListener {
            //mDelegate.onTapFileUpload(data)
        }

        Glide.with(itemView.context)
            .load(data.image)
            .into(itemView.ivGroceryImage)
    }
}