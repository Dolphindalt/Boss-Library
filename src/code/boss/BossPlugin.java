package boss;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import code.op.Main;
import boss.items.ItemHandler;
import boss.listeners.EntityDamageByEntityListener;
import boss.listeners.EntityDeathListener;
import boss.mob.MobCommands;
import boss.mob.MobHandler;
import boss.spawners.MobSpawnerHandler;

public class BossPlugin extends JavaPlugin {

	public static final Logger logger = Logger.getLogger("Minecraft");
	
	public static BossPlugin instance;
	
	public static Plugin carbynePlugin;
	public static Main carbyne;
	public static boolean carbyneEnabled = false;
	
	public File mobFile;
	public File itemFile;
	
	public FileConfiguration mobData;
	public FileConfiguration itemData;
	
	public void onEnable() {
		instance = this;
		
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		if (pm.isPluginEnabled("Carbyne")) {
			carbynePlugin = pm.getPlugin("Carbyne");
			carbyne = (Main) carbynePlugin;
			carbyneEnabled = true;
		}
		
		mobFile = new File(getDataFolder(), "mobs.yml");
		itemFile = new File(getDataFolder(), "items.yml");
		
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mobData = YamlConfiguration.loadConfiguration(mobFile);
		itemData = YamlConfiguration.loadConfiguration(itemFile);
		
		ItemHandler.loadItems(itemData);
		logger.info("[Item Handler]: Loaded " + ItemHandler.items.size() + " items!");
		// Important that this loads first.
		
		MobHandler.loadMobs(mobData);
		logger.info("[Mob Handler]: Loaded " + MobHandler.mobs.size() + " mobs!");
		
		registerEvents(pm);
		registerCommands();
	}
	
	public void onDisable() {
		Iterator<Entity> itr = MobHandler.livingMobs.iterator();
		while(itr.hasNext()) {
			Entity temp = itr.next();
			temp.remove();
		}
	}
	
	public void registerEvents(PluginManager pm) {
		pm.registerEvents(new EntityDamageByEntityListener(), this);
		pm.registerEvents(new EntityDeathListener(), this);
	}
	
	public void registerCommands() {
		this.getCommand("boss").setExecutor(new MobCommands());
	}
	
	public void registerTasks() {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new MobSpawnerHandler(), 0L, 20L);
	}
	
	private void firstRun() throws Exception {
	    if(!mobFile.exists()) {
	        mobFile.getParentFile().mkdirs();
	        copy(getResource("mobs.yml"), mobFile);
	    }
	    if(!itemFile.exists()) {
	        itemFile.getParentFile().mkdirs();
	        copy(getResource("items.yml"), itemFile);
	    }
	}
	
	private void copy(InputStream in, File file)
	{
	    try
	    {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0)
	        {
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
}
