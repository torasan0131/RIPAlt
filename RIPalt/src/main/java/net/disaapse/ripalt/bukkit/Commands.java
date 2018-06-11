package net.disaapse.ripalt.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class Commands implements CommandExecutor, TabCompleter {

	private final BukkitRIPalt plugin;

	public Commands(BukkitRIPalt plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmds, String commandLabel, String[] args) {
		if (!this.plugin.isEnabled()) {
			sender.sendMessage(ChatColor.RED + "---------- [RIPalt v" + this.plugin.getDescription().getVersion() + " : By tora0409] ----------");
			sender.sendMessage(ChatColor.RED + "Plugin : Disable");
			if (!this.plugin.getServer().getOnlineMode()) {
				sender.sendMessage(ChatColor.RED + "Reason : This plugin only run in online mode.");
			}
			return true;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (sender.hasPermission(BukkitRIPalt.PERMISSION_RELOAD)) {
				this.plugin.onReload();
				sender.sendMessage(ChatColor.GREEN + "[RIPalt] Reloaded");
			} else {
				sender.sendMessage(ChatColor.RED + "[RIPalt] You don't have permission !");
			}
			return true;
		} else {
			sender.sendMessage(ChatColor.GREEN + "---------- [RIPalt v" + this.plugin.getDescription().getVersion() + " : By tora0409] ----------");
			sender.sendMessage(ChatColor.GREEN + "/ripalt help : Help plugin");
			if (sender.hasPermission(BukkitRIPalt.PERMISSION_RELOAD)) {
				sender.sendMessage(ChatColor.GREEN + "/ripalt reload : Reload plugin");
			}
			return true;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> suggests = new ArrayList<String>();
		if((alias.equalsIgnoreCase("ripalt") || alias.equalsIgnoreCase("ripalt")) && args.length <= 1) {
			suggests.add("help");
			if (sender.hasPermission(BukkitRIPalt.PERMISSION_RELOAD)) {
				suggests.add("reload");
			}
		}
		return suggests;
	}
}
