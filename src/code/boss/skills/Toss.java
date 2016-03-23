package boss.skills;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import boss.utils.CustomEntityFirework;

public class Toss extends Skill {

	private final FireworkEffect.Builder builder = FireworkEffect.builder();
	private final FireworkEffect fe = builder.withColor(Color.BLUE).withFade(Color.BLACK).with(Type.BALL_LARGE).build();
	private int radius;
	private int power;
	
	public Toss(double chance, int radius, int power) {
		super(chance);
		this.radius = radius;
		this.power = power;
	}

	public void run(LivingEntity le) {
		List<Player> list = this.getPlayers(radius, le);
		CustomEntityFirework.spawn(le.getLocation(), fe);
		if(!list.isEmpty())
		{
			for(Player p : list)
			{
                float hForce = 20 / 25.0F;
                float vForce = 15 / 20.0F;
	            Vector v = p.getLocation().toVector().subtract(le.getLocation().toVector());
                v.setY(5);
                v.normalize();
                v.multiply(hForce*power);
                v.setY(vForce*power);
	            p.setVelocity(v);
			}
		}
	}

	
	
}
