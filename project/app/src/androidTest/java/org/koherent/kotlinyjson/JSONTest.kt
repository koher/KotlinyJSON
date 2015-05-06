package org.koherent.kotlinyjson

import junit.framework.TestCase
import kotlin.test.assertEquals
import kotlin.test.fail

public class JSONTest: TestCase() {
    public fun testBasic() {
        val json = JSON("""{"foo":{"bar":true},"baz":["abc",{"qux":[2,3.0]}]}""".toByteArray(Charsets.UTF_8))

        assertEquals(true, json["foo"]["bar"].boolean)
        assertEquals("abc", json["baz"][0].string)
        assertEquals(2, json["baz"][1]["qux"][0].int)
        assertEquals(3.0, json["baz"][1]["qux"][1].double)

        assertEquals(null, json["xyz"].int)
        assertEquals(null, json[0].int)
    }

    public fun testConstructor() {
        if (true) { // Boolean
            val json = JSON("""true""".toByteArray(Charsets.UTF_8))
            assertEquals(true, json.booleanValue)
        }

        if (true) { // Boolean
            val json = JSON("""false""".toByteArray(Charsets.UTF_8))
            assertEquals(false, json.booleanValue)
        }

        if (true) { // Int
            val json = JSON("""123""".toByteArray(Charsets.UTF_8))
            assertEquals(123, json.intValue)
        }

        if (true) { // Long
            val json = JSON(Long.MAX_VALUE.toString().toByteArray(Charsets.UTF_8))
            assertEquals(Long.MAX_VALUE, json.longValue)
        }

        if (true) { // Double
            val json = JSON("""3.5""".toByteArray(Charsets.UTF_8))
            assertEquals(3.5, json.doubleValue)
        }

        if (true) { // String
            val json = JSON(""""Hello"""".toByteArray(Charsets.UTF_8))
            assertEquals("Hello", json.stringValue)
        }

        if (true) { // String
            val json = JSON(""""Hello","World"""".toByteArray(Charsets.UTF_8))
            assertEquals(null, json.string)
        }
    }
}
