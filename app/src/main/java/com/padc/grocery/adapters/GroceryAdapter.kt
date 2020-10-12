package com.padc.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.grocery.R
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.delegates.GroceryItemDelegate
import com.padc.grocery.viewHolders.GroceryViewHolder
import com.zg.burgerjoint.adapters.BaseRecyclerAdapter

class GroceryAdapter(delegate : GroceryItemDelegate) : BaseRecyclerAdapter<GroceryViewHolder, GroceryVO>() {
    val mDelegate  = delegate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_grocery_item,parent,false)
        return GroceryViewHolder(mDelegate,view)
    }
}