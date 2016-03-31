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
	private int maxMobs;
	
	private Location location;
	
	public MobSpawner(Mob mob, Location location) {
		this.mob = mob;
		this.spawnedMobs = new ArrayList<>();
		this.location = location;
	}
	
	public void spawnMob() {
		if (spawnedMobs.size() < maxMobs) {
			location.getChunk().load(true);
			mob.spawn(location, spawnedMobs);
			location.getChunk().load(false);
		}
	}
	
	public void checkForDeadMobs() {
		Iterator<Entity> itr = spawnedMobs.iterator();
		
		while(itr.hasNext()) {
			Entity check = itr.next();
			if (check.isDead()) {
				spawnedMobs.remove(check);
			}
		}
	}
	
}
