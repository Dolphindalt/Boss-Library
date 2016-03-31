package boss.skills;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import boss.mob.Mob;
import boss.mob.MobHandler;
import boss.utils.Namer;

/*
 *  This class and other skill classes were heavily contributed to by ThaH3lper.
 */

public abstract class Skill {

	protected double chance;
	
	public Skill(double chance) {
		this.chance = chance;
	}
	
	public abstract void run(LivingEntity le);
	
	protected List<Player> getPlayers(int radius, LivingEntity mob) {
		List<Player> list = new ArrayList<Player>();
		List<Entity> near = mob.getNearbyEntities(radius, radius, radius);
		for(Entity check : near) {
			if(check instanceof Player) {
				if(((Player)check).getGameMode().equals(GameMode.CREATIVE))
					continue;
				list.add((Player) check);
			}
		}
		return list;
	}
	
	protected void message(int radius, LivingEntity le, String message)
	{
		List<Player> list = new ArrayList<Player>();
		List<Entity> near = le.getNearbyEntities(radius, radius, radius);
		for(Entity check : near)
		{
			if(check instanceof Player)
			{
				list.add((Player) check);
			}
		}
		Mob mob = MobHandler.getMob(le);
		Iterator<Player> itr = list.iterator();
		while(itr.hasNext())
		{
			Player temp = itr.next();
			temp.sendMessage(Namer.addChatColor("<" + mob.getDisplayName() + ChatColor.RESET + "> " + message));
		}
	}
	
	public double getChance() {
		return chance;
	}
	
}
