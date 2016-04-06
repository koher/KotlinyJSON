KotlinyJSON
=====================

_KotlinyJSON_ is a thin wrapper of [_org.json_](https://github.com/stleary/JSON-java) to decode JSONs in Kotlin, which has __similar APIs to [_SwiftyJSON_](https://github.com/SwiftyJSON/SwiftyJSON)__

```kotlin
import org.koherent.kotlinyjson.JSON

...

val json = JSON(inputStream)

val bar: String? = json["foo"][2]["bar"].string
if (bar != null) {
    println(bar)
}
```

Usage and comparison with _SwiftyJSON_
---------------------

### Initialization

```swift
// SwiftyJSON
let json = JSON(data: data)
```

```kotlin
// KotlinyJSON
val json = JSON(inputStream)
```

### Subscript by [ ]

```swift
// SwiftyJSON
let child1 = json[i]
let child2 = json["key"]
```

```kotlin
// KotlinyJSON
val child1 = json[i]
val child2 = json["key"]
```

### Getters

#### Optional getters

```swift
// SwiftyJSON
let number: Int? = json["foo"].int
let bool: Bool? = json[42]["bar"].bool
```

```kotlin
// KotlinyJSON
val number: Int? = json["foo"].int
val boolean: Boolean? = json[42]["bar"].boolean
```

#### Non-optional getters

```swift
// SwiftyJSON
let number: Double = json["foo"].doubleValue
let string: String = json[42]["bar"].stringValue
```

```kotlin
// KotlinyJSON
val number: Double = json["foo"].doubleValue
val string: String = json[42]["bar"].stringValue
```

### Raw strings

```swift
// SwiftyJSON
let string: String? = json.rawString()
```

```kotlin
// KotlinyJSON
val string: String? = json.rawString()
```

### Raw bytes

```swift
// SwiftyJSON
let data: NSData? = json.rawData()
```

```kotlin
// KotlinyJSON
val bytes: ByteArray? = json.rawBytes()
```

License
---------------------
[The MIT License](LICENSE)
