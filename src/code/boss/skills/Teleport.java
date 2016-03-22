package boss.skills;

import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import boss.utils.CustomEntityFirework;

public class Teleport extends Skill {

	private int radius;
	
	private final FireworkEffect.Builder builder = FireworkEffect.builder();
	private final FireworkEffect fe = builder.withColor(Color.PURPLE).flicker(true).withFade(Color.FUCHSIA).with(Type.BURST).build();
	private final FireworkEffect ge = builder.withColor(Color.PURPLE).flicker(true).with(Type.BURST).build();
	
	public Teleport(double chance, int radius) {
		super(chance);
		this.radius = radius;
	}

	@Override
	public void run(LivingEntity le) {
		
		List<Player> list = this.getPlayers(radius, le);
		
		if (!list.isEmpty()) {
			Random ran = new Random();
			Player p = list.get(ran.nextInt(list.size()));
			CustomEntityFirework.spawn(p.getLocation(), fe);
			CustomEntityFirework.spawn(le.getLocation(), ge);
			le.teleport(p.getLocation());
		}
		
	}

}
