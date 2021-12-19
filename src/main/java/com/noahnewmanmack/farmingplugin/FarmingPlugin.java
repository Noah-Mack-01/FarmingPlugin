package com.noahnewmanmack.farmingplugin;

import com.noahnewmanmack.farmingplugin.commands.SetTramplerCommand;
import com.noahnewmanmack.farmingplugin.events.listener.TrampleListener;
import com.noahnewmanmack.farmingplugin.events.listener.YieldGlitchListener;
import com.noahnewmanmack.farmingplugin.events.listener.YieldListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main Plugin Class.
 * <br> <b> Methods: </b> <br>
 * onEnable - when enabling server, register event listeners and command executors.
 * <br>onDisable - when disabling, do nothing.
 */
public class FarmingPlugin extends JavaPlugin  {

  /**
   * plugin enabler.
   */
  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new YieldListener(), this);
    getServer().getPluginManager().registerEvents(new TrampleListener(), this);
    getServer().getPluginManager().registerEvents(new YieldGlitchListener(), this);
    getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "TEST");
    getCommand("set-trample").setExecutor(new SetTramplerCommand());
  }


  @Override
  public void onDisable() {}


}
