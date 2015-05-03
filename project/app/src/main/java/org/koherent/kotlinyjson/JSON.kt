import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
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

    public constructor(data: ByteArray) {
        val string: String = java.lang.String(data, "UTF-8").toString()

        try {
            jsonObject = JSONObject(string)
        } catch (e: JSONException) {
            try {
                jsonArray = JSONArray(string)
            } catch (e2: JSONException) {
            }
        }
    }

    private constructor(parent: JSON, name: String) {
        this.parent = parent
        this.name = name
    }

    private constructor(parent: JSON, index: Int) {
        this.parent = parent
        this.index = index
    }

    public fun get(name: String): JSON {
        return JSON(this, name)
    }

    public fun get(index: Int): JSON {
        return JSON(this, index)
    }

    private fun getJSONObject(): JSONObject? {
        if (jsonObject !is JSONObject) {
            if (name is String) {
                jsonObject = parent?.getJSONObject()?.getJSONObject(name)
            } else if (index is Int) {
                jsonObject = parent?.getJSONArray()?.getJSONObject(index!!)
            }
        }

        return jsonObject
    }

    private fun getJSONArray(): JSONArray? {
        if (jsonArray !is JSONArray) {
            if (name is String) {
                jsonArray = parent?.getJSONObject()?.getJSONArray(name)
            } else if (index is Int) {
                jsonArray = parent?.getJSONArray()?.getJSONArray(index!!)
            }
        }

        return jsonArray
    }

    public val boolean: Boolean?
        get() {
            if (name is String) {
                return parent?.getJSONObject()?.getBoolean(name)
            } else if (index is Int) {
                return parent?.getJSONArray()?.getBoolean(index!!)
            } else {
                return null
            }
        }

    public val int: Int?
        get() {
            if (name is String) {
                return parent?.getJSONObject()?.getInt(name)
            } else if (index is Int) {
                return parent?.getJSONArray()?.getInt(index!!)
            } else {
                return null
            }
        }

    public val long: Long?
        get() {
            if (name is String) {
                return parent?.getJSONObject()?.getLong(name)
            } else if (index is Int) {
                return parent?.getJSONArray()?.getLong(index!!)
            } else {
                return null
            }
        }

    public val double: Double?
        get() {
            if (name is String) {
                return parent?.getJSONObject()?.getDouble(name)
            } else if (index is Int) {
                return parent?.getJSONArray()?.getDouble(index!!)
            } else {
                return null
            }
        }

    public val string: String?
        get() {
            if (name is String) {
                return parent?.getJSONObject()?.getString(name)
            } else if (index is Int) {
                return parent?.getJSONArray()?.getString(index!!)
            } else {
                return null
            }
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
                    result.put(name, this[name])
                }

                return result
            } else {
                return null
            }
        }

}

public val JSON.booleanValue: Boolean
    get() = boolean!!

public val JSON.intValue: Int
    get() = int!!

public val JSON.longValue: Long
    get() = long!!

public val JSON.doubleValue: Double
    get() = double!!

public val JSON.stringValue: String
    get() = string!!
