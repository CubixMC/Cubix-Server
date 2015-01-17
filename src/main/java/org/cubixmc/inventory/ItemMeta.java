package org.cubixmc.inventory;



public interface ItemMeta {

/*
*
*@return If The Item Has A Display Name
*/
public boolean hasDisplayName();

/*
*
*@return If The Item Has A Lore
*/
public boolean hasLore();

public ItemMeta clone();

/*
*
*@return if item has enchants
*/
public boolean hasEnchantments();

/*
*
*@return The Item's Lore
*/
public List<String> getLore();
/*
*
*
*@return a new lore for the item
*/
public void setLore(List<String> lore);
/*
*
*@return a new DisplayName for the item
*/
public void setDisplayName(String name);


}