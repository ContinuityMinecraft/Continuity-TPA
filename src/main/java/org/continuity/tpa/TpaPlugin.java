package org.continuity.tpa;

import org.bukkit.plugin.java.JavaPlugin;
import org.continuity.tpa.commands.TpaCommand;
import org.continuity.tpa.commands.TpnCommand;
import org.continuity.tpa.commands.TpyCommand;

public final class TpaPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Utils.getConfig().options().copyDefaults(true);
        saveConfig();
        this.getCommand("tpa").setExecutor(new TpaCommand());
        this.getCommand("tpy").setExecutor(new TpyCommand());
        this.getCommand("tpn").setExecutor(new TpnCommand());
    }

    public static TpaPlugin getPlugin(){
        return getPlugin(TpaPlugin.class);
    }
}
