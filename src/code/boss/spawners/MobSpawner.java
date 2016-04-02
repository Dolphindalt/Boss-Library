package boss.spawners;

import org.bukkit.Location;
import boss.mob.Mob;

public class MobSpawner {

	private String name;
	
	private Mob mob;
	private int intervalConstant;
	private int interval;
	private int maxMobs;
	private int livingMobs;
	
	private Location location;
	
	public MobSpawner(String name, Mob mob, Location location, int interval, int maxMobs) {
		this.name = name;
		this.mob = mob;
		this.intervalConstant= interval;
		this.interval = 0;
		this.livingMobs = 0;
		this.maxMobs = maxMobs;
		this.location = location;
	}
	
	public void tickSpawner() {
		
		if (interval == 0) {
			spawnMob();
		}
		
		interval--;
	}
	
	private void spawnMob() {
		if (livingMobs <= maxMobs) {
			location.getChunk().load(true);
			mob.spawn(location, this);
			livingMobs++;
			location.getChunk().load(false);
			interval = intervalConstant;
		}
	}

	public String getName() {
		return name;
	}
	
}
