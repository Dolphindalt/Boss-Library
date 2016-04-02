package boss.mob;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
						
					}
			} catch (IndexOutOfBoundsException ez) {
				sender.sendMessage("Invalid arguments!");
			}
		}
		return true;
	}

	
	
}
