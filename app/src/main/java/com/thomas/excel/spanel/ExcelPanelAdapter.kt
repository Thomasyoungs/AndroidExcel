package com.thomas.excel.spanel

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thomas.excel.library.PanelAdapter

/**
 * created by yangzhikuan on 23-10-10 .
 */
class ExcelPanelAdapter() : PanelAdapter() {
    private var verticalAxisInfoList: List<VerticalAxisInfo> = ArrayList()
    private var horizontalAxisInfoList: List<HorizontalAxisInfo> = ArrayList()
    private var ordersList: List<List<DataInfo>> = ArrayList()
    override val rowCount: Int
        get() = verticalAxisInfoList.size + 1

    override val columnCount: Int
        get() = horizontalAxisInfoList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, row: Int, column: Int) {
        when (getItemViewType(row, column)) {
            HORIZONTAL_TYPE -> setHorizontalAxisView(column, holder as HorizontalAxisViewHolder)
            VERTICAL_TYPE -> setVerticalAxisView(row, holder as VerticalAxisViewHolder)
            DATA_TYPE -> setOrderView(row, column, holder as OrderViewHolder)
            TITLE_TYPE -> {}
            else -> setOrderView(row, column, holder as OrderViewHolder)
        }
    }

    override fun getItemViewType(row: Int, column: Int): Int {
        if (column == 0 && row == 0) {
            return TITLE_TYPE
        }
        if (column == 0) {
            return VERTICAL_TYPE
        }
        return if (row == 0) {
            HORIZONTAL_TYPE
        } else DATA_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            HORIZONTAL_TYPE -> return HorizontalAxisViewHolder(
                LayoutInflater.from(
                    parent!!.context
                ).inflate(R.layout.listitem_horizontal_axis_info, parent, false)
            )
            VERTICAL_TYPE -> return VerticalAxisViewHolder(
                LayoutInflater.from(
                    parent!!.context
                ).inflate(R.layout.listitem_vertical_axis_info, parent, false)
            )
            DATA_TYPE -> return OrderViewHolder(
                LayoutInflater.from(
                    parent!!.context
                ).inflate(R.layout.listitem_content_info, parent, false)
            )
            TITLE_TYPE -> return TitleViewHolder(
                LayoutInflater.from(
                    parent!!.context
                ).inflate(R.layout.listitem_title, parent, false)
            )
            else -> {}
        }
        return OrderViewHolder(
            LayoutInflater.from(parent!!.context)
                .inflate(R.layout.listitem_content_info, parent, false)
        )
    }

    private fun setHorizontalAxisView(pos: Int, viewHolder: HorizontalAxisViewHolder) {
        val horizontalAxisInfo = horizontalAxisInfoList[pos - 1]
        if (pos > 0) {
            viewHolder.dateTextView.text = horizontalAxisInfo.date
        }
    }

    private fun setVerticalAxisView(pos: Int, viewHolder: VerticalAxisViewHolder) {
        val verticalAxisInfo = verticalAxisInfoList[pos - 1]
        if (pos > 0) {
            viewHolder.roomTypeTextView.text = verticalAxisInfo.roomType
            viewHolder.roomNameTextView.text = verticalAxisInfo.roomName
        }
    }

    private fun setOrderView(row: Int, column: Int, viewHolder: OrderViewHolder) {
        val dataInfo = ordersList[row - 1][column - 1]
        if (dataInfo.status === DataInfo.Status.COMMON) {
            viewHolder.view.setBackgroundResource(R.drawable.bg_white_gray_stroke)
            viewHolder.nameTextView.text = dataInfo.guestName
            viewHolder.nameTextView.setTextColor(Color.parseColor("#000000"))
        } else if (dataInfo.status === DataInfo.Status.BLUE_TEXT) {
            viewHolder.nameTextView.text = dataInfo.guestName
            viewHolder.nameTextView.setTextColor(Color.parseColor("#0000ff"))
        }
    }

    private class HorizontalAxisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTextView: TextView

        init {
            dateTextView = itemView.findViewById<View>(R.id.date) as TextView
        }
    }

    private class VerticalAxisViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var roomTypeTextView: TextView
        var roomNameTextView: TextView

        init {
            roomTypeTextView = view.findViewById<View>(R.id.room_type) as TextView
            roomNameTextView = view.findViewById<View>(R.id.room_name) as TextView
        }
    }

    private class OrderViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView: TextView = view.findViewById<View>(R.id.guest_name) as TextView

    }

    private class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView

        init {
            titleTextView = view.findViewById<View>(R.id.title) as TextView
        }
    }

    fun setVerticalAxisInfoList(verticalAxisInfoList: List<VerticalAxisInfo>) {
        this.verticalAxisInfoList = verticalAxisInfoList
    }

    fun setHorizontalAxisInfoList(horizontalAxisInfoList: List<HorizontalAxisInfo>) {
        this.horizontalAxisInfoList = horizontalAxisInfoList
    }

    fun setOrdersList(ordersList: List<List<DataInfo>>) {
        this.ordersList = ordersList
    }

    companion object {
        private const val TITLE_TYPE = 4
        private const val VERTICAL_TYPE = 0
        private const val HORIZONTAL_TYPE = 1
        private const val DATA_TYPE = 2
    }
}