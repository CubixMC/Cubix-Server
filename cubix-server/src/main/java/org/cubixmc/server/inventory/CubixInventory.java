package org.cubixmc.server.inventory;

import org.cubixmc.entity.Entity;
import org.cubixmc.inventory.Inventory;
import org.cubixmc.inventory.InventoryType;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.entity.CubixEntity;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CubixInventory implements Inventory {
    private final Set<Container> containers = ConcurrentHashMap.newKeySet();
    protected final CubixEntity holder;
    protected ItemStack[] contents;
    private InventoryType type;
    private String title;

    public CubixInventory(CubixEntity holder, InventoryType type) {
        this(holder, type, type.getDefaultTitle());
    }

    public CubixInventory(CubixEntity holder, InventoryType type, String title) {
        this(holder, type, title, type.getDefaultSize());
    }

    public CubixInventory(CubixEntity holder, InventoryType type, String title, int size) {
        this.holder = holder;
        this.contents = new ItemStack[size];
        this.type = type;
        this.title = title;
    }

    public void addContainer(Container container) {
        containers.add(container);
    }

    public void removeContainer(Container container) {
        containers.remove(container);
    }

    public void syncSingleSlot(int index) {
        for(Container container : containers) {
            container.markOne(index);
        }
    }

    public void syncAllSlots() {
        for(Container container : containers) {
            container.markAll();
        }
    }

    @Override
    public int size() {
        return contents.length;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void clear() {
        Arrays.fill(contents, null);
    }

    @Override
    public InventoryType getType() {
        return type;
    }

    @Override
    public void setType(InventoryType type) {
        this.type = type;
        this.contents = new ItemStack[type.getDefaultSize()];
    }

    @Override
    public Entity getHolder() {
        return holder;
    }

    @Override
    public ItemStack[] getContents() {
        return contents;
    }

    @Override
    public void setContents(ItemStack[] contents) {
        if(contents.length != size()) {
            throw new IllegalArgumentException("Array length does not equal to inventory size!");
        }

        this.contents = contents;
        syncAllSlots();
    }

    @Override
    public ItemStack getItem(int index) {
        if(index < 0 || index > size()) {
            throw new IllegalArgumentException("Slot index out of bounds! (0-size allowed)");
        }

        return contents[index];
    }

    @Override
    public void setItem(int index, ItemStack item) {
        if(index < 0 || index > size()) {
            throw new IllegalArgumentException("Slot index out of bounds! (0-size allowed)");
        }

        contents[index] = item;
        syncSingleSlot(index);
    }

    public boolean receive(ItemStack item) {
        // Merge with current stack
        boolean changed = false;
        for(int index = 0; index < contents.length; index++) {
            ItemStack i = contents[index];
            if(i == null) continue;
            if(i.getType() == item.getType() && i.getAmount() < 64) { // TODO: Max stack size.
                int allowance = Math.min(item.getAmount(), 64 - i.getAmount());
                i.setAmount(i.getAmount() + allowance);
                item.setAmount(item.getAmount() - allowance);
                changed = true; // Mark as changed
                syncSingleSlot(index); // Update slot
                if(item.getAmount() == 0) {
                    return true;
                }
            }
        }

        // Still items left, lets see if we can add them to our inventory
        if(addItem(item.clone())) {
            item.setAmount(0);
            return true;
        }
        return changed;
    }

    @Override
    public boolean addItem(ItemStack item) {
        for(int i = 0; i < contents.length; i++) {
            if(contents[i] == null) {
                contents[i] = item;
                syncSingleSlot(i);
                return true;
            }
        }

        return false;
    }
}
