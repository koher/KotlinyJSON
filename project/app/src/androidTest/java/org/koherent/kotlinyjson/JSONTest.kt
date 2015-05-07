package org.koherent.kotlinyjson

import junit.framework.TestCase
import java.io.ByteArrayInputStream
import kotlin.test.assertEquals
import kotlin.test.fail

public class JSONTest: TestCase() {
    public fun testBasic() {
        doTestBasic(JSON(basicJSONString.toByteArray(Charsets.UTF_8)))
    }

    private val basicJSONString: String = """{"foo":{"bar":true},"baz":["abc",{"qux":[2,3.0]}]}"""

    private fun doTestBasic(json: JSON) {
        assertEquals(true, json["foo"]["bar"].boolean)
        assertEquals("abc", json["baz"][0].string)
        assertEquals(2, json["baz"][1]["qux"][0].int)
        assertEquals(3.0, json["baz"][1]["qux"][1].double)

        assertEquals(null, json["xyz"].int)
        assertEquals(null, json[0].int)
    }

    public fun testConstructorForData() {
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

        if (true) { // List
            val json = JSON("""[2,3.0,"5"]""".toByteArray(Charsets.UTF_8))
            val list = json.listValue
            assertEquals(2, list[0].intValue)
            assertEquals(3.0, list[1].doubleValue)
            assertEquals("5", list[2].stringValue)
        }

        if (true) { // Map
            val json = JSON("""{"foo":2,"bar":3.0,"baz":"5"}""".toByteArray(Charsets.UTF_8))
            val map = json.mapValue
            assertEquals(2, map["foo"]!!.intValue)
            assertEquals(3.0, map["bar"]!!.doubleValue)
            assertEquals("5", map["baz"]!!.stringValue)
        }
    }

    public fun testConstructorForValue() {
        if (true) { // Boolean
            val json = JSON(true)
            assertEquals(true, json.booleanValue)
        }

        if (true) { // Int
            val json = JSON(123)
            assertEquals(123, json.intValue)
        }

        if (true) { // Long
            val json = JSON(Long.MAX_VALUE)
            assertEquals(Long.MAX_VALUE, json.longValue)
        }

        if (true) { // Double
            val json = JSON(3.5)
            assertEquals(3.5, json.doubleValue)
        }

        if (true) { // String
            val json = JSON("Hello")
            assertEquals("Hello", json.stringValue)
        }

        if (true) { // List
            val json = JSON(arrayListOf(JSON(2), JSON(3.0), JSON("5")))
            val list = json.listValue
            assertEquals(2, list[0].intValue)
            assertEquals(3.0, list[1].doubleValue)
            assertEquals("5", list[2].stringValue)
        }

        if (true) { // Map
            val json = JSON(hashMapOf(Pair("foo", JSON(2)), Pair("bar", JSON(3.0)), Pair("baz", JSON("5"))))
            val map = json.mapValue
            assertEquals(2, map["foo"]!!.intValue)
            assertEquals(3.0, map["bar"]!!.doubleValue)
            assertEquals("5", map["baz"]!!.stringValue)
        }
    }

    public fun testConstructorForInputStream() {
        doTestBasic(JSON(ByteArrayInputStream(basicJSONString.toByteArray(Charsets.UTF_8))))
    }

    public fun testRawString() {
        if (true) { // Boolean
            val json = JSON("true".toByteArray(Charsets.UTF_8))
            assertEquals("true", json.rawString())
        }

        if (true) { // Boolean
            val json = JSON("false".toByteArray(Charsets.UTF_8))
            assertEquals("false", json.rawString())
        }

        if (true) { // Int
            val json = JSON("123".toByteArray(Charsets.UTF_8))
            assertEquals("123", json.rawString())
        }

        if (true) { // Long
            val json = JSON(Long.MAX_VALUE.toString().toByteArray(Charsets.UTF_8))
            assertEquals(Long.MAX_VALUE.toString(), json.rawString())
        }

        if (true) { // Double
            val json = JSON("3.5".toByteArray(Charsets.UTF_8))
            assertEquals("3.5", json.rawString())
        }

        if (true) { // String
            val json = JSON(""""Hello"""".toByteArray(Charsets.UTF_8))
            assertEquals(""""Hello"""", json.rawString())
        }

        if (true) { // String
            val json = JSON(""""\"Hello, World!!\""""".toByteArray(Charsets.UTF_8))
            assertEquals(""""\"Hello, World!!\""""", json.rawString())
        }

        if (true) { // List
            val json = JSON(JSON("""[2,3.0,"5"]""".toByteArray(Charsets.UTF_8)).rawString()!!.toByteArray(Charsets.UTF_8))
            val list = json.listValue
            assertEquals(2, list[0].intValue)
            assertEquals(3.0, list[1].doubleValue)
            assertEquals("5", list[2].stringValue)
        }

        if (true) { // Map
            val json = JSON(JSON("""{"foo":2,"bar":3.0,"baz":"5"}""".toByteArray(Charsets.UTF_8)).rawString()!!.toByteArray(Charsets.UTF_8))
            val map = json.mapValue
            assertEquals(2, map["foo"]!!.intValue)
            assertEquals(3.0, map["bar"]!!.doubleValue)
            assertEquals("5", map["baz"]!!.stringValue)
        }
    }
}
