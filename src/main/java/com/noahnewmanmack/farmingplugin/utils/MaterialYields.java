package com.noahnewmanmack.farmingplugin.utils;

import org.bukkit.Material;

/**
 * Enumerator with the Materials being harvested, their respective crop and seed yields.
 */
public enum MaterialYields {
  PUMPKIN(Material.PUMPKIN, Material.BARRIER, Material.PUMPKIN),
  PUMPKIN_STEM(Material.PUMPKIN_STEM, Material.PUMPKIN_SEEDS, Material.BARRIER),
  MELON_STEM(Material.MELON_STEM, Material.MELON_SEEDS, Material.BARRIER),
  MELON(Material.MELON, Material.MELON_SLICE, Material.BARRIER),
  WHEAT(Material.WHEAT, Material.WHEAT_SEEDS, Material.WHEAT),
  CARROT(Material.CARROTS, Material.CARROT, Material.BARRIER),
  POTATO(Material.POTATOES, Material.POTATO, Material.BARRIER),
  BEETROOT(Material.BEETROOTS, Material.BEETROOT_SEEDS, Material.BEETROOT),
  COCOA(Material.COCOA, Material.COCOA_BEANS, Material.BARRIER);


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
