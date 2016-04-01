package boss.mob;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import boss.items.ItemObject;
import boss.skills.Grab;
import boss.skills.HealthDepend;
import boss.skills.LightningStorm;
import boss.skills.Parry;
import boss.skills.Potion;
import boss.skills.Shuffle;
import boss.skills.Skill;
import boss.skills.SpawnAdd;
import boss.skills.Teleport;
import boss.skills.Toss;
import boss.skills.UsableOnce;
import boss.spawners.MobSpawner;
import boss.utils.Namer;
import boss.utils.Parse;

/*
 *  Most of this class was created by ThaH3lper, check him out on the bukkit forums.ö
 */

public class Mob {

	private String configName;
	private String type;
	private String displayName;
	private int health;
	private int damage;
	private boolean despawn;
	private boolean arrowImmune;
	
	private boolean deathBroadcast;
	private boolean hasSkills;
	private boolean parrying;
	
	private List<ItemObject> items;
	private List<Double> chances;
	
	private List<Skill> skills;
	
	private Entity entity = null;
	
	private MobSpawner spawner = null;
	
	public Mob(String configName, String type, String displayName, int health, int damage,
			boolean despawn, boolean arrowImmune, boolean deathBroadcast, List<ItemObject> items,
			List<Double> chances, List<String> skills) {
		this.configName = configName;
		this.type = type;
		this.displayName = displayName;
		this.health = health;
		this.damage = damage;
		this.despawn = despawn;
		this.arrowImmune = arrowImmune;
		this.deathBroadcast = deathBroadcast;
		this.items = items;
		this.chances = chances;
		this.hasSkills = false;
		this.parrying = false;
		this.skills = new ArrayList<>();
		//Loading skills
		Iterator<String> itr = skills.iterator();
		while(itr.hasNext()) {
			String s = itr.next();
			String[] split = s.split(",");
			if (split[0].equalsIgnoreCase("parry")) {
				if (split.length != 2) continue;
				double chance = Parse.parseDouble(split[1]);
				this.skills.add(new Parry(chance));
			} else if (split[0].equalsIgnoreCase("shuffle")) {
				if (split.length != 3) continue;
				this.skills.add(new Shuffle(Parse.parseDouble(split[1]), Parse.parseInteger(split[2])));
			} else if (split[0].equalsIgnoreCase("spawnadd")) {
				if (split.length != 5) continue;
				this.skills.add(new SpawnAdd(Parse.parseDouble(split[1]), split[2], Parse.parseInteger(split[3]), Parse.parseInteger(split[4])));
			} else if (split[0].equalsIgnoreCase("toss")) {
				if (split.length != 4) continue;
				this.skills.add(new Toss(Parse.parseDouble(split[1]), Parse.parseInteger(split[2]), Parse.parseInteger(split[3])));
			} else if (split[0].equalsIgnoreCase("lightningstorm")) {
				if (split.length != 3) continue;
				this.skills.add(new LightningStorm(Parse.parseDouble(split[1]), Parse.parseDouble(split[2])));
			} else if (split[0].equalsIgnoreCase("teleport")) {
				if (split.length != 3) continue;
				this.skills.add(new Teleport(Parse.parseDouble(split[1]), Parse.parseInteger(split[2])));
			} else if (split[0].equalsIgnoreCase("potion")) {
				if(split.length != 3) continue;
				String[] data = split[1].split(":");
				if(data.length != 4) continue;
				int radius = Integer.valueOf(data[3]);
				double chance = Double.valueOf(split[2]);
				PotionEffect potion = new PotionEffect(PotionEffectType.getByName(data[0]), Parse.parseInteger(data[1]) * 20, Parse.parseInteger(data[2]) - 1);
				this.skills.add(new Potion(chance, radius, potion));
			} else if (split[0].equalsIgnoreCase("grab")) {
				if (split.length != 2) continue;
				this.skills.add(new Grab(Parse.parseDouble(split[1])));
			}
		}
		if (!this.skills.isEmpty()) hasSkills = true;
	}
	
	public void spawn(Location l) {
		LivingEntity le = this.spawnMob(l, this.type);
		le.setCustomNameVisible(true);
		le.setCustomName(Namer.addChatColor(displayName));
		le.setMaxHealth(health);
		le.setHealth(health);
		entity = le;
		MobHandler.livingMobs.add(entity);
	}
	
	public void spawn(Location l, MobSpawner spawner) {
		LivingEntity le = this.spawnMob(l, this.type);
		le.setCustomNameVisible(true);
		le.setCustomName(Namer.addChatColor(displayName));
		le.setMaxHealth(health);
		le.setHealth(health);
		entity = le;
		MobHandler.livingMobs.add(entity);
		this.spawner = spawner;
	}

