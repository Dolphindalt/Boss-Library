package boss.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import boss.mob.Mob;
import boss.mob.MobHandler;

/*
 *  Credit to Frodenkvist for most of this class.
 */

public class EntityDamageByEntityListener implements Listener {

	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof TNTPrimed) return;
		
		Entity damaged = e.getEntity();
		
		if (e.getDamager() instanceof Arrow) {
			if (damaged == null) return;
			if (!(damaged instanceof LivingEntity)) return;
			
			Mob mob = MobHandler.getMob((LivingEntity)damaged);
			
			if (mob == null) return;
			
			if(mob.isArrowImmune()) {
				e.getDamager().remove();
				e.setCancelled(true);
				return;
			}
			
			if (mob.hasSkills()) {
				//TODO: EXECUTE A SKILL
			}
		}
		
		else if (e.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball)e.getDamager();
			
			LivingEntity shooter = (LivingEntity)snowball.getShooter();
			
			if(shooter == null) return;
			Mob mDamager = MobHandler.getMob(shooter);
			
			if(mDamager == null) return;
			e.setDamage(mDamager.getDamage());
			
			if(damaged == null) return;
			if(!(damaged instanceof LivingEntity)) return;
			
			Mob mob = MobHandler.getMob((LivingEntity)damaged);
			if(mob == null) return;
			
			if(mob.hasSkills()) {

			}
		}
		
		else if (e.getDamager() instanceof Fireball) {
			Fireball fireball = (Fireball)e.getDamager();
			
			LivingEntity shooter = (LivingEntity)fireball.getShooter();
			
			if(shooter == null) return;
			Mob mDamager = MobHandler.getMob(shooter);
			
			if(mDamager == null) return;
			e.setDamage(mDamager.getDamage());
			
			if(damaged == null) return;
			if(!(damaged instanceof LivingEntity)) return;
			
			Mob mob = MobHandler.getMob((LivingEntity)damaged);
			if(mob == null) return;
			
			if(mob.hasSkills()) {

			}
		}
		
		else if (e.getDamager() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity)e.getDamager();
			
			if (damaged == null) return;
			if (!(damaged instanceof LivingEntity)) return;
			
			Mob mob = MobHandler.getMob((LivingEntity) damaged);
			
			if (mob != null) {
				if (e.getDamager() instanceof Player) {
				//TODO: Add parrying!
				}
			}
			
			if (mob.hasSkills()) {
				
			}
			
			Mob mDamager = MobHandler.getMob(damager);
			if (mDamager == null) return;
			e.setDamage(mDamager.getDamage()/15);
		}
		
	}
	
}
