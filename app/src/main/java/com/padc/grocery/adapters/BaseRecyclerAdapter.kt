package com.zg.burgerjoint.adapters

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.zg.burgerjoint.viewholders.BaseViewHolder

abstract class BaseRecyclerAdapter<T : BaseViewHolder<W>, W> : RecyclerView.Adapter<T>() {

    protected var mData: ArrayList<W> = arrayListOf()


    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bindData(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setNewData(newData: List<W>) {
        if (newData.isEmpty()){
            mData.clear()
        }else{
            mData = ArrayList(newData)
        }
        notifyDataSetChanged()
    }

    fun appendNewData(newData: List<W>) {
        if(newData.isEmpty()) return

        val startPosition = mData.size

        mData.addAll(newData)
        notifyItemRangeInserted(startPosition, newData.size)
    }

}
