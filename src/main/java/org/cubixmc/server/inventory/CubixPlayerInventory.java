package org.cubixmc.server.inventory;

import org.cubixmc.inventory.InventoryType;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.inventory.PlayerInventory;
import org.cubixmc.server.entity.CubixPlayer;

public class CubixPlayerInventory extends CubixInventory implements PlayerInventory {

    public CubixPlayerInventory(CubixPlayer player) {
        super(player, InventoryType.PLAYER, "",  45);
    }

    public ItemStack[] getNetworkContents() {
        return super.getContents();
    }

    @Override
    public ItemStack getItem(int index) {
        if(index >= 0 && index < 9) { // hotbar
            index += 36;
        } else if(index >= 100 && index < 104) { // armor
            index = 8 - (index - 100);
        } else if(index >= 80 && index < 84) { // crafting
            index -= 79;
        }

        return super.getItem(index);
    }

    @Override
    public void setItem(int index, ItemStack item) {
        if(index >= 0 && index < 9) { // hotbar
            index += 36;
        } else if(index >= 100 && index < 104) { // armor
            index = 8 - (index - 100);
        } else if(index >= 80 && index < 84) { // crafting
            index -= 79;
        }

        super.setItem(index, item);
    }

    @Override
    public ItemStack[] getContents() {
        ItemStack[] actual = new ItemStack[36];
        System.arraycopy(getContents(), 36, actual, 0, 9);
        System.arraycopy(getContents(), 9, actual, 9, 27);

        return actual;
    }

    @Override
    public void setContents(ItemStack[] contents) {
        System.arraycopy(contents, 0, getContents(), 36, 9);
        System.arraycopy(contents, 9, getContents(), 9, 27);
        syncAllSlots();
    }

    @Override
    public boolean addItem(ItemStack item) {
        for(int i = 36; i <= 44; i++) {
            if(contents[i] == null) {
                contents[i] = item;
                syncSingleSlot(i);
                return true;
            }
        }
        for(int i = 9; i <= 35; i++) {
            if(contents[i] == null) {
                contents[i] = item;
                syncSingleSlot(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public void setHelmet(ItemStack helmet) {
        setItem(103, helmet);
    }

    @Override
    public void setChestplate(ItemStack chestplate) {
        setItem(102, chestplate);
    }

    @Override
    public void setLeggings(ItemStack leggings) {
        setItem(101, leggings);
    }

    @Override
    public void setBoots(ItemStack boots) {
        setItem(100, boots);
    }

    @Override
    public ItemStack getHelmet() {
        return getItem(103);
    }

    @Override
    public ItemStack getChestplate() {
        return getItem(102);
    }

    @Override
    public ItemStack getLeggings() {
        return getItem(101);
    }

    @Override
    public ItemStack getBoots() {
        return getItem(100);
    }
}
