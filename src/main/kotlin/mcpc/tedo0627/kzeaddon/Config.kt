package mcpc.tedo0627.kzeaddon

import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.WritingMode
import mcpc.tedo0627.kzeaddon.gui.KillLogGui
import mcpc.tedo0627.kzeaddon.listener.HidePlayerListener
import java.io.File

class Config {

    companion object {
        const val version = "1.0.0"
    }

    private val config = CommentedFileConfig.builder(File("config/KZEAddon.toml")).sync().autosave().writingMode(WritingMode.REPLACE).build()

    init {
        config.load()

        if (!config.contains("configVersion") || config.get<String>("configVersion") != version) {
            config.clear()
        }
    }

    var hidePlayer: HidePlayerListener.Type
        get() = config.getEnumOrElse("hidePlayer", HidePlayerListener.Type.DISABLE)
        set(value) {
            config.set<Int>("hidePlayer", value)
        }

    var displayBullet: Boolean
        get() = config.get<Boolean>("displayBullet") ?: false
        set(value) {
            config.set<Boolean>("displayBullet", value)
        }

    var displayReloadDuration: Boolean
        get() = config.get<Boolean>("displayReloadDuration") ?: false
        set(value) {
            config.set<Boolean>("displayReloadDuration", value)
        }

    var fillKillLogName: KillLogGui.Type
        get() = config.getEnumOrElse("fillKillLogName", KillLogGui.Type.DISABLE)
        set(value) {
            config.set<Boolean>("fillKillLogName", value)
        }

    fun getWeaponKillCount(weapon: String): Int {
        return config.getIntOrElse("weaponStats.${weapon}.killCount", 0)
    }

    fun setWeaponKillCount(weapon: String, count: Int) {
        config.set<Int>("weaponStats.${weapon}.killCount", count)
    }

    fun exist(name: String): Boolean {
        return config.contains(name)
    }

    fun <T> get(name: String): T {
        return config.get(name)
    }

    fun save() {
        config.set<String>("configVersion", version)
        config.save()
    }
}