package com.thomas.excel.library

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A flexible view for providing a limited window into a large data set,like a two-dimensional recyclerView.
 * but it will pin the itemView of first row and first column in their original location.
 */
class ExcelSpanel : FrameLayout {
    private var recyclerView: RecyclerView? = null
    private var headerRecyclerView: RecyclerView? = null
    private var panelLineAdapter: PanelLineAdapter? = null
    private var panelAdapter: PanelAdapter? = null
    private var firstItemView: FrameLayout? = null

    constructor(context: Context?, panelAdapter: PanelAdapter?) : super(context!!) {
        this.panelAdapter = panelAdapter
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_scrollable_panel, this, true)
        recyclerView = findViewById<View>(R.id.recycler_content_list) as RecyclerView
        recyclerView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        firstItemView = findViewById<View>(R.id.first_item) as FrameLayout
        headerRecyclerView = findViewById<View>(R.id.recycler_header_list) as RecyclerView
        headerRecyclerView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        headerRecyclerView!!.setHasFixedSize(true)
        if (panelAdapter != null) {
            panelLineAdapter = PanelLineAdapter(panelAdapter, recyclerView, headerRecyclerView)
            recyclerView!!.adapter = panelLineAdapter
            setUpFirstItemView(panelAdapter)
        }
    }

    private fun setUpFirstItemView(panelAdapter: PanelAdapter?) {
        val viewHolder =
            panelAdapter!!.onCreateViewHolder(firstItemView, panelAdapter.getItemViewType(0, 0))
        if (viewHolder != null) {
            panelAdapter.onBindViewHolder(viewHolder, 0, 0)
        }
        firstItemView!!.addView(viewHolder!!.itemView)
    }

    fun notifyDataSetChanged() {
        if (panelLineAdapter != null) {
            setUpFirstItemView(panelAdapter)
            panelLineAdapter!!.notifyDataChanged()
        }
    }

    /**
     * @param panelAdapter [PanelAdapter]
     */
    fun setPanelAdapter(panelAdapter: PanelAdapter?) {
        if (panelLineAdapter != null) {
            panelLineAdapter!!.setPanelAdapter(panelAdapter)
            panelLineAdapter!!.notifyDataSetChanged()
        } else {
            panelLineAdapter = PanelLineAdapter(panelAdapter, recyclerView, headerRecyclerView)
            recyclerView!!.adapter = panelLineAdapter
        }
        this.panelAdapter = panelAdapter
        setUpFirstItemView(panelAdapter)
    }

    /**
     * Adapter used to bind dataSet to cell View that are displayed within every row of [ExcelSpanel].
     */
    private class PanelLineItemAdapter(
        private var row: Int,
        private val panelAdapter: PanelAdapter?
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return panelAdapter!!.onCreateViewHolder(parent, viewType)!!
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            panelAdapter!!.onBindViewHolder(holder, row, position + 1)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
            panelAdapter!!.onBindViewHolder(holder, row, position + 1)
        }

        override fun getItemViewType(position: Int): Int {
            return panelAdapter!!.getItemViewType(row, position + 1)
        }

        override fun getItemCount(): Int {
            return panelAdapter!!.columnCount - 1
        }

        fun setRow(row: Int) {
            this.row = row
        }
    }

    /**
     * Adapter used to bind dataSet to views that are displayed within a[ExcelSpanel].
     */
    class PanelLineAdapter(
        private var panelAdapter: PanelAdapter?,
        private val contentRV: RecyclerView?,
        private val headerRecyclerView: RecyclerView?
    ) : RecyclerView.Adapter<PanelLineAdapter.ViewHolder>() {
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

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var lineItemAdapter = holder.recyclerView.adapter as PanelLineItemAdapter?
            if (lineItemAdapter == null) {
                lineItemAdapter = PanelLineItemAdapter(position + 1, panelAdapter)
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

        fun notifyDataChanged() {
            setUpHeaderRecyclerView()
            notifyDataSetChanged()
        }

        private fun setUpHeaderRecyclerView() {
            if (panelAdapter != null) {
                if (headerRecyclerView!!.adapter == null) {
                    val lineItemAdapter = PanelLineItemAdapter(0, panelAdapter)
                    headerRecyclerView.adapter = lineItemAdapter
                } else {
                    headerRecyclerView.adapter!!.notifyDataSetChanged()
                }
            }
        }

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
                                    this@PanelLineAdapter.firstPos = firstPos
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

        private val recyclerViews: HashSet<RecyclerView?>
            private get() {
                val recyclerViewHashSet = HashSet<RecyclerView?>()
                recyclerViewHashSet.add(headerRecyclerView)
                for (i in 0 until contentRV!!.childCount) {
                    recyclerViewHashSet.add(
                        contentRV.getChildAt(i)
                            .findViewById<View>(R.id.recycler_line_list) as RecyclerView
                    )
                }
                return recyclerViewHashSet
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
}