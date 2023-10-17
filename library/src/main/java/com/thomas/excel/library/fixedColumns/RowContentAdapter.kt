package com.thomas.excel.library.fixedColumns

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.thomas.excel.library.R


class RowContentAdapter : RecyclerView.Adapter<RowContentAdapter.ContentViewHolder> {
    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    private var mContext: Context? = null
    private var mList: List<CotentData>? = null
    private val CONTENT = 2


    constructor(mContext: Context, mList: List<CotentData>) {
        this.mContext = mContext
        this.mList = mList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {

        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false)
        return ContentViewHolder(itemView)

    }

    override fun onBindViewHolder(@NonNull holder: ContentViewHolder, position: Int) {

        holder.setmStock(mList!![position])
        holder.refreshView()

    }






    inner class ContentViewHolder(convertView: View) :
        RecyclerView.ViewHolder(convertView) {

        var itemTv1: TextView
        var itemTv2: TextView
        var itemTv3: TextView
        var itemTv4: TextView
        var itemTv5: TextView
        var itemTv6: TextView
        lateinit var mStock: CotentData

        init {
            //
            itemTv1 = convertView.findViewById(R.id.tv_table_content_right_item0)
            itemTv2 = convertView.findViewById(R.id.tv_table_content_right_item1)
            itemTv3 = convertView.findViewById(R.id.tv_table_content_right_item2)
            itemTv4 = convertView.findViewById(R.id.tv_table_content_right_item3)
            itemTv5 = convertView.findViewById(R.id.tv_table_content_right_item4)
            itemTv6 = convertView.findViewById(R.id.tv_table_content_right_item5)

        }

        fun setmStock(mStock: CotentData) {
            this.mStock = mStock
        }

        fun refreshView() {
            itemTv1.text = mStock.content1
            itemTv2.text = mStock.content2
            itemTv3.text = mStock.content3
            itemTv4.text = mStock.content4
            itemTv5.text = mStock.content5
            itemTv6.text = mStock.content6

        }
    }


}
