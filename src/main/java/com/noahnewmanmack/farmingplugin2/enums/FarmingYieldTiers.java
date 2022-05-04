package com.noahnewmanmack.farmingplugin2.enums;

import org.bukkit.Material;

/**
 * <B>Farming Yield Tiers</B><br>
 * An enumerator used to contain a map of Materials with valid yields.<br>
 * A given tier will have a list of materials which produce two given yields, both a minimum<br>
 * yield of seeds and a yield of crop.
 */
public enum FarmingYieldTiers {

  BROKE_SEED(Material.BEDROCK, 1,0),
  TIER_THREE_WOOD_HOE( Material.WOODEN_HOE, 1, 1),
  TIER_THREE_STONE_HOE( Material.STONE_HOE, 1, 1),
  TIER_TWO_IRON_HOE(Material.IRON_HOE, 1, 2),
  TIER_TWO_GOLD_HOE(Material.GOLDEN_HOE, 1, 2),
  TIER_ONE_DIAMOND_HOE(Material.DIAMOND_HOE, 1, 3),
  TIER_ONE_NETHERITE_HOE(Material.NETHERITE_HOE, 1, 3);

  public final int cropYield, seedMin;
  public final Material mat;

  /**
   * Constructor for a Hoe Yield.
   * @param seedsMinimum the minimum number of seeds produced through yield.
   * @param cropNum the number of crops produced through yield.
   */
  FarmingYieldTiers(Material mat, int seedsMinimum, int cropNum) {
    this.mat = mat;
    this.seedMin = seedsMinimum;
    this.cropYield = cropNum;
    }

  }





