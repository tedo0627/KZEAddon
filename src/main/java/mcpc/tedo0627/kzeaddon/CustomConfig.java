package mcpc.tedo0627.kzeaddon;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import java.io.File;

public class CustomConfig {

    private CommentedFileConfig config;

    public CustomConfig() {
        config = CommentedFileConfig.builder(new File("config/KZEAddon.toml"))
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        config.load();
    }

    public boolean isHideJoinMessage() {
        return config.getOrElse("hideJoinMessage", false);
    }

    public void setHideJoinMessage(boolean bool) {
        config.set("hideJoinMessage", bool);
    }

    public boolean isHidePlayer() {
        return config.getOrElse("hidePlayer", false);
    }

    public void setHidePlayer(boolean bool) {
        config.set("hidePlayer", bool);
    }
}
