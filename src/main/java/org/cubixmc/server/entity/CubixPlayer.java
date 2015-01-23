package org.cubixmc.server.entity;

import io.netty.util.internal.ConcurrentSet;
import lombok.Getter;
import lombok.Setter;
import org.cubixmc.GameMode;
import org.cubixmc.chat.ChatColor;
import org.cubixmc.chat.ChatMessage;
import org.cubixmc.entity.Entity;
import org.cubixmc.entity.Player;
import org.cubixmc.inventory.Inventory;
import org.cubixmc.inventory.PlayerInventory;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.play.*;
import org.cubixmc.server.threads.Threads;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.server.world.PlayerChunkMap;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Position;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.UUID;

public class CubixPlayer extends CubixEntityLiving implements Player {
    private final @Getter Set<Integer> keepAliveIds = new ConcurrentSet<>();
    private final @Getter Connection connection;
    private final @Getter PlayerChunkMap playerChunkMap;
    private final UUID uniqueUserId;
    private final String username;
    private GameMode gameMode = GameMode.SURVIVAL;
    
    private @Getter long keepAliveCount;
    private @Getter @Setter int ping;

    public CubixPlayer(CubixWorld world, Connection connection, UUID uuid, String name) {
        super(world);
        this.connection = connection;
        this.keepAliveCount = System.currentTimeMillis() + 5000L;
        this.uniqueUserId = uuid;
        this.username = name;
        this.playerChunkMap = new PlayerChunkMap(this);
    }

    public void tick() {
        if(keepAliveCount < System.currentTimeMillis() - 5000L) {
            int keepAliveId = random.nextInt(1024);
            while(keepAliveIds.contains(keepAliveId)) {
                keepAliveId = random.nextInt(1024);
            }

            keepAliveIds.add(keepAliveId);
            connection.sendPacket(new PacketOutKeepAlive(keepAliveId));
            this.keepAliveCount = System.currentTimeMillis();
        }
    }

    @Override
    public boolean spawn(final Position position) {
        if(!super.spawn(position)) {
            return false;
        }

        // Send properties fo world and player
        PacketOutJoinGame join = new PacketOutJoinGame(entityId, 0, 0, 0, 60, "default", false);
        PacketOutSpawnPosition compass = new PacketOutSpawnPosition(position);
        PacketOutPlayerAbilities abilities = new PacketOutPlayerAbilities(6, 0.1F, 0.2F);
        connection.sendPackets(join, compass, abilities);

        // Send the chunks and perform the reast in the world thread
        Threads.worldExecutor.execute(new Runnable() {
            @Override
            public void run() {
                playerChunkMap.sendAll();
                PacketOutPlayerPositionLook coords = new PacketOutPlayerPositionLook(position.getX(), position.getY(), position.getZ());
                connection.sendPacket(coords);
            }
        });
        return true;
    }

    public CubixWorld getWorld() {
        return world;
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
        for(Player p : CubixServer.getInstance().getOnlinePlayers()){
            p.sendMessage(username + " : " + msg);
        }
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
    public void giveExp(float amount) {
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
    public float getTotalExperience() {
        return 0;
    }

    @Override
    public void setTotalExperience(float exp) {

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
        message = ChatColor.replace('&', message);
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
    public void setHeader(String message) {

    }

    @Override
    public void setFooter(String message) {

    }

    @Override
    public PacketOut getSpawnPacket() {
        PacketOutSpawnPlayer packet = new PacketOutSpawnPlayer();
        packet.setEntityID(entityId);
        packet.setPlayerUUID(uniqueUserId);
        packet.setCurrentItem((short) 0);
        packet.setX(MathHelper.floor(position.getX() * 32.0));
        packet.setY(MathHelper.floor(position.getY() * 32.0));
        packet.setZ(MathHelper.floor(position.getZ() * 32.0));
        packet.setYaw(MathHelper.byteToDegree(position.getYaw()));
        packet.setPitch(MathHelper.byteToDegree(position.getPitch()));
        packet.setMetadata(metadata);
        return packet;
    }
}
