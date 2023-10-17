package com.thomas.excel.library.fixedColumns

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.thomas.excel.library.R


class RowTitleAdapter : RecyclerView.Adapter<RowTitleAdapter.ColumnTitleViewHolder> {
    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    private var mContext: Context? = null
    private var mList: List<String>? = null


    constructor(mContext: Context, mList: List<String>) {
        this.mContext = mContext
        this.mList = mList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColumnTitleViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.item_vertical_title_name, parent, false)
        return ColumnTitleViewHolder(itemView)


    }

    override fun onBindViewHolder(@NonNull holder: ColumnTitleViewHolder, position: Int) {

        val mViewHolder = holder as ColumnTitleViewHolder
        mViewHolder.setmStock(mList!![position])
        mViewHolder.refreshView()

    }


    inner class ColumnTitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tv_goodname: TextView
        lateinit var mStock: String

        init {
            tv_goodname = view.findViewById(R.id.tv_name) as TextView
        }

        fun setmStock(mStock: String) {
            this.mStock = mStock
        }

        fun refreshView() {
            //公司名称
            tv_goodname.text = mStock

        }
    }


}
