package com.noahnewmanmack.farmingplugin.commands;



import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * When executed, either promotes or demotes a given player from the rank of non-trampler.
 * This means they either will or will no longer trample crops when jumping.
 * Requires Moderator to Execute.
 * <br><b>Methods:</b><br>
 * onCommand - as mentioned above.
 */
public class SetTramplerCommand implements CommandExecutor {



  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s,
      String[] strings) throws IllegalArgumentException {

    // if invalid command argument
    if (!(commandSender instanceof Player) || strings.length != 1) {
      commandSender.sendMessage("Invalid Arguments");
      return false;
    }

    Player player = (Player) commandSender;
    boolean hasPermissions = player.hasPermission("group.moderator");
    boolean targetHasPermissions = player.getServer().getPlayer(strings[0])
        .hasPermission("group.nontrampler");

    // if player has moderator.
    if (hasPermissions) {

      // if target already has permissions, demote.
      if (targetHasPermissions) {
        player.sendMessage("lp user " + strings[0] + " demote trampling");
        player.getServer().dispatchCommand(player.getServer().getConsoleSender(),
            "lp user " + strings[0] + " demote trampling");
        player.sendMessage("Demoting...");
      } else { // else, promote.
        player.sendMessage("lp user " + strings[0] + " promote trampling");
        player.getServer().dispatchCommand(player.getServer().getConsoleSender(),
            "lp user " + strings[0] + " promote trampling");
        player.sendMessage("Promoting...");
      }
      return true;
    } // if player doesn't have moderator, send message.
    player.sendMessage("You do not have permission!");
    return false;
  }

}
