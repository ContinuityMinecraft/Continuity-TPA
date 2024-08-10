package org.continuity.tpa;

import org.bukkit.configuration.file.FileConfiguration;

public class Utils {
    public static FileConfiguration getConfig() {
        return TpaPlugin.getPlugin().getConfig();
    }
}
