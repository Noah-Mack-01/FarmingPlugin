package com.noahnewmanmack.farmingplugin.events.listener;

import java.util.ArrayList;
import java.util.List;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;

/**
 * <B> GLITCH CHECKERS </B>
 * <br> Checks to see if there are workarounds being attempted and prevents them
 * from occuring.
 */
public class YieldGlitchListener implements Listener {

  private final static List<Material> affectedMaterialsList;

  /*
   * This is a list of materials to look for when an event occurs to crops.
   */
  static {
    affectedMaterialsList = new ArrayList<>();
    affectedMaterialsList.add(Material.WHEAT);
    affectedMaterialsList.add(Material.POTATOES);
    affectedMaterialsList.add(Material.CARROTS);
    affectedMaterialsList.add(Material.BEETROOTS);
    affectedMaterialsList.add(Material.MELON_STEM);
    affectedMaterialsList.add(Material.PUMPKIN_STEM);
    affectedMaterialsList.add(Material.FARMLAND);
  }

  /**
   * Event handler to prevent tnt explosions. Yes, this is too broad.
   * Couldn't stop the effect of the explosion on specific blocks for some reason.
   * @param e blow up event.
   */
  @EventHandler
  public void antiGrief(BlockExplodeEvent e) {
    List<Block> modify = e.blockList();
    for (Block b : modify) {
      if (affectedMaterialsList.contains(b.getType())
          || b.getType().equals(Material.MELON)
          || b.getType().equals(Material.PUMPKIN)) {
        b.setType(Material.AIR);
      }
    }
  }


  /**
   * On a piston hitting a piece of crop, it will make sure not to yield any crop.
   * Punished player by ruining crop.
   * @param e physics event from the piston interaction.
   */
  @EventHandler
  public void onPistonTomFoolery(BlockPhysicsEvent e) {
    if (e.getSourceBlock().getType().equals(Material.PISTON)
        && affectedMaterialsList.contains(e.getBlock().getType())) {
      e.getBlock().setType(Material.AIR);
      e.setCancelled(true);
    }
  }


  /**
   * On the event of a player breaking farmland beneath a crop,
   * cancels the item drops to prevent crops from spawning
   * and also makes sure to drop the appropriate item of the block in question.
   *  @param e broken block event.
   */
  @EventHandler
  public void onFarmlandBreak(BlockBreakEvent e) {
    if (e.getBlock().getType().equals(Material.FARMLAND)) {
      e.setDropItems(false);
      e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(),
          new ItemStack(Material.DIRT, 1));
    }
  }


}
