package boss.skills;

import org.bukkit.entity.LivingEntity;

import boss.mob.Mob;
import boss.mob.MobHandler;

public class SpawnAdd extends Skill implements UsableOnce, HealthDepend {

	private String configName;
	private int amount;
	private boolean used;
	private int healthNeeded;
	
	public SpawnAdd(double chance, String configName, int amount, int healthNeeded) {
		super(chance);
		this.configName = configName;
		this.amount = amount;
		this.healthNeeded = healthNeeded;
	}

	public void run(LivingEntity le) {
		Mob add = MobHandler.getMob(configName);
		if (add == null) return;
		
		for (int i = 0; i < amount; i++) {
			add.spawn(le.getLocation());
		}
		
		return;
	}

	public boolean hasUsed() {
		return used;
	}

	public void setUsed(boolean value) {
		used = value;
	}

	public int getHealthNeedToCast() {
		return healthNeeded;
	}

	
	
}
