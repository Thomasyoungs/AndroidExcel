package com.thomas.excel.library

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
     * Adapter used to bind dataSet to cell View that are displayed within every row of [ExcelPanel].
     */
class PanelRowItemAdapter(
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