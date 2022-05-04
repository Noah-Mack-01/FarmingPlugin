package com.noahnewmanmack.farmingplugin2.listeners;

import com.noahnewmanmack.farmingplugin2.enums.MaterialYields;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;



/**
 * <B> GLITCH CHECKERS </B>
 * <br> Checks to see if there are workarounds being attempted and prevents them
 * from occuring.<br>
 * <br><b>Method</b> listenForExplosion: prevents TNT farming.
 * <br><b>Method</b> listenForEntityExplosion: prevents creeper farming.
 * <br><b>Method</b> listenForFarmlandBreak: prevents breaking blocks below crops.
 * <br><b>Method </b>listenForPistonExtension: prevents breaking crops with pistons.
 * <br><b>Method </b>listenForPistonRetraction: prevents breaking crops with stick piston retraction.
 * <br><b>Method </b>listenForTrampleEvent: prevents mob trampling circumvention.
 * <br><b>Method </b>listenForFlowOntoCrops: prevents water, lava from destroying crops. With this one, yes, I know its problematic, as it will effect performance. Consider setting it to air before cancelling?
 */
public class GlitchPreventionListener implements Listener  {


  /**
   * Prevents farmland or crops from being exploded via TNT.
   * @param e explosion event.
   */
  @EventHandler
  public void listenForExplosion(BlockExplodeEvent e) {
    List<Block> lb = e.blockList(); // list of blocks exploding in the event
    lb.removeIf(this::isVulnerable);
  }

  @EventHandler
  public void listenForFarmlandBreak(BlockBreakEvent e) {
    if (e.getBlock().getType().equals(Material.FARMLAND)
        && isVulnerable(e.getBlock().getLocation().add(0,1,0).getBlock())) {
      e.setCancelled(true);
    }
  }

  /**
   * Prevents farmland or crops from being exploded via an entity.
   * @param e explosion event.
   */
  @EventHandler
  public void listenForEntityExplosion(EntityExplodeEvent e) {
    List<Block> lb = e.blockList(); // list of blocks exploding in the event
    lb.removeIf(this::isVulnerable);
  }

  /**
   * Cancels a piston activation if the piston is being used to displace crops.
   * @param e piston extension event.
   */
  @EventHandler
  public void listenForPistonExtension(BlockPistonExtendEvent e) {
    for (Block b: e.getBlocks()) {
      if (isVulnerable(b)) {
        e.setCancelled(true);
        break;
      }
    }
  }

  /**
   * Cancels a piston activation if the piston is being used to displace crops
   * via retraction of sticky piston.
   * @param e piston extension event.
   */
  @EventHandler
  public void listenForPistonRetraction(BlockPistonRetractEvent e) {
    for (Block b: e.getBlocks()) {
      if (isVulnerable(b)) {
        e.setCancelled(true);
        break;
      }
    }
  }

  /**
   * Prevent Water from displacing crops as bypass.
   * @param e water or lava flow event.
   */
  @EventHandler
  public void listenForFlowOntoCrops(BlockFromToEvent e) {
    Material mat = e.getToBlock().getType();
    if (mat.equals(Material.MELON) || mat.equals(Material.PUMPKIN)) { return; }
    if (isVulnerable(e.getToBlock())) {
      e.setCancelled(true);
    }
  }

  /**
   * Prevents Mobs from Trampling Crops as Bypass.
   * @param event Mob Trampling Crops.
   */
  @EventHandler
  public void listenForTrampleEvent(EntityInteractEvent event) {
    //check: was it a trampling? does player have permissions?
    if (event.getBlock().getType().equals(Material.FARMLAND)) {
      event.setCancelled(true);
    }
  }


  private boolean isVulnerable(Block b) {
    for (MaterialYields my : MaterialYields.values()) {
      if (my.type.equals(b.getType())) { return true; }
    }
    return (b.getType().equals(Material.FARMLAND));
  }


}
