package com.noahnewmanmack.farmingplugin.utils;

import java.util.Map;
import org.bukkit.Material;

/**
 * <B>Farming Yield Tiers</B><br>
 * An enumerator used to contain a map of Materials with valid yields.<br>
 * A given tier will have a list of materials which produce two given yields, both a minimum<br>
 * yield of seeds and a yield of crop.
 */
public enum FarmingYieldTiers {

  TIER_FOUR(0,0),
  TIER_THREE( 1, 1),
  TIER_TWO( 1, 2),
  TIER_ONE(1, 3);

  public final int cropYield, seedMin;

  /**
   * Constructor for a Hoe Yield.
   * @param seedsMinimum the minimum number of seeds produced through yield.
   * @param cropNum the number of crops produced through yield.
   */
  FarmingYieldTiers( int seedsMinimum, int cropNum) {
    this.seedMin = seedsMinimum;
    this.cropYield = cropNum;
  }



}
