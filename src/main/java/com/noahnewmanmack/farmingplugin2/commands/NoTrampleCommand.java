package com.noahnewmanmack.farmingplugin2.commands;

import com.noahnewmanmack.farmingplugin2.files.NoTrampleFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * <B>NoTrampleCommand</B>br>
 * <b>Method</b> onCommand: when moderator inputs 'notrample [name]', writes to permissions file.
 */
public class NoTrampleCommand implements CommandExecutor {

  /**
   * Command to edit permissions.
   * @param sender sender of the command.
   * @param command command in question; must be notrample.
   * @param label n/a; inheritance.
   * @param args single argument of username.
   * @return true if executed, false if not.
   */
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equals("notrample") && args.length == 1) {
      // if command is fired, add or remove user from file.
      NoTrampleFile.toggleUser(args[0]);
      return true;
    }
    return false;
  }
}
