package pers.bibong.choptree2;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pers.bibong.choptree2.impl.listeners.ChopTreeEvent;
import pers.bibong.choptree2.impl.listeners.MarkBlockEvent;
import pers.bibong.choptree2.impl.utils.MessageHandle;
import pers.bibong.choptree2.impl.utils.YamlLoader;

import pers.bibong.choptree2.impl.utils.BlockUtils;

public final class ChopTree2 extends JavaPlugin {
    private static ChopTree2 plugin;

    private YamlLoader config;
    private YamlLoader setting;

    @Override
    public void onEnable() {
        if (this.init()) MessageHandle.INFO("插件初始化完畢。");

        this.reload();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void reload() {
        this.loadYaml();
        BlockUtils.initLogs();
        BlockUtils.initLeaves();
    }

    private void loadYaml() {
        this.config  = new YamlLoader(this, "config");
        this.setting = new YamlLoader(this, "setting");
    }

    private boolean init() {
        try {
            plugin = this;
            this.regEvents();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void regEvents() {
        getServer().getPluginManager().registerEvents(new ChopTreeEvent(), this);
        getServer().getPluginManager().registerEvents(new MarkBlockEvent(), this);
    }

    @NotNull
    public YamlLoader config() {
        return config;
    }

    @NotNull
    public YamlLoader setting() {
        return setting;
    }

    public static ChopTree2 inst() {
        return plugin;
    }
}