	public void execute() {
		Iterator<Skill> itr = skills.iterator();
		
		while(itr.hasNext()) {
			Skill temp = itr.next();
			if (temp instanceof UsableOnce) { 
				if (((UsableOnce)temp).hasUsed()) continue;
			}
			if (temp instanceof HealthDepend) {
				if (((HealthDepend) temp).getHealthNeedToCast() < (int)((Damageable) entity).getHealth()) continue;
			}
			if(Math.random() > temp.getChance()) continue;
			
			try {
				temp.run((LivingEntity) entity);
			} catch (Exception e) {
			}
		}
	}
	
	public List<ItemStack> createDropLoot() {
		if (items == null || chances == null) return null;
		List<ItemStack> list = new ArrayList<ItemStack>(); 
		for (int i = 0; i < items.size(); i++) {
			double chance = Math.random();
			if (chance <= chances.get(i)) {
				list.add(items.get(i).getIs());
			}
		}
		return list;

	}
	
	public Vector getTargetVector(Location shooter, Location target) {
		Location first_location = shooter.add(0, 1, 0);
		Location second_location = target.add(0, 1, 0);
		Vector vector = second_location.toVector().subtract(first_location.toVector());
		return vector;
	}
	
	public void message(Entity ent, String message) {
		List<Player> list = new ArrayList<Player>();
		List<Entity> near = ent.getNearbyEntities(30, 30, 30);
		for(Entity check : near)
		{
			if(check instanceof Player)
			{
				list.add((Player) check);
			}
		}
		Mob mob = MobHandler.getMob((LivingEntity) ent);
		Iterator<Player> itr = list.iterator();
		while(itr.hasNext())
		{
			Player temp = itr.next();
			temp.sendMessage(Namer.addChatColor("<" + mob.getDisplayName() + ChatColor.RESET + "> " + message));
		}
	}
	
