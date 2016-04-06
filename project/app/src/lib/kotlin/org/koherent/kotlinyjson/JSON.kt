package org.koherent.kotlinyjson

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.util.ArrayList
import java.util.HashMap

public class JSON {
    private var jsonObject: JSONObject?
    private var jsonArray: JSONArray?

    private var parent: JSON?
    private var name: String?
    private var index: Int?

    init {
        jsonObject = null
        jsonArray = null

        parent = null
        name = null
        index = null
    }

    public constructor(bytes: ByteArray) {
        val string = String(bytes, Charsets.UTF_8)

        try {
            jsonObject = JSONObject(string)
        } catch (e: JSONException) {
            try {
                jsonArray = JSONArray(string)
            } catch (e2: JSONException) {
                try {
                    val parent = JSON("[$string]".toByteArray(Charsets.UTF_8))
                    if (parent.getJSONArray()?.length() == 1) {
                        this.parent = parent
                        index = 0
                    }
                } catch (e3: JSONException) {
                }
            }
        }
    }

    public constructor(inputStream: InputStream) : this(inputStreamToByteArray(inputStream) ?: ByteArray(0)) {
    }

    public constructor(file: File) : this(inputStreamToByteArray(FileInputStream(file), true) ?: ByteArray(0)) {
    }

    public constructor(value: Boolean) {
        val jsonArray = JSONArray()
        jsonArray.put(value)
        parent = JSON(jsonArray)
        index = 0
    }

    public constructor(value: Int) {
        val jsonArray = JSONArray()
        jsonArray.put(value)
        parent = JSON(jsonArray)
        index = 0
    }

    public constructor(value: Long) {
        val jsonArray = JSONArray()
        jsonArray.put(value)
        parent = JSON(jsonArray)
        index = 0
    }

    public constructor(value: Double) {
        val jsonArray = JSONArray()
        jsonArray.put(value)
        parent = JSON(jsonArray)
        index = 0
    }

    public constructor(value: String) {
        val jsonArray = JSONArray()
        jsonArray.put(value)
        parent = JSON(jsonArray)
        index = 0
    }

    public constructor(value: List<JSON>) : this(("[" + value.map { it.rawString() ?: "" /* will create illegal JSON String */ }.joinToString(",") + "]").toByteArray(Charsets.UTF_8)) {
    }

    public constructor(value: Map<String, JSON>) : this(("{" + value.map { JSONObject.quote(it.key) + ":" + (it.value.rawString() ?: "") }.joinToString(",") + "}").toByteArray(Charsets.UTF_8)) {
    }

    private constructor(parent: JSON, name: String) {
        this.parent = parent
        this.name = name
    }

    private constructor(parent: JSON, index: Int) {
        this.parent = parent
        this.index = index
    }

    private constructor(jsonArray: JSONArray) {
        this.jsonArray = jsonArray
    }

    public operator fun get(name: String): JSON {
        return JSON(this, name)
    }

    public operator fun get(index: Int): JSON {
        return JSON(this, index)
    }

    private fun <T : Any> getValue(fromParentObject: (JSONObject, String) -> T?, fromParentArray: (JSONArray, Int) -> T?): T? {
        try {
            if (name is String) {
                val jsonObject = parent?.getJSONObject()
                if (jsonObject is JSONObject) {
                    return fromParentObject(jsonObject, name!!)
                }
            } else if (index is Int) {
                val jsonArray = parent?.getJSONArray()
                if (jsonArray is JSONArray) {
                    return fromParentArray(jsonArray, index!!)
                }
            }
        } catch(e: JSONException) {
        }

        return null
    }

    private fun getJSONObject(): JSONObject? {
        if (jsonObject !is JSONObject) {
            jsonObject = getValue({ o, n -> o.getJSONObject(n) }, { a, i -> a.getJSONObject(i) })
        }

        return jsonObject
    }

    private fun getJSONArray(): JSONArray? {
        if (jsonArray !is JSONArray) {
            jsonArray = getValue({ o, n -> o.getJSONArray(n) }, { a, i -> a.getJSONArray(i) })
        }

        return jsonArray
    }

    public val boolean: Boolean?
        get() {
            return getValue({ o, n -> o.getBoolean(n) }, { a, i -> a.getBoolean(i) })
        }

