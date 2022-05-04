package com.noahnewmanmack.farmingplugin2.listeners;

import com.noahnewmanmack.farmingplugin2.files.NoTrampleFile;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listener implementation checking for whether the player may not trample crops.
 * <br><b>Method</b> checkTrampleEvent: if the event is valid, don't trample crops.
 */
public class TrampleListener implements Listener {

  /**
   * Checks whether a given player interact event
   * constitutes a trample, if the player has permissions, and if the
   * trample was farmland. Otherwise, will let event pass.
   * @param event Player Interaction w/ a block.
   */
 @EventHandler
 public void checkTrampleEvent(PlayerInteractEvent event) {
   //check: was it a trampling? does player have permissions?
   if (event.getClickedBlock() != null
       && event.getClickedBlock().getType().equals(Material.FARMLAND)
       && event.getAction().equals(Action.PHYSICAL)
       && NoTrampleFile.containsUsername(event.getPlayer().getName())) {
     event.setCancelled(true);
   }
 }
}
