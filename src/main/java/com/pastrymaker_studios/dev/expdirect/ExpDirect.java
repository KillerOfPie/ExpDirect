package com.pastrymaker_studios.dev.expdirect;

import com.bergerkiller.bukkit.common.PluginBase;
import org.bukkit.command.CommandSender;

public class ExpDirect extends PluginBase {
    /**
     * Gets the minimum BKCommonLib version required for this Plugin to
     * function<br>
     * Override this and return Common.VERSION as result (compiler will
     * automatically inline this)
     *
     * @return Minimum BKCommonLib version number
     */
    @Override
    public int getMinimumLibVersion() {
        return 0;
    }

    /**
     * Called when this plugin is being enabled
     */
    @Override
    public void enable() {

    }

    /**
     * Called when this plugin is being disabled
     */
    @Override
    public void disable() {

    }

    /**
     * Handles a command
     *
     * @param sender  of the command
     * @param command name
     * @param args    of the command
     * @return True if handled, False if not
     */
    @Override
    public boolean command(CommandSender sender, String command, String[] args) {
        return false;
    }
}
