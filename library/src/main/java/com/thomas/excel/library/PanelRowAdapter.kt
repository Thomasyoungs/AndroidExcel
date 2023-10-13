package com.thomas.excel.library

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * 行适配器
 */
class PanelRowAdapter(
    private var panelAdapter: PanelAdapter?,
    private val headerRecyclerView: RecyclerView?
) : RecyclerView.Adapter<PanelRowAdapter.ViewHolder>() {
    private val observerList = HashSet<RecyclerView?>()
    private var firstPos = -1
    private var firstOffset = -1

    init {
        initRecyclerView(headerRecyclerView)
        setUpHeaderRecyclerView()
    }

    fun setPanelAdapter(panelAdapter: PanelAdapter?) {
        this.panelAdapter = panelAdapter
        setUpHeaderRecyclerView()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return panelAdapter!!.rowCount - 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_content_row, parent, false)
        )
        initRecyclerView(viewHolder.recyclerView)
        return viewHolder
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var lineItemAdapter = holder.recyclerView.adapter as PanelRowItemAdapter?
        if (lineItemAdapter == null) {
            lineItemAdapter = PanelRowItemAdapter(position + 1, panelAdapter)
            holder.recyclerView.adapter = lineItemAdapter
        } else {
            lineItemAdapter.setRow(position + 1)
            lineItemAdapter.notifyDataSetChanged()
        }
        if (holder.firstColumnItemVH == null) {
            val viewHolder = panelAdapter!!.onCreateViewHolder(
                holder.firstColumnItemView,
                panelAdapter!!.getItemViewType(position + 1, 0)
            )
            holder.firstColumnItemVH = viewHolder
            holder.firstColumnItemVH?.let { panelAdapter!!.onBindViewHolder(it, position + 1, 0) }
            holder.firstColumnItemView.addView(viewHolder!!.itemView)
        } else {
            panelAdapter!!.onBindViewHolder(holder.firstColumnItemVH!!, position + 1, 0)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyDataChanged() {
        setUpHeaderRecyclerView()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpHeaderRecyclerView() {
        if (panelAdapter != null) {
            if (headerRecyclerView!!.adapter == null) {
                val lineItemAdapter = PanelRowItemAdapter(0, panelAdapter)
                headerRecyclerView.adapter = lineItemAdapter
            } else {
                headerRecyclerView.adapter!!.notifyDataSetChanged()
            }
        }
    }

    /**
     * 设置头部横向滚动 和 下部横向滚动联动
     */
    @SuppressLint("ClickableViewAccessibility")
    fun initRecyclerView(recyclerView: RecyclerView?) {
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        if (layoutManager != null && firstPos > 0 && firstOffset > 0) {
            layoutManager.scrollToPositionWithOffset(firstPos + 1, firstOffset)
        }
        observerList.add(recyclerView)
        recyclerView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> for (rv in observerList) {
                    rv!!.stopScroll()
                }
            }
            false
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val firstPos = linearLayoutManager!!.findFirstVisibleItemPosition()
                val firstVisibleItem = linearLayoutManager.getChildAt(0)
                if (firstVisibleItem != null) {
                    val firstRight = linearLayoutManager.getDecoratedRight(firstVisibleItem)
                    for (rv in observerList) {
                        if (recyclerView !== rv) {
                            val layoutManager = rv!!.layoutManager as LinearLayoutManager?
                            if (layoutManager != null) {
                                this@PanelRowAdapter.firstPos = firstPos
                                firstOffset = firstRight
                                layoutManager.scrollToPositionWithOffset(
                                    firstPos + 1,
                                    firstRight
                                )
                            }
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recyclerView: RecyclerView
        var firstColumnItemView: FrameLayout
        var firstColumnItemVH: RecyclerView.ViewHolder? = null

        init {
            recyclerView = view.findViewById<View>(R.id.recycler_line_list) as RecyclerView
            firstColumnItemView = view.findViewById<View>(R.id.first_column_item) as FrameLayout
            recyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}