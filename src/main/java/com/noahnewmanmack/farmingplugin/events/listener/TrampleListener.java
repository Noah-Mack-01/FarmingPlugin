package com.noahnewmanmack.farmingplugin.events.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listener implementation checking for whether the player may not trample crops.
 * <br><b>EventListener: onTrample</b> - if the event is valid, don't trample crops.
 */
public class TrampleListener implements Listener {

  /**
   * Checks whether an event is valid, then avoids trampling if so.
   * @param ev a given PlayerInteractEvent.
   */
  @EventHandler
  public void onTrample(PlayerInteractEvent ev) {
    if (ev.getClickedBlock() == null) { return ;}
    if (!validEvent(ev)) { return; }
    ev.setCancelled(true);
  }


  // confirms whether the event is valid.
  // is the event a physical event? is the interacted block soil?
  // does the player have permission to avoid trampling?
  private boolean validEvent(PlayerInteractEvent ev) {
    boolean physicalEvent = ev.getAction().equals(Action.PHYSICAL);
    boolean soil = ev.getClickedBlock().getType().equals(Material.FARMLAND);
    boolean hasPermission = ev.getPlayer().hasPermission("group.nontrampler");
    return (hasPermission && physicalEvent && soil);
  }

}
