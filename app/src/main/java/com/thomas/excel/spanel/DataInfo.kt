package com.thomas.excel.spanel

import java.util.*

/**
 * created by yangzhikuan on 23-10-10 .
 */
class DataInfo {
    var id: Long = 0
    var guestName: String? = null
    var status: Status? = null
    var isBegin = false

    enum class Status {
        CHECK_IN, REVERSE, BLANK;

        companion object {
            private val VALUES = Collections.unmodifiableList(Arrays.asList(*values()))
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