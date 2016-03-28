package boss.items;

import org.bukkit.inventory.ItemStack;

public class ItemObject {

	private ItemStack is;
	private String configName;
	
	public ItemObject(String configName, ItemStack is) {
		this.configName = configName;
		this.is = is;
	}
	
	public String getConfigName() {
		return configName;
	}

	public ItemStack getIs() {
		return is.clone();
	}
	
}
