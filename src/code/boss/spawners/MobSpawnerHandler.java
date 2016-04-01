package boss.spawners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import boss.BossPlugin;
import boss.mob.MobHandler;
import boss.utils.Parse;

public class MobSpawnerHandler implements Runnable {

	public static List<MobSpawner> spawners = new ArrayList<>();
	
	public static void loadSpawners(FileConfiguration fc) {
		ConfigurationSection cs = fc.getConfigurationSection("Spawners");
		for (String s : cs.getKeys(false)) {
			if (MobHandler.getMob(cs.getString(".MobName")) == null) {
				BossPlugin.logger.warning("MobName failed for " + s);
				continue;
			}
			String sworld;
			World world;
			if ((sworld = cs.getString(".World")) != null) {
				if ((world = Bukkit.getWorld(sworld)) != null) {
					
				} else {
					BossPlugin.logger.warning("Ibalid world for " + s);
					continue;
				}
			} else {
				BossPlugin.logger.warning("World failed for " + s);
				continue;
			}
			
			String location;
			boolean failedLocation = false;
			if ((location = cs.getString(s + ".Location")) != null) {
				String[] cords = location.split(",");
				for (String c : cords) {
					if (Parse.parseInteger(c) == null || Parse.parseInteger(c) == -1) {
						failedLocation = true;
					}
				}
				if (failedLocation == true) {
					BossPlugin.logger.warning("Location failed for " + s);
					continue;
				}
			}
			
			int interval;
			if ((interval = cs.getInt(s + ".SpawnInterval")) == -1) {
				BossPlugin.logger.warning("SpawnInterval failed for " + s);
				continue;
			}
			
			int maxMobs;
			if ((maxMobs = cs.getInt(s + ".MaxLivingMobs")) == -1) {
				BossPlugin.logger.warning("MaxMobs failed for " + s);
				continue;
			}
			
			String[] split = location.split(",");
			Location l = new Location(world, Parse.parseInteger(split[0]), Parse.parseInteger(split[1]), Parse.parseInteger(split[2]));
			
			MobSpawner spawner = new MobSpawner(MobHandler.getMob(cs.getString(".MobName")), l, interval, maxMobs);
			
			spawners.add(spawner);
		}
	}

	public void run() {
		Iterator<MobSpawner> itr = spawners.iterator();
		
		while(itr.hasNext()) {
			MobSpawner spawner = itr.next();
			spawner.tickSpawner();
		}
	}
	
}