	//Method is old needs updating
	@SuppressWarnings("deprecation")
	private LivingEntity spawnMob(Location loc, String s) {
		if(s.equals("zombie"))
		{
			Zombie m = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			return m;
		}
		else if(s.equals("babyzombie"))
		{
			Zombie m = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			m.setBaby(true);
			return m;
		}
		else if(s.equals("villagezombie"))
		{
			Zombie m = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			m.setVillager(true);
			return m;
		}
		else if(s.equals("babyvillagezombie"))
		{
			Zombie m = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			m.setBaby(true);
			m.setVillager(true);
			return m;
		}
		//spawnSkeleton
		else if(s.equals("skeleton"))
		{
			Skeleton m = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			return m;
		}
		else if(s.equals("skeletonwither"))
		{
			Skeleton m = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			m.setSkeletonType(SkeletonType.WITHER);
			return m;
		}
		//none editables
		else if(s.equals("spider"))
		{
			Spider m = (Spider) loc.getWorld().spawnEntity(loc, EntityType.SPIDER);
			return m;
		}
		else if(s.equals("cavespider"))
		{
			CaveSpider m = (CaveSpider) loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
			return m;
		}
		else if(s.equals("slime"))
		{
			Slime m = (Slime) loc.getWorld().spawnEntity(loc, EntityType.SLIME);
			m.setSize(1);
			return m;
		}
		else if(s.equals("magmacube"))
		{
			MagmaCube m = (MagmaCube) loc.getWorld().spawnEntity(loc, EntityType.MAGMA_CUBE);
			m.setSize(1);
			return m;
		}
		else if(s.equals("pigmen"))
		{
			PigZombie m = (PigZombie) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
			m.setAngry(true);
			return m;
		}
		else if(s.equals("babypigmen"))
		{
			PigZombie m = (PigZombie) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
			m.setBaby(true);
			m.setAngry(true);
			return m;
		}
		else if(s.equals("blaze"))
		{
			Blaze m = (Blaze) loc.getWorld().spawnEntity(loc, EntityType.BLAZE);
			return m;
		}
		else if(s.equals("bat"))
		{
			Bat m = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
			return m;
		}
		else if(s.equals("witch"))
		{
			Witch m = (Witch) loc.getWorld().spawnEntity(loc, EntityType.WITCH);
			return m;
		}
		else if(s.equals("wolf"))
		{
			Wolf m = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
			m.setAngry(true);
			return m;
		}
		else if(s.equals("creeper"))
		{
			Creeper m = (Creeper) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
			return m;
		}
		else if(s.equals("ghast"))
		{
			Ghast m = (Ghast) loc.getWorld().spawnEntity(loc, EntityType.GHAST);
			return m;
		}
		else if(s.equals("enderman"))
		{
			Enderman m = (Enderman) loc.getWorld().spawnEntity(loc, EntityType.ENDERMAN);
			return m;
		}
		else if(s.equals("enderdragon"))
		{
			EnderDragon m = (EnderDragon) loc.getWorld().spawnEntity(loc, EntityType.ENDER_DRAGON);
			return m;
		}
		else if(s.equals("wither"))
		{
			Wither m = (Wither) loc.getWorld().spawnEntity(loc, EntityType.WITHER);
			return m;
		}
		else if(s.equals("ocelot"))
		{
			Ocelot m = (Ocelot) loc.getWorld().spawnEntity(loc, EntityType.OCELOT);
			m.setAdult();
			return m;
		}
		else if(s.equals("babyocelot"))
		{
			Ocelot m = (Ocelot) loc.getWorld().spawnEntity(loc, EntityType.OCELOT);
			m.setBaby();
			return m;
		}
		else if(s.equals("horse"))
		{
			Horse m = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
			m.setAdult();
			return m;
		}
		else if(s.equals("babyhorse"))
		{
			Horse m = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
			m.setBaby();
			return m;
		}
		else if(s.equals("chicken"))
		{
			Chicken m = (Chicken) loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
			m.setAdult();
			return m;
		}
		else if(s.equals("babychicken"))
		{
			Chicken m = (Chicken) loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
			m.setBaby();
			return m;
		}	
		else if(s.equals("cow"))
		{
			Cow m = (Cow) loc.getWorld().spawnEntity(loc, EntityType.COW);
			m.setAdult();
			return m;
		}		
		else if(s.equals("babycow"))
		{
			Cow m = (Cow) loc.getWorld().spawnEntity(loc, EntityType.COW);
			m.setBaby();
			return m;
		}	
		else if(s.equals("mushroomcow"))
		{
			MushroomCow m = (MushroomCow) loc.getWorld().spawnEntity(loc, EntityType.MUSHROOM_COW);
			m.setAdult();
			return m;
		}		
		else if(s.equals("babymushroomcow"))
		{
			MushroomCow m = (MushroomCow) loc.getWorld().spawnEntity(loc, EntityType.MUSHROOM_COW);
			m.setBaby();
			return m;
		}
		else if(s.equals("sheep"))
		{
			Sheep m = (Sheep) loc.getWorld().spawnEntity(loc, EntityType.SHEEP);
			m.setAdult();
			return m;
		}		
		else if(s.equals("babysheep"))
		{
			Sheep m = (Sheep) loc.getWorld().spawnEntity(loc, EntityType.SHEEP);
			m.setBaby();
			return m;
		}
		else if(s.equals("squid"))
		{
			Squid m = (Squid) loc.getWorld().spawnEntity(loc, EntityType.SQUID);
			m.setRemainingAir(1000);
			return m;
		}
		else if(s.equals("giant"))
		{
			Giant m = (Giant) loc.getWorld().spawnEntity(loc, EntityType.GIANT);
			return m;
		}
		else if(s.equals("irongolem"))
		{
			IronGolem m = (IronGolem) loc.getWorld().spawnEntity(loc, EntityType.IRON_GOLEM);
			return m;
		}
		else if(s.equals("pig"))
		{
			Pig m = (Pig) loc.getWorld().spawnEntity(loc, EntityType.PIG);
			return m;
		}
		else if(s.equals("villager"))
		{
			Villager m = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			m.setAdult();
			return m;
		}
		else if(s.equals("babyvillager"))
		{
			Villager m = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			m.setBaby();
			return m;
		}
		else if(s.equals("snowman"))
		{
			Snowman m = (Snowman) loc.getWorld().spawnEntity(loc, EntityType.SNOWMAN);
			return m;
		}
		else if(s.equals("silverfish"))
		{
			Silverfish m = (Silverfish) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
			return m;
		}
		else if(s.equals("endermite"))
		{
			Endermite m = (Endermite) loc.getWorld().spawnEntity(loc, EntityType.ENDERMITE);
			return m;
		}
		else if(s.equals("gaurdian"))
		{
			Guardian m = (Guardian) loc.getWorld().spawnEntity(loc, EntityType.GUARDIAN);
			return m;
		}
		return null;
	}
	
	public String getConfigName() {
		return configName;
	}

	public String getType() {
		return type;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}

	public boolean isCanDespawn() {
		return despawn;
	}

	public boolean isArrowImmune() {
		return arrowImmune;
	}
	
	public boolean hasDeathBroadcast() {
		return deathBroadcast;
	}

	public boolean hasSkills() {
		return hasSkills;
	}

	public boolean isParrying() {
		return parrying;
	}

	public void setParrying(boolean parrying) {
		this.parrying = parrying;
	}

	public Entity getEntity() {
		return entity;
	}

	public MobSpawner getSpawner() {
		return spawner;
	}
	
}
