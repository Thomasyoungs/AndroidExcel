package com.thomas.excel.panel

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.thomas.excel.library.equalwidth.DataInfo
import com.thomas.excel.library.equalwidth.ExcelPanelAdapter
import com.thomas.excel.library.equalwidth.HorizontalAxisInfo
import com.thomas.excel.library.equalwidth.VerticalAxisInfo
import kotlinx.android.synthetic.main.activity_common_excel_pannel.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yangzhikuan
 * Date 2023/10/16
 * Description
 */
class SpecifyActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }

    override fun getResources(): Resources {
        return super.getResources()
    }

    private var excelPanelAdapter: ExcelPanelAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_excel_pannel)
        excelPanelAdapter = ExcelPanelAdapter("指定")
        currentPage = 1
        generateTestData()
        excelPanel.setPanelAdapter(excelPanelAdapter)
        smartRefreshLayout.setRefreshHeader(ClassicsHeader(this))
        smartRefreshLayout.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    currentPage = 1
                    generateTestData()
                    smartRefreshLayout.finishRefresh()
                }, 1000
            )
        }
        smartRefreshLayout.setRefreshFooter(ClassicsFooter(this))
        smartRefreshLayout.setOnLoadMoreListener {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    currentPage++
                    generateTestData()
                    smartRefreshLayout.finishLoadMore()
                }, 1000
            )

        }
    }

    private var currentPage = 1
    private val PAGE_SIZE = 20
    private fun generateTestData() {
        val horizontalAxisInfoList: MutableList<HorizontalAxisInfo> = ArrayList()
        for (j in 0..30) {
            val templates   ="abcdefghijklmnopgrstuvwxyz"
            var identifier: String = templates[j % 26].toString().uppercase()+"组"
            val horizontalAxisInfo = HorizontalAxisInfo()
            horizontalAxisInfo.date = identifier
            horizontalAxisInfo.width = dp2px(50)
            horizontalAxisInfoList.add(horizontalAxisInfo)
        }
        excelPanelAdapter?.setHorizontalAxisInfoList(horizontalAxisInfoList)
        val verticalAxisInfoList: MutableList<VerticalAxisInfo> = ArrayList()
        for (i in 0..currentPage * PAGE_SIZE) {
            val verticalAxisInfo = VerticalAxisInfo()
            verticalAxisInfo.roomType = ""
            verticalAxisInfo.roomId = i.toLong()
            verticalAxisInfo.roomName = "" + i
            verticalAxisInfoList.add(verticalAxisInfo)
        }
        excelPanelAdapter?.setVerticalAxisInfoList(verticalAxisInfoList)
        val ordersList: MutableList<List<DataInfo>> = ArrayList()
        for (i in 0..currentPage * PAGE_SIZE) {
            val dataInfoList: MutableList<DataInfo> = ArrayList()
            //横坐标数据
            for (j in 0..30) {
                val item = DataInfo()
                if (i % 2 == 0) {
                    item.guestName = "√"
                    item.isBegin = true
                    item.width = dp2px(50)
                    item.status = DataInfo.Status.COMMON
                } else {
                    item.status = DataInfo.Status.BLANK
                }
                dataInfoList.add(item)
            }

            ordersList.add(dataInfoList)
        }
        excelPanelAdapter?.setOrdersList(ordersList)
        excelPanel.notifyDataSetChanged()
    }

    companion object {
        val DAY_UI_MONTH_DAY_FORMAT = SimpleDateFormat("MM-dd")
        val WEEK_FORMAT = SimpleDateFormat("EEE", Locale.US)
    }

    fun Context.dp2px(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}