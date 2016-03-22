package boss.items;

import org.bukkit.inventory.ItemStack;

public class ItemObject {

	private ItemStack is;
	private String configName;
	private String name;
	
	public ItemObject(String name, String configName, ItemStack is) {
		this.configName = configName;
		this.name = name;
		this.is = is;
	}

	public String getName() {
		return name;
	}
	
	public String getConfigName() {
		return configName;
	}

	public ItemStack getIs() {
		return is.clone();
	}
	
}
