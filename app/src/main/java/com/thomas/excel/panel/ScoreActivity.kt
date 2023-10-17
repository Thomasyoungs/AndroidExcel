package com.thomas.excel.panel

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.support.v4.app.INotificationSideChannel
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thomas.excel.library.fixedColumns.CotentData
import com.thomas.excel.library.fixedColumns.RowTitleAdapter
import com.thomas.excel.library.fixedColumns.RowContentAdapter
import kotlinx.android.synthetic.main.activity_score.*
import kotlin.collections.ArrayList

class ScoreActivity : AppCompatActivity() {

    private lateinit var mRowTitleAdapter: RowTitleAdapter
    private var mRowContentAdapter: RowContentAdapter? = null
    private lateinit var list: MutableList<String>
    private lateinit var cotentList: MutableList<CotentData>

    private val featherList = arrayOf("灰", "黑色", "黑白花", "黑点白")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        initView()
    }

    private fun initView() {
        list = ArrayList()
        cotentList = ArrayList()
        // 设置两个水平控件的联动
        row_list_container.setScrollView(content_container)
        content_container.setScrollView(row_list_container)
        mRowTitleAdapter = RowTitleAdapter(this, list)
        mRowContentAdapter = RowContentAdapter(this, cotentList)
        left_title_listview.adapter = mRowTitleAdapter
        right_listview.adapter = mRowContentAdapter
        left_title_listview.layoutManager = LinearLayoutManager(this)
        right_listview.layoutManager = LinearLayoutManager(this)
        left_title_listview.isNestedScrollingEnabled = false
        right_listview.isNestedScrollingEnabled = false
        for (i in 0 until 20) {
            list.add("第${i}名")
            cotentList.add(
                CotentData(
                    "暴力新${i}",
                    "2030-03-100${i}",
                    featherList[i % featherList.size],
                    "${100.000 + i}",
                    "2023-10-17 10:00:0${i}",
                    "唐山"
                )
            )

        }
        setColumnTitle(
            CotentData(
                "会员名",
                "环号",
                "羽色",
                "分速",
                "归巢时间",
                "地区"
            )
        )
    }

    private fun setColumnTitle(contentData: CotentData) {

        tv_title_0.text = contentData.content1
        tv_title_1.text = contentData.content2
        tv_title_2.text = contentData.content3
        tv_title_3.text = contentData.content4
        tv_title_4.text = contentData.content5
        tv_title_5.text = contentData.content6
    }
}
