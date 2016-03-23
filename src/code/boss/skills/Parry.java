package boss.skills;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;

import boss.BossPlugin;
import boss.mob.Mob;
import boss.mob.MobHandler;
import boss.utils.CustomEntityFirework;

public class Parry extends Skill {

	private final FireworkEffect.Builder builder = FireworkEffect.builder();
	private final FireworkEffect fe = builder.withColor(Color.NAVY).withFade(Color.FUCHSIA).with(Type.BURST).build();
	private final FireworkEffect ge = builder.withColor(Color.RED).withFade(Color.FUCHSIA).with(Type.BURST).build();
	
	public Parry(double chance) {
		super(chance);
	}

	public void run(LivingEntity le) {
		final Mob mob = MobHandler.getMob(le);
		
		try {
			CustomEntityFirework.spawn(le.getLocation(), fe);
		} catch (Exception e) {
		}
		mob.setParrying(true);
		this.message(30, le, ChatColor.AQUA + "parries!");
		Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable() {
			@Override
			public void run() {
				try {
					CustomEntityFirework.spawn(le.getLocation(), ge);
				}
				catch (Exception e)
				{
				}
				mob.setParrying(false);
			}
		},5*20);
		this.message(30, le, ChatColor.RED + "lets down his guard!");
	}

}
