package boss.skills;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import boss.BossPlugin;
import boss.utils.CustomEntityFirework;

public class Grab extends Skill {

	private int radius;
	private int damage;
	private final FireworkEffect.Builder builder = FireworkEffect.builder();
	private final FireworkEffect fe = builder.withColor(Color.AQUA).with(Type.BURST).build();
	
	public Grab(double chance) {
		super(chance);

	}

	public void run(LivingEntity le) {
		if (le.isInsideVehicle()) return;
		List<Player> players = this.getPlayers(radius, le);
		if (players.isEmpty()) return;
		
		final Player target = players.get(new Random().nextInt(players.size()));
		le.teleport(target);
		target.setPassenger(le);
		
		try {
			CustomEntityFirework.spawn(target.getLocation(), fe);
			CustomEntityFirework.spawn(target.getLocation(), fe);
		} catch (Exception e) {
		}
		
		this.message(radius, le, ChatColor.RED + "grabs onto " + ChatColor.AQUA + target.getDisplayName());
		target.damage(damage);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
		{
			@Override
			public void run()
			{
				target.damage(damage);
				Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
				{
					@Override
					public void run()
					{
						target.damage(damage);
						Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
						{
							@Override
							public void run()
							{
								target.damage(damage);
								Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
								{
									@Override
									public void run()
									{
										target.eject();
									}
								}, 1*20);
							}
						}, 1*20);
					}
				}, 1*20);
			}
		}, 1*20);
		
	}

	
	
}
