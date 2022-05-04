package com.noahnewmanmack.farmingplugin2.files;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class NoTrampleFile {

  public static File file;
  public static FileConfiguration fileConfiguration;

  public static void setup(){
    file = new File(Bukkit.getServer()
        .getPluginManager().getPlugin("FarmingPlugin")
        .getDataFolder(), "no_trample.yml");

    if (file.exists()) {
      fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    else {
      try {
        file.createNewFile();
      } catch (IOException e) {
        System.out.println("Tried to create new no_trample.yml; encountered error.");
      }
    }

    fileConfiguration = YamlConfiguration.loadConfiguration(file);

  }

  public static FileConfiguration getFileConfiguration(){
    return fileConfiguration;
  }


  public static boolean containsUsername(String user) {
    return fileConfiguration.contains(user);
  }

  public static void toggleUser(String user) {
    if (containsUsername(user)) {
      fileConfiguration.set(user, null);
    }
    else {
      fileConfiguration.set(user, "no-trample");
    }
    trySave();
  }

  public static void trySave() {
    try {
      fileConfiguration.save(file);
    } catch (IOException e) {
      System.out.println("Could not save file!");
    }
  }

}
