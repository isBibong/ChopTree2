package pers.bibong.choptree2.impl.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class YamlLoader {
    private final JavaPlugin plugin;
    private final String     fileName;

    private File              file;
    private YamlConfiguration configuration;

    private void init(boolean replace) {
        this.file          = new File(this.plugin.getDataFolder().getPath(), File.separator + this.fileName + ".yml");
        this.configuration = new YamlConfiguration();
        if (this.file.exists()) {
            return;
        }

        try {this.plugin.saveResource(this.fileName + ".yml", replace);}
        catch (IllegalArgumentException e) {
            String color = MessageHandle.toColor("&4檔案取得失敗:[" + this.fileName + ".yml]");
            MessageHandle.WARN(color);
        }
    }

    public void load() {
        this.load(false);
    }

    public void load(boolean replace) {
        this.init(replace);

        try {this.configuration = YamlConfiguration.loadConfiguration(this.file);}
        catch (Exception e) {
            String color = MessageHandle.toColor("&4檔案加載失敗:[" + this.fileName + ".yml]");
            MessageHandle.WARN(color);
        }
    }

    public boolean save() {
        try {this.configuration.save(file);}
        catch (Exception e) {
            String color = MessageHandle.toColor("&4檔案儲存失敗:[ " + this.fileName + ".yml]");
            MessageHandle.WARN(color);
            return false;
        }
        return true;
    }

    public String getString(String path) {
        String getString = this.configuration.getString(path);
        String error     = String.format("請檢查路徑: %s 是否正確。", path);
        return getString == null ? error : getString;
    }

    public int getInt(String path) {
        return this.configuration.getInt(path);
    }

    public boolean getBoolean(String path) {
        return this.configuration.getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return this.configuration.getStringList(path);
    }

    public YamlConfiguration get() {
        return this.configuration;
    }

    public YamlLoader(@NotNull JavaPlugin plugin, @NotNull String fileName) {
        this.plugin   = plugin;
        this.fileName = fileName;

        this.load();
    }

    public YamlLoader(@NotNull JavaPlugin plugin, @NotNull String fileName, boolean replace) {
        this.plugin   = plugin;
        this.fileName = fileName;

        this.load(replace);
    }
}
