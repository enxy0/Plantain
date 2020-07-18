package kek.plantain

import kek.plantain.data.entity.toCount
import kek.plantain.data.entity.toDate
import kek.plantain.data.entity.toRubles
import kek.plantain.utils.getFakeDump
import org.junit.Assert.assertEquals
import org.junit.Test

class DumpTest {
    private val dump = getFakeDump()

    @Test fun `extracting is correct`() {
        assertEquals(115200.toRubles(), dump.balance)
        assertEquals(4400.toRubles(), dump.lastPayedMoney)
        assertEquals(0.toRubles(), dump.lastPaymentMoney)
        assertEquals(0.toCount(), dump.subwayTravelCount)
        assertEquals(2.toCount(), dump.groundTravelCount)
        assertEquals("01.01.2010 00:00".toDate(), dump.lastPaymentDate)
        assertEquals("22.06.2020 20:05".toDate(), dump.lastUsedDate)
    }

    @Test fun `writing is correct`() {
        // Dump uses delegated properties
        dump.balance = 115300.toRubles()
        assertEquals(115300.toRubles(), dump.balance)

        dump.lastPayedMoney = 3300.toRubles()
        assertEquals(3300.toRubles(), dump.lastPayedMoney)

        dump.subwayTravelCount = 2.toCount()
        assertEquals(2.toCount(), dump.subwayTravelCount)

        dump.groundTravelCount = 3.toCount()
        assertEquals(3.toCount(), dump.groundTravelCount)

        dump.lastUsedDate = "17.07.2020 03:52".toDate()
        assertEquals("17.07.2020 03:52".toDate(), dump.lastUsedDate)

        dump.lastPaymentDate = "12.03.2019 03:52".toDate()
        assertEquals("12.03.2019 03:52".toDate(), dump.lastPaymentDate)
    }
}