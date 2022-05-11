package mcpc.tedo0627.kzeaddon.forge;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import java.io.File;

public class CustomConfig {

    private final CommentedFileConfig config;

    public CustomConfig() {
        config = CommentedFileConfig.builder(new File("config/KZEAddon.toml"))
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        config.load();
    }

    public boolean isDeleteJoinMessage() {
        return config.getOrElse("message.deleteJoin", false);
    }

    public void setDeleteJoinMessage(boolean bool) {
        config.set("message.deleteJoin", bool);
    }

    public boolean isDeleteQuitMessage() {
        return config.getOrElse("message.deleteQuit", false);
    }

    public void setDeleteQuitMessage(boolean bool) {
        config.set("message.deleteQuit", bool);
    }

    public boolean isHidePlayer() {
        return config.getOrElse("hidePlayer", false);
    }

    public void setHidePlayer(boolean bool) {
        config.set("hidePlayer", bool);
    }

    public void save() {
        config.save();
    }
}
