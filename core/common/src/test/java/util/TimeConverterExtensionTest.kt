package util

import com.winphyoethu.entain.common.util.secondsToTime
import org.junit.Assert.assertEquals
import kotlin.test.Test

class TimeConverterExtensionTest {

    @Test
    fun testConvertMillis() {
        (24 * 60 * 60L).secondsToTime()
        assertEquals("1 day", (24 * 60 * 60L).secondsToTime())
        assertEquals("2 days", (48 * 60 * 60L).secondsToTime())

        assertEquals("1h 5m", (60 * 65L).secondsToTime())
        assertEquals("1h 59m", (60 * 60 + 59 * 60L).secondsToTime())
        assertEquals("2h", (60 * 60 + 60 * 60L).secondsToTime())
        assertEquals("5h", (5 * 60 * 60L).secondsToTime())

        assertEquals("30m", (30 * 60 + 30L).secondsToTime())
        assertEquals("4m 30s", (4 * 60 + 30L).secondsToTime())
        assertEquals("59s", 59L.secondsToTime())
        assertEquals("1s", 1L.secondsToTime())
        assertEquals("LIVE", (-5L).secondsToTime())
    }

}