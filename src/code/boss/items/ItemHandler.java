package boss.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

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
			System.out.println(s);
			items.add(new ItemObject(cs.getString(s + ".Name"), s, is));
		}
	}
	
}