    public val int: Int?
        get() {
            return getValue({ o, n -> o.getInt(n) }, { a, i -> a.getInt(i) })
        }

    public val long: Long?
        get() {
            return getValue({ o, n -> o.getLong(n) }, { a, i -> a.getLong(i) })
        }

    public val double: Double?
        get() {
            return getValue({ o, n -> o.getDouble(n) }, { a, i -> a.getDouble(i) })
        }

    public val string: String?
        get() {
            return getValue({ o, n -> o.getString(n) }, { a, i -> a.getString(i) })
        }

    public val list: List<JSON>?
        get() {
            val length = getJSONArray()?.length()
            if (length is Int) {
                val result = ArrayList<JSON>()
                for (index in 0..(length - 1)) {
                    result.add(JSON(this, index))
                }
                return result
            } else {
                return null
            }
        }

    public val map: Map<String, JSON>?
        get() {
            val names = getJSONObject()?.keys()
            if (names is Iterator<String>) {
                val result = HashMap<String, JSON>()
                while (names.hasNext()) {
                    val name = names.next()
                    result.put(name, this.get(name))
                }
                return result
            } else {
                return null
            }
        }

    public fun rawBytes(): ByteArray? {
        return rawString()?.toByteArray(Charsets.UTF_8)
    }

    public fun rawString(): String? {
        val jsonObject = getJSONObject()
        if (jsonObject != null) {
            return jsonObject.toString()
        }

        val jsonArray = getJSONArray()
        if (jsonArray != null) {
            return jsonArray.toString()
        }

        val booleanValue = boolean
        if (booleanValue != null) {
            return booleanValue.toString()
        }

        val doubleValue = double
        if (doubleValue != null) {
            if (doubleValue.toLong().toDouble() != doubleValue) { // has decimals
                return doubleValue.toString()
            }
        }

        val longValue = long // int is included
        if (longValue != null) {
            return longValue.toString()
        }

        val stringValue = string
        if (stringValue != null) {
            return JSONObject.quote(stringValue)
        }

        return null
    }

    override public fun toString(): String {
        return rawString() ?: ""
    }

    override public fun equals(another: Any?): Boolean {
        if (!(another is JSON)) {
            return false
        }

        val list1 = list
        if (list1 != null) {
            val list2 = another.list
            if (list2 != null) {
                return list1 == list2
            } else {
                return false
            }
        }

        val map1 = map
        if (map1 != null) {
            val map2 = another.map
            if (map2 != null) {
                return map1 == map2
            } else {
                return false
            }
        }

        val boolean1 = boolean
        if (boolean1 != null) {
            val boolean2 = another.boolean
            if (boolean2 != null) {
                return boolean1 == boolean2
            } else {
                return false
            }
        }

        val double1 = double
        if (double1 != null) {
            if (double1.toLong().toDouble() != double1) {
                val double2 = another.double
                if (double2 != null) {
                    return double1 == double2
                }
            }
        }

        val long1 = long
        if (long1 != null) {
            val long2 = another.long
            if (long2 != null) {
                return long1 == long2
            } else {
                return false
            }
        }

        val string1 = string
        if (string1 != null) {
            val string2 = another.string
            if (string2 != null) {
                return string1 == string2
            } else {
                return false
            }
        }

        return true // null == null
    }
}

public val JSON.booleanValue: Boolean
    get() = boolean ?: false

public val JSON.intValue: Int
    get() = int ?: 0

public val JSON.longValue: Long
    get() = long ?: 0L

public val JSON.doubleValue: Double
    get() = double ?: 0.0

public val JSON.stringValue: String
    get() = string ?: ""

public val JSON.listValue: List<JSON>
    get() = list ?: listOf()

public val JSON.mapValue: Map<String, JSON>
    get() = map ?: mapOf()

private fun inputStreamToByteArray(inputStream: InputStream, closeWhenDone: Boolean = false): ByteArray? {
    try {
        val buffer = ByteArray(0x1000)
        val outputStream = ByteArrayOutputStream()

        while (true) {
            val length = inputStream.read(buffer)
            if (length < 0) {
                break
            }

            outputStream.write(buffer, 0, length)
        }

        return outputStream.toByteArray()
    } catch (e: IOException) {
        return null
    } finally {
        if (closeWhenDone) {
            try {
                inputStream.close()
            } catch (e: IOException) {
            }
        }
    }
}