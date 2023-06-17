package mcpc.tedo0627.kzeaddon.forge.option

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.stream.JsonWriter
import com.mojang.serialization.JsonOps
import net.minecraft.client.OptionInstance
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object OptionConfig {

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
            val json = Gson().fromJson(reader, JsonObject::class.java)
            reader.close()

            AddonOptions.getOptionsMap().forEach { load(json, it.key, it.value) }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    private fun <T> load(json: JsonObject, key: String, option: OptionInstance<T>) {
        val dataResult = option.codec().parse(JsonOps.INSTANCE, json.get(key))
        dataResult.result().ifPresent { option.set(it) }
    }

    fun save() {
        try {
            val json = JsonObject()
            AddonOptions.getOptionsMap().forEach { save(json, it.key, it.value) }

            val file = File("config", "KZEAddon.json")
            val writer = JsonWriter(FileWriter(file))
            writer.setIndent("    ")
            Gson().toJson(json, writer)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun <T> save(json: JsonObject, key: String, option: OptionInstance<T>) {
        val dataResult = option.codec().encodeStart(JsonOps.INSTANCE, option.get())
        json.add(key, dataResult.result().get())
    }
}