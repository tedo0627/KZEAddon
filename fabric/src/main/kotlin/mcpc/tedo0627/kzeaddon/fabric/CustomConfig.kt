package mcpc.tedo0627.kzeaddon.fabric

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.stream.JsonWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object CustomConfig {

    private val json: JsonObject

    init {
        try {
            val file = File("config", "KZEAddon.json")
            if (!file.exists()) {
                val writer = JsonWriter(FileWriter(file))
                writer.setIndent("    ")
                Gson().toJson(JsonObject(), writer)
                writer.close()
            }

            val reader = FileReader(file)
            json = Gson().fromJson(reader, JsonObject::class.java)
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun getString(key: String, default: String): String {
        return json.getAsJsonPrimitive(key)?.asString ?: default
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return json.getAsJsonPrimitive(key)?.asBoolean ?: default
    }

    fun <T : Enum<T>> getEnum(key: String, default: Enum<T>): Enum<T> {
        val obj = json.getAsJsonPrimitive(key) ?: return default
        val ordinal = obj.asInt

        val enums = default::class.java.enumConstants
        if (enums.size <= ordinal) return default

        return enums[ordinal]
    }

    fun set(key: String, value: Boolean) {
        json.add(key, JsonPrimitive(value))
    }

    fun set(key: String, value: Number) {
        json.add(key, JsonPrimitive(value))
    }

    fun set(key: String, value: String) {
        json.add(key, JsonPrimitive(value))
    }

    fun set(key: String, value: Enum<*>) {
        json.add(key, JsonPrimitive(value.ordinal))
    }

    fun save() {
        try {
            val file = File("config", "KZEAddon.json")
            val writer = JsonWriter(FileWriter(file))
            writer.setIndent("    ")
            Gson().toJson(json, writer)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}