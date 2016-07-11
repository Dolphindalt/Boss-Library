package boss.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import boss.mob.Mob;
import boss.mob.MobHandler;

public class EntityDeathListener implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		LivingEntity le = e.getEntity();
		
		try {
			
		Mob mob = MobHandler.getMob(le);
		if (mob == null) return;
		e.getDrops().clear();
		
		List<ItemStack> items = mob.createDropLoot();
		if (!(items == null) && !items.isEmpty()) {
			Entity ent = e.getEntity();
			for (ItemStack is : items) {
				ent.getWorld().dropItemNaturally(ent.getLocation(), is);
			}
		}
		
		if (mob.hasDeathBroadcast()) {
			Player killer = le.getKiller();
			if (killer == null) return;
			Bukkit.broadcastMessage(killer.getDisplayName() + ChatColor.YELLOW + " has slain " + ChatColor.DARK_PURPLE + mob.getConfigName());
		}
		
		MobHandler.livingMobs.remove(mob.getEntity());
		
		} catch (NullPointerException ex) {
			return;
		}
	}
	
}
