package com.noahnewmanmack.farmingplugin2.listeners;


import com.noahnewmanmack.farmingplugin2.enums.FarmingYieldTiers;
import com.noahnewmanmack.farmingplugin2.enums.MaterialYields;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.apache.commons.lang.ObjectUtils.Null;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

/**
 * <b>FarmListener</b><br> Modifies any event pertaining to the breaking and harvesting of crops.<br>
 * <b>Method: </b>listenForFarmBreak: Modifies a given BlockBreakEvent if block fits criteria.
 */
public class FarmListener implements Listener {


  /**
   * If the given event happens to a block detailed in our maps, then proceed with
   * drop event conditions.
   *
   * @param event: an event where block is being broken by player.
   */
  @EventHandler
  public void listenForFarmBreak(BlockBreakEvent event) throws NullPointerException {

    // declaring variables as opposed to doing extensive calls to save processing time
      // from what I understand minecraft servers can only run on one processor at a time
      // so speed more valuable than data.

    ItemStack itemInUse = event.getPlayer().getInventory().getItemInMainHand();
    event.getPlayer().getServer().getConsoleSender().sendMessage("Item: " + itemInUse.getType());
    Material blockBroken = event.getBlock().getType();
    FarmingYieldTiers yieldTier = null;
    MaterialYields materialYield = null;

    // if block broken requires plugin handling, fire.
    if (isValidBlockType(blockBroken)) {
      event.getBlock().setType(Material.AIR);
      event.setCancelled(true);
    }
    else {
      return;
    }

    // if item in hand is valid, execute
    if (isValidToolType(itemInUse)) {

      event.getPlayer().getServer().getConsoleSender().sendMessage("Made past tool check");

      // get the appropriate yield tier from yield tier.
      for (FarmingYieldTiers fyt : FarmingYieldTiers.values()){
        if (fyt.mat.equals(itemInUse.getType())) {yieldTier = fyt;}
      }

      // get material yield from enumerator
      for (MaterialYields my : MaterialYields.values()){
        if (my.type.equals(blockBroken)) { materialYield = my;}
      }

      // filter for non-ageable crop.
      if (!(blockBroken.equals(Material.PUMPKIN)
          || blockBroken.equals(Material.MELON)
          || blockBroken.equals(Material.COCOA))) {

        //this is an ageable crop.
        org.bukkit.block.data.Ageable ag = ((Ageable) event.getBlock().getBlockData());
        if (ag.getAge() != ag.getMaximumAge()) {
          // If age is not maximum age, return the appropriate yield for spoiling a crop.
          yieldTier = FarmingYieldTiers.BROKE_SEED;
        }

      }


      // we gathered the appropriate yield class, and the materials to yield.
      // now we set block to air, and drop items.

      event.getPlayer().getServer().getConsoleSender().sendMessage("Setting to Air!");
      Random ran = new Random();

      int mult = 1;
      if (itemInUse.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
        mult = itemInUse.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
      }

      ItemStack drop;
      //set dropb
      drop = new ItemStack(materialYield.seedOutput,
          ran.ints(yieldTier.seedMin, yieldTier.seedMin + 2)
              .limit(1).toArray()[0]);

      // drop seed output
      event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), drop);

      // set drop
      drop = new ItemStack(materialYield.cropOutput, yieldTier.cropYield * mult);

      //drop crop output
      event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), drop);

      // damage item.
      // item in use has to be not null to get to this point in code so dw.
      addDamage(event.getPlayer().getItemInUse(), ran);
    }
  }

  // adds damage to an item if it passes unbreakable check
  private void addDamage(ItemStack item, Random ran) throws NullPointerException {
    int unbrk = item.getEnchantmentLevel(Enchantment.DURABILITY);
    double prob = 100.00/(1 + unbrk);
    if (ran.ints(0,100).limit(1).toArray()[0] < prob) {
      //cast metadata to damageable to set damage on item.
      Damageable dmg = ((Damageable)item.getItemMeta());
      dmg.setDamage(dmg.getDamage() + 1);
    }
  }

  // checks validity of the block being broken for the listener.
  private boolean isValidBlockType(Material m) {
    for (MaterialYields valid: MaterialYields.values()) {
      if (m.equals(valid.type)) { return true; }
    }
    return false;
  }

  // checks validity of the tool type being broken for the lisener.
  private boolean isValidToolType(ItemStack m) {


    if (m == null) {return false;}

    for (FarmingYieldTiers fyt : FarmingYieldTiers.values()) {
      if (fyt.mat.equals(m.getType())) { return true; }
    }
    return false;
  }

}

