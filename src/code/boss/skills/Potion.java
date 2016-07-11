package boss.skills;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import boss.utils.CustomEntityFirework;

/*
 *  Frodenkvist created tihs class completely.
 */

public class Potion extends Skill {

	private int radius;
	private PotionEffect pe;
	private final FireworkEffect.Builder builder = FireworkEffect.builder();
	
	public Potion(double chance, int radius, PotionEffect pe)
	{
		super(chance);
		this.radius = radius;
		this.pe = pe;
	}
	
	public void run(LivingEntity entity) {
		List<Player> list = this.getPlayers(radius, entity);
		if(!list.isEmpty())
		{
			CustomEntityFirework.spawn(entity.getLocation(), builder.withColor(getPotionColor(pe)).with(Type.BALL_LARGE).build());
			for(Player p : list)
			{
				p.addPotionEffect(pe);
				CustomEntityFirework.spawn(p.getLocation(), getPotionFirework(pe));
			}
		}
	}
	
	private FireworkEffect getPotionFirework(PotionEffect pe) {
		if(pe.getType() == PotionEffectType.POISON)
		{
			FireworkEffect potion = builder.withColor(Color.GREEN).withColor(Color.LIME).with(Type.BURST).withFlicker().withTrail().build();
			return potion;
		}
		else if(pe.getType() == PotionEffectType.WITHER)
		{
			FireworkEffect potion = builder.withColor(Color.GRAY).withColor(Color.BLACK).with(Type.CREEPER).withFade(Color.BLACK).build();
			return potion;
		}
		else if(pe.getType() == PotionEffectType.BLINDNESS)
		{
			FireworkEffect potion = builder.withColor(Color.BLACK).withColor(Color.BLACK).with(Type.STAR).withFade(Color.BLACK).flicker(true).trail(true).build();
			return potion;
		}
		else if(pe.getType() == PotionEffectType.CONFUSION)
		{
			FireworkEffect potion = builder.withColor(Color.GREEN).withColor(Color.BLACK).with(Type.CREEPER).withFade(Color.LIME).build();
			return potion;
		}
		else if(pe.getType() == PotionEffectType.WEAKNESS)
		{
			FireworkEffect potion = builder.withColor(Color.BLACK).withColor(Color.BLUE).with(Type.BURST).flicker(true).trail(true).withFade(Color.NAVY).build();
			return potion;
		}
		else if(pe.getType() == PotionEffectType.HARM)
		{
			FireworkEffect potion = builder.withColor(Color.RED).withColor(Color.BLUE).with(Type.STAR).withFade(Color.LIME).build();
			return potion;
		}
		else
		{
			FireworkEffect potion = builder.withColor(Color.GRAY).with(Type.BURST).build();
			return potion;
		}
	}
	
	private Color getPotionColor(PotionEffect pe) {
		if(pe.getType() == PotionEffectType.POISON)
		{
			Color potion = Color.GREEN;
			return potion;
		}
		else if(pe.getType() == PotionEffectType.WITHER)
		{
			Color potion = Color.BLACK;
			return potion;
		}
		else if(pe.getType() == PotionEffectType.BLINDNESS)
		{
			Color potion = Color.GRAY;
			return potion;
		}
		else if(pe.getType() == PotionEffectType.CONFUSION)
		{
			Color potion = Color.LIME;
			return potion;
		}
		else if(pe.getType() == PotionEffectType.WEAKNESS)
		{
			Color potion = Color.NAVY;
			return potion;
		}
		else if(pe.getType() == PotionEffectType.HARM)
		{
			Color potion = Color.RED;
			return potion;
		}
		else
		{
			Color potion = Color.GRAY;
			return potion;
		}
	}
	
}
