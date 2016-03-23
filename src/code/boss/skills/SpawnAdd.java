package boss.skills;

import org.bukkit.entity.LivingEntity;

import boss.mob.Mob;
import boss.mob.MobHandler;

public class SpawnAdd extends Skill implements UsableOnce {

	private String configName;
	private int amount;
	private boolean used;
	
	public SpawnAdd(double chance, String configName, int amount) {
		super(chance);
		this.configName = configName;
		this.amount = amount;
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

	
	
}
