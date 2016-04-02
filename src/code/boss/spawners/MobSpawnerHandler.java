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
import boss.mob.Mob;
import boss.mob.MobHandler;
import boss.utils.Parse;

public class MobSpawnerHandler implements Runnable {

	public static List<MobSpawner> spawners = new ArrayList<>();
	
	public static FileConfiguration fc;
	
	public static void loadSpawners(FileConfiguration fc) {
		setFc(fc);
		ConfigurationSection cs = fc.getConfigurationSection("Spawners");
		for (String s : cs.getKeys(false)) {
			if (MobHandler.getMob(cs.getString(".MobName")) == null) {
				BossPlugin.logger.warning("MobName failed for " + s);
				continue;
			}
			
			String sworld;
			World world;
			if ((sworld = cs.getString(".World")) == null) {
				BossPlugin.logger.warning("World failed for " + s);
				continue;
			}
			if ((world = Bukkit.getWorld(sworld)) == null) {
				BossPlugin.logger.warning("Invalid world for " + s);
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
			
			MobSpawner spawner = new MobSpawner(s, MobHandler.getMob(cs.getString(".MobName")), l, interval, maxMobs);
			
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
	
	public void addSpawner(String name, Mob mob, Location location, int interval, int maxMobs) {
		for (MobSpawner m : spawners) {
			if (m.getName().equals(name)) {
				return;
			}
		}
		ConfigurationSection cs = fc.getConfigurationSection("Spawners");
		MobSpawner spawner = new MobSpawner(name, mob, location, interval, maxMobs);
		spawners.add(spawner);
		
		cs.createSection(name);
		cs.createSection(name + ".MobName");
		cs.createSection(name + ".World");
		cs.createSection(name + ".Location");
		cs.createSection(name + ".SpawnInterval");
		cs.createSection(name + ".MaxLivingMobs");
		cs.set(name + ".MobName", mob.getConfigName());
		cs.set(name + ".World", location.getWorld());
		cs.set(name + ".Location", location.getX() + "," + location.getY() + "," + location.getZ());
		cs.set(name + ".SpawnInterval", interval);
		cs.set(name + ".MaxLivingMobs", maxMobs);
	}
	
	public void removeSpawner(String name) {
		MobSpawner spawner;
		if ((spawner = getSpawner(name)) != null) {
			if (spawners.contains(spawner)) {
				spawners.remove(spawner);
				ConfigurationSection cs = fc.getConfigurationSection("Spawners");
				cs.set(name, null);
			}
		}
	}
	
	public MobSpawner getSpawner(String name) {
		for (MobSpawner s : spawners) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}

	public static FileConfiguration getFc() {
		return fc;
	}

	public static void setFc(FileConfiguration fc) {
		MobSpawnerHandler.fc = fc;
	}
	
}
