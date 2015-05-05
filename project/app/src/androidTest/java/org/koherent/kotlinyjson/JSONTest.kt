package org.koherent.kotlinyjson

import junit.framework.TestCase
import kotlin.test.assertEquals
import kotlin.test.fail

public class JSONTest: TestCase() {
    public fun testBasic() {
        val json = JSON("""{"foo":{"bar":true},"baz":["abc",{"qux":[2,3.0]}]}""".toByteArray("UTF-8"))

        assertEquals(true, json["foo"]["bar"].boolean)
        assertEquals("abc", json["baz"][0].string)
        assertEquals(2, json["baz"][1]["qux"][0].int)
        assertEquals(3.0, json["baz"][1]["qux"][1].double)

        assertEquals(null, json["xyz"].int)
        assertEquals(null, json[0].int)
    }
}
