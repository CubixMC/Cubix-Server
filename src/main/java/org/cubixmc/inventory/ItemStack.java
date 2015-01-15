package org.cubixmc.inventory;

public class ItemStack {
    private final Material material;
    private int amount;
    private short data;

    public ItemStack() {
        this(Material.AIR);
    }

    public ItemStack(Material material) {
        this(material, 1);
    }

    public ItemStack(Material material, int amount) {
        this(material, amount, (short) 0);
    }

    public ItemStack(Material material, int amount, short data) {
        if(material == null) {
            throw new IllegalArgumentException("Material cannot be null!");
        }

        this.material = material;
        this.amount = amount;
        this.data = data;
    }

    public Material getType() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public short getData() {
        return data;
    }

    public void setData(short data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ItemStack itemStack = (ItemStack) o;

        if(amount != itemStack.amount) return false;
        if(data != itemStack.data) return false;
        if(material != itemStack.material) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = material.hashCode();
        result = 31 * result + amount;
        result = 31 * result + (int) data;
        return result;
    }

    @Override
    public String toString() {
        return "ItemStack{" +
                "material=" + material +
                ", amount=" + amount +
                ", data=" + data +
                '}';
    }
}
