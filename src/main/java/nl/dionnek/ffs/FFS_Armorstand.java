package nl.dionnek.ffs;

import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorstandFS extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("amsce").setExecutor(new AmsceCommand(new AmsceUtils(this)));
    }
}