package org.capy.tpaplugin;

import org.bukkit.configuration.file.FileConfiguration;

public class Utils {
    public static FileConfiguration getConfig() {
        return TpaPlugin.getPlugin().getConfig();
    }
}
