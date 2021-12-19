package com.noahnewmanmack.farmingplugin.events.listener;


import com.noahnewmanmack.farmingplugin.utils.FarmingYieldTiers;
import com.noahnewmanmack.farmingplugin.utils.MaterialYields;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.bukkit.ChatColor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * <b>Yield Listener.</b> Modifies any event pertaining to the breaking and harvesting of crops.
 * <br><b>Methods:</b><br>
 * onBlockBreak: Modifies a given BlockBreakEvent if block fits criteria.
 * <br>dropProperYield: Returns the proper item from the break
 * depending on the item used, enchants, etc.
 */
public class YieldListener implements Listener {

  private FarmingYieldTiers curYield = FarmingYieldTiers.TIER_FOUR;
  private static Material harvested = Material.BARRIER;
  private static BlockData broken;
  private final static MaterialYields[] validOps;
  private final static Map<Material, FarmingYieldTiers> validItems;

  static {
    // used an arrayList for the enumerators. Not sure if this is really faster than a series
    // of maps.
    validOps = new MaterialYields[9];
    validOps[0] = MaterialYields.PUMPKIN;
    validOps[1] = MaterialYields.PUMPKIN_STEM;
    validOps[2] = MaterialYields.MELON;
    validOps[3] = MaterialYields.MELON_STEM;
    validOps[4] = MaterialYields.WHEAT;
    validOps[5] = MaterialYields.POTATO;
    validOps[6] = MaterialYields.CARROT;
    validOps[7] = MaterialYields.COCOA;
    validOps[8] = MaterialYields.BEETROOT;

    // map of items to tiers.
    validItems = new TreeMap<>();
    validItems.put(Material.DIAMOND_HOE, FarmingYieldTiers.TIER_ONE);
    validItems.put(Material.GOLDEN_HOE, FarmingYieldTiers.TIER_TWO);
    validItems.put(Material.IRON_HOE, FarmingYieldTiers.TIER_TWO);
    validItems.put(Material.STONE_HOE, FarmingYieldTiers.TIER_THREE);
    validItems.put(Material.WOODEN_HOE, FarmingYieldTiers.TIER_THREE);
  }


  /**
   * onBlockBreak: If the given event happens to a block detailed in our maps, then proceed with
   * drop event conditions.
   *
   * @param ev event in which a block is being broken.
   */
  @EventHandler
  public void onBlockBreak(BlockBreakEvent ev) {

    if (invalidOperation(ev.getBlock().getType())) {
      return;
    }
    curYield = returnYield(ev.getPlayer());
    harvested = ev.getBlock().getType();
    broken = ev.getBlock().getBlockData();
  }

  /**
   * dropProperYield: drop the proper yield of a BlockDropItemEvent.
   *
   * @param ev the DropItemEvent in question.
   */
  @EventHandler
  public void dropProperYield(BlockDropItemEvent ev) {


    // checking for ageable. doesnt follow through if not aged.
    // believe the plugins spirit doesn't require someone to have a hoe to remove
    // seeds from the ground. if you disagree, let me know.
    // also checks for if the operation is invalid.
    if ((broken instanceof Ageable && isntAged((Ageable) broken)) || invalidOperation(harvested)) {
      harvested = Material.BARRIER;
      broken = null;
      return;
    }


    // if getting items
    ev.getItems().removeAll(ev.getItems());
    Location dropLoc = ev.getPlayer().getLocation().add(ev.getPlayer().getVelocity());
    MaterialYields yield = enumFinder(harvested);

    //we use a barrier as a filler placement.
    // if the crop has an appropriate seed output, then we drop the output.
    if (!yield.seedOutput.equals(Material.BARRIER)) {
      ev.getPlayer().getWorld().dropItem(dropLoc,
          new ItemStack(yield.seedOutput,
              properYield(ev.getPlayer().getInventory().getItemInMainHand(),
                  seedYield(curYield.seedMin, curYield))));
    }

    // we use a barrier as a filler placement.
    // if the crop has an appropriate output, then we drop it.
    if (!yield.cropOutput.equals(Material.BARRIER)) {
      ev.getPlayer().getWorld().dropItem(dropLoc,
          new ItemStack(yield.cropOutput,
              properYield(ev.getPlayer().getInventory()
                  .getItemInMainHand(), curYield.cropYield)));
    }

    harvested = Material.BARRIER;
    broken = null;
  }


  // returns the correct seed yield. random no. between 1 and the crop yield x 3.
  private int seedYield(int min, FarmingYieldTiers tier) {
    Random ran = new Random();
    return ran.ints(min, tier.cropYield * 3).limit(1).toArray()[0];
  }

  //returns the appropriate yield for an item.
  //if the item is enchanted, add +1 to the crop yield.
  private int properYield(ItemStack item, int flatYield) {
    if (!item.getEnchantments().containsKey(Enchantment.LUCK)) {
      return flatYield;
    }
    return item.getEnchantments().get(Enchantment.LUCK) + flatYield;
  }

  // finds the correct tier of an item in a players hand.
  private FarmingYieldTiers returnYield(Player p) throws IllegalArgumentException {
    if (p == null) {
      throw new IllegalArgumentException("Player is null!");
    }
    //get type of item in hand.
    Material curType = p.getInventory().getItemInMainHand().getType();
    // if the item isn't valid, recommend to use a hoe.
    if (!validItems.containsKey(curType)) {
      p.sendMessage(ChatColor.RED + "Try Using a Hoe.");
      return FarmingYieldTiers.TIER_FOUR;
    }
    return validItems.get(curType);
  }

  // check whether an operation is vald.
  // does the material being harvested appear in our enumerators?
  // if so, its valid. if not, its invalid.
  private boolean invalidOperation(Material harvested) {
    for (MaterialYields y : validOps) {
      if (y.type.equals(harvested)) {
        return false;
      }
    }
    return true;
  }

  // finds the correct enumerator for a given material.
  private MaterialYields enumFinder(Material harvested) {
    for (MaterialYields y : validOps) {
      if (y.type.equals(harvested)) {
        return y;
      }
    }
    throw new IllegalArgumentException("Not A Valid Enumerator!");
  }

  // if the crop isn't fully aged, return true.
  private boolean isntAged(Ageable b) {
    return b.getAge() != b.getMaximumAge();
  }
}


