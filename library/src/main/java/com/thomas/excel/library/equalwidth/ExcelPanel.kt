package com.thomas.excel.library.equalwidth

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.excel.library.R
import kotlinx.android.synthetic.main.view_scrollable_panel.view.*

/**
 * A flexible view for providing a limited window into a large data set,like a two-dimensional recyclerView.
 * but it will pin the itemView of first row and first column in their original location.
 */
class ExcelPanel : FrameLayout {
    private var panelRowAdapter: PanelRowAdapter? = null
    private var panelAdapter: PanelAdapter? = null

    constructor(context: Context?, panelAdapter: PanelAdapter?) : super(context!!) {
        this.panelAdapter = panelAdapter
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(  context!!, attrs, defStyleAttr ) {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_scrollable_panel, this, true)
        contentRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        headerRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        headerRecyclerView.setHasFixedSize(true)
        if (panelAdapter != null) {
            panelRowAdapter = PanelRowAdapter(panelAdapter, headerRecyclerView)
            contentRecyclerView.adapter = panelRowAdapter
            setUpFirstItemView(panelAdapter)
        }
    }

    private fun setUpFirstItemView(panelAdapter: PanelAdapter?) {
        val viewHolder =
            panelAdapter!!.onCreateViewHolder(firstItemView, panelAdapter.getItemViewType(0, 0))
        if (viewHolder != null) {
            panelAdapter.onBindViewHolder(viewHolder, 0, 0)
        }
        firstItemView.addView(viewHolder!!.itemView)
    }

    fun notifyDataSetChanged() {
        if (panelRowAdapter != null) {
            setUpFirstItemView(panelAdapter)
            panelRowAdapter!!.notifyDataChanged()
        }
    }

    /**
     * @param panelAdapter [PanelAdapter]
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setPanelAdapter(panelAdapter: PanelAdapter?) {
        if (panelRowAdapter != null) {
            panelRowAdapter!!.setPanelAdapter(panelAdapter)
            panelRowAdapter!!.notifyDataSetChanged()
        } else {
            panelRowAdapter = PanelRowAdapter(panelAdapter, headerRecyclerView)
            contentRecyclerView.adapter = panelRowAdapter
        }
        this.panelAdapter = panelAdapter
        setUpFirstItemView(panelAdapter)
    }


}