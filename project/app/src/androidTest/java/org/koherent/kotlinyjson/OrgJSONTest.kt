package org.koherent.kotlinyjson

import junit.framework.TestCase
import org.json.JSONException
import org.json.JSONObject
import kotlin.test.assertEquals
import kotlin.test.fail

public class OrgJSONTest: TestCase() {
    public fun testUpdate() {
        val json = JSONObject("""{"a":2,"b":{"c":3,"d":{"e":5,"f":7}}}""")

        val b = json.getJSONObject("b")
        val d = b.getJSONObject("d")

        assertEquals(3, b.getInt("c"))
        assertEquals(5, d.getInt("e"))
        assertEquals(7, d.getInt("f"))

        val newF = 11
        json.getJSONObject("b").getJSONObject("d").put("f", newF)

        assertEquals(11, json.getJSONObject("b").getJSONObject("d").getInt("f"))
        assertEquals(3, b.getInt("c"))
        assertEquals(5, d.getInt("e"))
        //assertEquals(7, d.getInt("f"))
        assertEquals(11, d.getInt("f")) // updated

        val newB = 13
        json.put("b", newB)

        assertEquals(13, json.getInt("b"))
        try {
            json.getJSONObject("b").getInt("c")
            fail()
        } catch (e: JSONException) {
        }

        assertEquals(3, b.getInt("c"))
        assertEquals(5, d.getInt("e"))
        assertEquals(11, d.getInt("f"))
    }

    public fun testNull() {
        val json = JSONObject("""{"a":null}""")

        assertEquals(true, json.isNull("a"))
        assertEquals(true, json.has("a"))
        assertEquals(true, json.isNull("b"))
        assertEquals(false, json.has("b"))
    }
}