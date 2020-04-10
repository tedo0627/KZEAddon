package mcpc.tedo0627.kzeaddon

import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.WritingMode
import mcpc.tedo0627.kzeaddon.listener.HidePlayerListener
import java.io.File

class Config {

    private val config = CommentedFileConfig.builder(File("config/KZEAddon.toml")).sync().autosave().writingMode(WritingMode.REPLACE).build()

    init {
        config.load()
    }

    var hidePlayer: HidePlayerListener.Type
        get() {
            val ordinal = config.get<Int>("hidePlayer") ?: 0
            return HidePlayerListener.Type.values()[ordinal]
        }
        set(value) {
            config.set<Int>("hidePlayer", value.ordinal)
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

    fun save() {
        config.save()
    }
}