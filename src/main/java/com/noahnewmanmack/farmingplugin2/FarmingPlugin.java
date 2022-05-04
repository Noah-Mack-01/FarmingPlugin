package com.noahnewmanmack.farmingplugin2;

import com.noahnewmanmack.farmingplugin2.commands.NoTrampleCommand;
import com.noahnewmanmack.farmingplugin2.files.NoTrampleFile;
import com.noahnewmanmack.farmingplugin2.listeners.FarmListener;
import com.noahnewmanmack.farmingplugin2.listeners.GlitchPreventionListener;
import com.noahnewmanmack.farmingplugin2.listeners.TrampleListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <b>FARMING PLUGIN</b>
 * <br><b>Method</b> onEnable: starts all needed operations for plugin.
 * <br><b>Method</b> onDisable: saves permissions and config.yml files.
 */
public class FarmingPlugin extends JavaPlugin {

  /**
   * Lets console know we're running, sets up config and permission files, command, and listeners.
   */
  @Override
  public void onEnable(){

    // lets us know in console that plugin has successfully started.
    getServer().getConsoleSender().sendMessage("Test!");

    // set up our config.yml--I'm pretty sure we need this for other custom config files to be creatable.
    getConfig().options().copyDefaults(true);
    saveConfig();

    // sets up no trample permissions file.
    NoTrampleFile.setup();

    // executor of no trample command
    getCommand("notrample").setExecutor(new NoTrampleCommand());


    // LISTENERS
    getServer().getPluginManager().registerEvents(new FarmListener(), this);
    getServer().getPluginManager().registerEvents(new TrampleListener(), this);
    getServer().getPluginManager().registerEvents(new GlitchPreventionListener(), this);

  }

  /**
   * Save files.
   */
  @Override
  public void onDisable(){
    NoTrampleFile.trySave();
    saveConfig();
  }


}
