package com.thomas.excel.panel

import java.util.*

/**
 * created by yangzhikuan on 23-10-10 .
 */
class DataInfo {
    var id: Long = 0
    var guestName: String? = null
    var status: Status? = null
    var isBegin = false
    var width: Int? = null

    enum class Status {
        BLUE_TEXT, COMMON, BLANK;

        companion object {
            private val VALUES = Collections.unmodifiableList(listOf(*values()))
            private val SIZE = VALUES.size
            private val RANDOM = Random()
            fun randomStatus(): Status {
                return VALUES[RANDOM.nextInt(
                    SIZE
                )]
            }
        }
    }
}