package boss.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/*
 *  This class and other skill classes were heavily contributed to by ThaH3lper.
 */

public abstract class Skill {

	protected double chance;
	
	public Skill(double chance) {
		this.chance = chance;
	}
	
	public abstract void run(LivingEntity le);
	
	protected List<Player> getPlayers(int radius, LivingEntity mob)
	{
		List<Player> list = new ArrayList<Player>();
		List<Entity> near = mob.getNearbyEntities(radius, radius, radius);
		for(Entity check : near)
		{
			if(check instanceof Player)
			{
				if(((Player)check).getGameMode().equals(GameMode.CREATIVE))
					continue;
				list.add((Player) check);
			}
		}
		return list;
	}
	
	public double getChance() {
		return chance;
	}
	
}
