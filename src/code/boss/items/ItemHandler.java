package boss.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import code.op.gear.CarbyneGear;
import code.op.gear.GearHandler;
import boss.BossPlugin;
import boss.utils.Namer;

public class ItemHandler {

	public static List<ItemObject> items = new ArrayList<ItemObject>();
	
	public static void loadItems(FileConfiguration fc) {
		ConfigurationSection cs = fc.getConfigurationSection("Items");
		
		for (String s : cs.getKeys(false)) {
			Material mat = Material.getMaterial(cs.getString(s + ".Type"));
			short data = (short)cs.getInt(s + ".Data");
			int amount = cs.getInt(s + ".Amount");
			
			ItemStack is = new ItemStack(mat, amount, data);
			
			if(cs.contains(s + ".Name")) {
				Namer.setName(is, cs.getString(s + ".Name"));
			}
			
			if(cs.contains(s + ".Lore")) {
				Namer.setLore(is, cs.getStringList(s + ".Lore"));
			}
			
			if(cs.contains(s + ".Enchantments")) {
				Iterator<String> eItr = cs.getStringList(s + ".Enchantments").iterator();
				while(eItr.hasNext()) {
					String[] split = eItr.next().split(",");
					int lvl = Integer.valueOf(split[1]);
					is.addUnsafeEnchantment(Enchantment.getByName(split[0]), lvl);
				}
			}
			items.add(new ItemObject(s, is));
		}
		
		if (BossPlugin.carbyneEnabled) {
			ConfigurationSection carcs = fc.getConfigurationSection("Carbyne");
			GearHandler handler = BossPlugin.carbyne.getGearHanlder();
			for (String s : carcs.getKeys(false)) {
				if (carcs.getString(s + ".GearCode") == null) continue;
				String code = carcs.getString(s + ".GearCode");
				ItemStack is = getCarbyneGear(code, handler);
				if (is == null) continue;
				items.add(new ItemObject(s, is));
			}

			if (GearHandler.getMoney() != null) {
				items.add(new ItemObject("Money", GearHandler.getMoney()));
			}
		}
		
	}
	
	public static ItemStack getCarbyneGear(String tag, GearHandler handler) {
		for (CarbyneGear cg : handler.getGear()) {
			if (cg.getDisplayName().equals(tag)) {
				return cg.getItem();
			}
		}
		return null;
	}
	
}
