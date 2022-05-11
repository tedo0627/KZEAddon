package mcpc.tedo0627.kzeaddon.fabric;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;

public class CustomConfig {

    private static final File file = new File("config", "KZEAddon.json");

    public boolean deleteJoinMessage = false;
    public boolean deleteQuitMessage = false;

    public CustomConfig() {}

    public CustomConfig(boolean deleteJoinMessage, boolean deleteQuitMessage) {
        this.deleteJoinMessage = deleteJoinMessage;
        this.deleteQuitMessage = deleteQuitMessage;
    }

    public static CustomConfig load() {
        try {
            if (!file.exists()) {
                JsonWriter writer = new JsonWriter(new FileWriter(file));
                writer.setIndent("    ");
                new Gson().toJson(new CustomConfig(), CustomConfig.class, writer);
                writer.close();
            }
            JsonReader reader = new JsonReader(new FileReader(file));
            CustomConfig config = new Gson().fromJson(reader, CustomConfig.class);
            reader.close();
            return config;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isDeleteJoinMessage() {
        return deleteJoinMessage;
    }

    public void setDeleteJoinMessage(boolean bool) {
        deleteJoinMessage = bool;
    }

    public boolean isDeleteQuitMessage() {
        return deleteQuitMessage;
    }

    public void setDeleteQuitMessage(boolean bool) {
        deleteQuitMessage = bool;
    }

    public void save() {
        try {
            JsonWriter writer = new JsonWriter(new FileWriter(file));
            writer.setIndent("    ");
            new Gson().toJson(this, CustomConfig.class, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
