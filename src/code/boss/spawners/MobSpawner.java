package boss.spawners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import boss.mob.Mob;

public class MobSpawner {

	private Mob mob;
	private List<Entity> spawnedMobs;
	private int interval;
	private int maxMobs;
	
	private Location location;
	
	public MobSpawner(Mob mob, Location location, int interval, int maxMobs) {
		this.mob = mob;
		this.spawnedMobs = new ArrayList<>();
		this.interval = interval;
		this.maxMobs = maxMobs;
		this.location = location;
	}
	
	public void tickSpawner() {
		checkForDeadMobs();
		
		if (interval == 0) {
			spawnMob();
		}
		
		interval--;
	}
	
	private void spawnMob() {
		if (spawnedMobs.size() < maxMobs) {
			location.getChunk().load(true);
			mob.spawn(location, spawnedMobs);
			location.getChunk().load(false);
		}
	}
	
	private void checkForDeadMobs() {
		Iterator<Entity> itr = spawnedMobs.iterator();
		
		while(itr.hasNext()) {
			Entity check = itr.next();
			if (check.isDead()) {
				spawnedMobs.remove(check);
			}
		}
		
	}
	
}
