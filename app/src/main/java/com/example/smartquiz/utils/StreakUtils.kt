package com.example.smartquiz.utils

import java.util.Calendar

object StreakUtils {

    fun isSameDay(time1: Long, time2: Long): Boolean {
        val c1 = Calendar.getInstance().apply { timeInMillis = time1 }
        val c2 = Calendar.getInstance().apply { timeInMillis = time2 }

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
               c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
    }

    fun isYesterday(lastTime: Long, now: Long): Boolean {
        val cal = Calendar.getInstance().apply {
            timeInMillis = now
            add(Calendar.DAY_OF_YEAR, -1)
        }
        return isSameDay(cal.timeInMillis, lastTime)
    }
}
