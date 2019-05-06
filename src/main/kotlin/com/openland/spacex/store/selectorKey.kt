package com.openland.spacex.store

import com.openland.spacex.model.InputValue
import org.json.JSONArray
import org.json.JSONObject

private fun selectorKey(value: InputValue, arguments: JSONObject): String? {
    if (value is InputValue.Int) {
        return value.value.toString()
    } else if (value is InputValue.Reference) {
        if (arguments.has(value.name)) {
            val ex = arguments.get(value.name)
            if (ex == null) {
                return "null"
            } else if (ex is String) {
                return "\"" + ex + "\""
            } else if (ex is Number) {
                return ex.toString()
            } else if (ex is Boolean) {
                return ex.toString()
            } else if (ex is JSONArray) {
                val res = mutableListOf<String>()

                return "[]"
            } else {
                error("Unknown input value")
            }
        } else {
            return null
        }
    } else {
        error("Unknown input value")
    }
}

fun selectorKey(name: String, fieldArguments: Map<String, InputValue>, arguments: JSONObject): String {
    if (fieldArguments.isEmpty()) {
        return name
    }
    val mapped = fieldArguments.mapValues { selectorKey(it.value, arguments) }
    val sortedKeys = mapped.keys.sortedBy { it }
    val converted = mutableListOf<String>()
    for (k in sortedKeys) {
        if (mapped[k] != null) {
            converted.add(k + ":" + mapped[k])
        }
    }
    return name + "(" + converted.joinToString(",") + ")"
}