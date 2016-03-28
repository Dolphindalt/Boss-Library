package boss.skills;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import boss.utils.CustomEntityFirework;

public class Shuffle extends Skill {

	private int radius;
	private final FireworkEffect.Builder builder = FireworkEffect.builder();
	private final FireworkEffect fe = builder.withColor(Color.PURPLE).withFade(Color.BLUE).with(Type.BURST).build();
	
	public Shuffle(double chance, int radius) {
		super(chance);
		this.radius = radius;
	}
	
	public void run(LivingEntity caster) {
		
		List<Player> list = this.getPlayers(radius, caster);
		if(list.isEmpty())
			return;
		
		List<Location> locs = new ArrayList<Location>(list.size());
		for(Player p : list) {
			locs.add(p.getLocation());
		}
		Random rand = new Random();
		Iterator<Player> itr = list.iterator();
		while(itr.hasNext())
		{
			Player temp = itr.next();
			try {
				CustomEntityFirework.spawn(temp.getLocation(), fe);
			}
			catch(Exception e) {
			}
			int r = rand.nextInt(locs.size());
			temp.teleport(locs.get(r));
			temp.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,10*20,1));
			locs.remove(r);
		}
	}
	
}
