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

    public fun testRawBytes() {
        val bytes = JSON(basicJSONString.toByteArray(Charsets.UTF_8)).rawBytes()!!
        doTestBasic(JSON(bytes))
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

    public fun testBooleanValue() {
        if (true) {
            val json = JSON("""true""".toByteArray())
            assertEquals(true, json.booleanValue)
        }

        if (true) {
            val json = JSON("""false""".toByteArray())
            assertEquals(false, json.booleanValue)
        }

        if (true) {
            val json = JSON("""null""".toByteArray())
            assertEquals(false, json.booleanValue)
        }
    }

    public fun testIntValue() {
        if (true) {
            val json = JSON("""42""".toByteArray())
            assertEquals(42, json.intValue)
        }

        if (true) {
            val json = JSON("""0""".toByteArray())
            assertEquals(0, json.intValue)
        }

        if (true) {
            val json = JSON("""null""".toByteArray())
            assertEquals(0, json.intValue)
        }
    }

    public fun testLongValue() {
        if (true) {
            val json = JSON("""42""".toByteArray())
            assertEquals(42L, json.longValue)
        }

        if (true) {
            val json = JSON("""0""".toByteArray())
            assertEquals(0L, json.longValue)
        }

        if (true) {
            val json = JSON("""null""".toByteArray())
            assertEquals(0L, json.longValue)
        }
    }

    public fun testDoubleValue() {
        if (true) {
            val json = JSON("""42.0""".toByteArray())
            assertEquals(42.0, json.doubleValue)
        }

        if (true) {
            val json = JSON("""0.0""".toByteArray())
            assertEquals(0.0, json.doubleValue)
        }

        if (true) {
            val json = JSON("""null""".toByteArray())
            assertEquals(0.0, json.doubleValue)
        }
    }

    public fun testStringValue() {
        if (true) {
            val json = JSON(""""KotlinyJSON"""".toByteArray())
            assertEquals("KotlinyJSON", json.stringValue)
        }

        if (true) {
            val json = JSON("""""""".toByteArray())
            assertEquals("", json.stringValue)
        }

        if (true) {
            val json = JSON("""null""".toByteArray())
            assertEquals("", json.stringValue)
        }
    }

    public fun testListValue() {
        if (true) {
            val json = JSON("""[2, 3, 5]""".toByteArray())
            assertEquals(listOf(JSON(2), JSON(3), JSON(5)), json.listValue)
        }

        if (true) {
            val json = JSON("""[]""".toByteArray())
            assertEquals(listOf<Int>(), json.listValue)
        }

        if (true) {
            val json = JSON("""null""".toByteArray())
            assertEquals(listOf<Int>(), json.listValue)
        }
    }

    public fun testMapValue() {
        if (true) {
            val json = JSON("""{"a": 2, "b": 3, "c": 5}""".toByteArray())
            assertEquals(mapOf(Pair("a", JSON(2)), Pair("b", JSON(3)), Pair("c", JSON(5))), json.mapValue)
        }

        if (true) {
            val json = JSON("""{}""".toByteArray())
            assertEquals(mapOf<String, Int>(), json.mapValue)
        }

        if (true) {
            val json = JSON("""null""".toByteArray())
            assertEquals(mapOf<String, Int>(), json.mapValue)
        }
    }
}
