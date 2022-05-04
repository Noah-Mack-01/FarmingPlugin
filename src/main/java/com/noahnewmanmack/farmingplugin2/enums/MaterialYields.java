package com.noahnewmanmack.farmingplugin2.enums;

import org.bukkit.Material;

/**
 * Enumerator with the Materials being harvested, their respective crop and seed yields.
 */
public enum MaterialYields {

  PUMPKIN(Material.PUMPKIN, null, Material.PUMPKIN),
  PUMPKIN_STEM(Material.PUMPKIN_STEM, Material.PUMPKIN_SEEDS, null),
  MELON_STEM(Material.MELON_STEM, Material.MELON_SEEDS, null),
  MELON(Material.MELON, Material.MELON_SLICE, null),
  WHEAT(Material.WHEAT, Material.WHEAT_SEEDS, Material.WHEAT),
  CARROT(Material.CARROTS, Material.CARROT, null),
  POTATO(Material.POTATOES, Material.POTATO, null),
  BEETROOT(Material.BEETROOTS, Material.BEETROOT_SEEDS, Material.BEETROOT),
  COCOA(Material.COCOA, Material.COCOA_BEANS, null);


  public final Material type;
  public final Material seedOutput;
  public final Material cropOutput;

  //constructor
  private MaterialYields(Material type, Material seedOutput, Material cropOutput) {
    this.cropOutput = cropOutput;
    this.type = type;
    this.seedOutput = seedOutput;
  }


}