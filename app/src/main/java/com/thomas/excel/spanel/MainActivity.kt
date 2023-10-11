package com.thomas.excel.spanel

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.thomas.excel.library.ExcelSpanel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }

    override fun getResources(): Resources {
        return super.getResources()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val excelSpanel = findViewById<View>(R.id.scrollable_panel) as ExcelSpanel
        val excelSpanelAdapter = ExcelSpanelAdapter()
        generateTestData(excelSpanelAdapter)
        excelSpanel.setPanelAdapter(excelSpanelAdapter)
    }

    private val featherList = arrayOf("灰", "黑色", "黑白花", "黑点白")
    private fun generateTestData(excelSpanelAdapter: ExcelSpanelAdapter) {
        val verticalAxisInfoList: MutableList<VerticalAxisInfo> = ArrayList()
        for (i in 0..99) {
            val verticalAxisInfo = VerticalAxisInfo()
            verticalAxisInfo.roomType = ""
            verticalAxisInfo.roomId = i.toLong()
            verticalAxisInfo.roomName = "" + i
            verticalAxisInfoList.add(verticalAxisInfo)
        }
        excelSpanelAdapter.setRoomInfoList(verticalAxisInfoList)
        val horizontalAxisInfoList: MutableList<HorizontalAxisInfo> = ArrayList()
        val horizontalAxisInfo = HorizontalAxisInfo()
        horizontalAxisInfo.date = "参赛名"
        horizontalAxisInfo.week = ""
        horizontalAxisInfoList.add(horizontalAxisInfo)
        val horizontalAxisInfo1 = HorizontalAxisInfo()
        horizontalAxisInfo1.date = "环号"
        horizontalAxisInfo1.week = ""
        horizontalAxisInfoList.add(horizontalAxisInfo1)
        val horizontalAxisInfo2 = HorizontalAxisInfo()
        horizontalAxisInfo2.date = "羽色"
        horizontalAxisInfo2.week = ""
        horizontalAxisInfoList.add(horizontalAxisInfo2)
        val horizontalAxisInfo3 = HorizontalAxisInfo()
        horizontalAxisInfo3.date = "分速"
        horizontalAxisInfo3.week = ""
        horizontalAxisInfoList.add(horizontalAxisInfo3)
        val horizontalAxisInfo4 = HorizontalAxisInfo()
        horizontalAxisInfo4.date = "归巢日期"
        horizontalAxisInfo4.week = ""
        horizontalAxisInfoList.add(horizontalAxisInfo4)
        val horizontalAxisInfo5 = HorizontalAxisInfo()
        horizontalAxisInfo5.date = "地区"
        horizontalAxisInfo5.week = ""
        horizontalAxisInfoList.add(horizontalAxisInfo5)
        excelSpanelAdapter.setDateInfoList(horizontalAxisInfoList)
        val ordersList: MutableList<List<DataInfo>> = ArrayList()
        for (i in 0..99) {
            val dataInfoList: MutableList<DataInfo> = ArrayList()
            //横坐标数据
            //参赛名
            val name = DataInfo()
            name.guestName = "暴力心$i"
            name.isBegin = true
            name.status = DataInfo.Status.COMMON
            dataInfoList.add(name)
            //环号
            val ringNum = DataInfo()
            ringNum.guestName = "2023-03-01111$i"
            ringNum.isBegin = true
            ringNum.status = DataInfo.Status.BLUE_TEXT
            dataInfoList.add(ringNum)
            //羽色
            val feather = DataInfo()
            feather.guestName = featherList[i % featherList.size]
            feather.isBegin = true
            feather.status = DataInfo.Status.COMMON
            dataInfoList.add(feather)
            //分速
            val sudu = DataInfo()
            sudu.guestName = (1382.123 + i).toString()
            sudu.isBegin = true
            sudu.status = DataInfo.Status.COMMON
            dataInfoList.add(sudu)
            //归巢日期
            val time = DataInfo()
            time.guestName = "2023-10-10 00:00:00.000"
            time.isBegin = true
            time.status = DataInfo.Status.COMMON
            dataInfoList.add(time)
            //地区
            val area = DataInfo()
            area.guestName = "唐山"
            area.isBegin = true
            area.status = DataInfo.Status.COMMON
            dataInfoList.add(time)
            ordersList.add(dataInfoList)
        }
        excelSpanelAdapter.setOrdersList(ordersList)
    }

    companion object {
        val DAY_UI_MONTH_DAY_FORMAT = SimpleDateFormat("MM-dd")
        val WEEK_FORMAT = SimpleDateFormat("EEE", Locale.US)
    }
}