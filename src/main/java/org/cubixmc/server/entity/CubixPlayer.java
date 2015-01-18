package org.cubixmc.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.cubixmc.GameMode;
import org.cubixmc.chat.ChatMessage;
import org.cubixmc.entity.Player;
import org.cubixmc.inventory.Inventory;
import org.cubixmc.inventory.PlayerInventory;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.play.PacketOutChatMessage;
import org.cubixmc.server.network.packets.play.PacketOutKeepAlive;
import org.cubixmc.server.world.World;

import java.net.InetSocketAddress;
import java.util.UUID;

public class CubixPlayer extends CubixEntityLiving implements Player {
    private final @Getter Connection connection;
    private final UUID uniqueUserId;
    private final String username;
    private GameMode gameMode = GameMode.SURVIVAL;
    
    private @Getter long keepAliveCount;
    private @Getter int keepAliveId;
    private @Getter @Setter int ping;

    public CubixPlayer(World world, Connection connection, UUID uuid, String name) {
        super(world);
        this.connection = connection;
        this.keepAliveCount = System.currentTimeMillis() + 5000L;
        this.uniqueUserId = uuid;
        this.username = name;
    }

    public void tick() {
        if(keepAliveCount < System.currentTimeMillis() - 5000L) {
            connection.sendPacket(new PacketOutKeepAlive(this.keepAliveId = random.nextInt(256)));
            this.keepAliveCount = System.currentTimeMillis();
        }
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueUserId;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void setDisplayName(String displayName) {
    }

    @Override
    public PlayerInventory getInventory() {
        throw new UnsupportedOperationException("Not done yet!");
    }

    @Override
    public InetSocketAddress getAddress() {
        return connection.getChannel().remoteAddress();
    }

    @Override
    public void kickPlayer(String message) {
        connection.disconnect(message);
    }

    @Override
    public void chat(String msg) {
    }

    @Override
    public void saveData() {
    }

    @Override
    public void loadData() {
    }

    @Override
    public boolean performCommand(String command) {
        return false;
    }

    @Override
    public boolean isSneaking() {
        return false;
    }

    @Override
    public void setSneaking(boolean sneak) {
    }

    @Override
    public boolean isSprinting() {
        return false;
    }

    @Override
    public void setSprinting(boolean sprinting) {
    }

    @Override
    public void giveExp(int amount) {
    }

    @Override
    public void giveExpLevels(int amount) {
    }

    @Override
    public float getExp() {
        return 0;
    }

    @Override
    public void setExp(float exp) {
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public void setLevel(int level) {
    }

    @Override
    public int getTotalExperience() {
        return 0;
    }

    @Override
    public void setTotalExperience(int exp) {
    }

    @Override
    public void askResourcePack(String url) {
        throw new UnsupportedOperationException("Packet not added yet!");
    }

    @Override
    public void setResourcePack(String url) {
        askResourcePack(url);
    }

    @Override
    public void sendMessage(String message) {
        ChatMessage chatMessage = ChatMessage.fromString(message);
        connection.sendPacket(new PacketOutChatMessage(chatMessage.toString(), 0));
    }

    @Override
    public void openInventory(Inventory inventory) {
        throw new UnsupportedOperationException("Not done yet!");
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public double getMaxHealth() {
        return 20.0;
    }
}
