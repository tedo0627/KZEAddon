package mcpc.tedo0627.kzeaddon

import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.WritingMode
import java.io.File

class Config {

    val config = CommentedFileConfig.builder(File("config/KZEAddon.toml")).sync().autosave().writingMode(WritingMode.REPLACE).build()
}