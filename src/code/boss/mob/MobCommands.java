package boss.mob;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import boss.spawners.MobSpawner;
import boss.spawners.MobSpawnerHandler;
import boss.utils.Parse;

public class MobCommands implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("boss") && sender.hasPermission("boss.admin")) {
			try {
				if (args[0].equalsIgnoreCase("mob")) {
					for (Mob m : MobHandler.mobs) {
						if (m.getConfigName().equals(args[1])) {
							m.spawn(((Player) sender).getLocation());
							break;
						}
					}
					return true;
				} else 
				if (args[0].equalsIgnoreCase("mobs")) {
					String list = "";
					for (String s : MobHandler.configNames) {
						list = list + s + ", ";
					}
					sender.sendMessage(list);
					return true;
				} else
					if (args[0].equalsIgnoreCase("spawner")) {
						if (args[1].equalsIgnoreCase("create")) {
							if (args.length != 6) {
								sender.sendMessage(ChatColor.RED + "/spawner create <name> <mobname> <interval> <maxmobs>");
								return true;
							}
							Mob m;
							if ((m = MobHandler.getMob(args[3])) == null) {
								sender.sendMessage(ChatColor.RED + "Mob is null!");
								return true;
							}
							int interval;
							if ((interval = Parse.parseInteger(args[4])) == -1) {
								sender.sendMessage(ChatColor.RED + "Interval time is null");
								return true;
							}
							int maxMobs;
							if ((maxMobs = Parse.parseInteger(args[5])) == -1) {
								sender.sendMessage(ChatColor.RED + "Interval time is null");
								return true;
							}
							MobSpawnerHandler.addSpawner(args[2], m, ((Player) sender).getLocation(), interval, maxMobs);
							return true;
						} else
						if (args[1].equalsIgnoreCase("remove")) {
							if (args.length != 3) {
								sender.sendMessage(ChatColor.RED + "/spawner remove <spawnername>");
								return true;
							}
							if (MobSpawnerHandler.getSpawner(args[2]) == null) {
								sender.sendMessage(ChatColor.RED + "Spawner name is invalid!");
								return true;
							}
							MobSpawnerHandler.removeSpawner(args[2]);
							return true;
						} else
						if (args[1].equalsIgnoreCase("spawners")) {
							String names = "";
							Iterator<MobSpawner> itr = MobSpawnerHandler.spawners.iterator();
							while(itr.hasNext()) {
								MobSpawner m = itr.next();
								names = names + m.getName() + ",";
							}
							sender.sendMessage(ChatColor.GREEN + names);
							return true;
						}
					}
			} catch (IndexOutOfBoundsException ez) {
				sender.sendMessage("Invalid arguments!");
			}
		}
		return true;
	}

	
	
}
