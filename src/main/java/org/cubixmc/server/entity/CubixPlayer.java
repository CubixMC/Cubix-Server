package org.cubixmc.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.cubixmc.GameMode;
import org.cubixmc.chat.ChatMessage;
import org.cubixmc.entity.Player;
import org.cubixmc.inventory.Inventory;
import org.cubixmc.inventory.PlayerInventory;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.play.PacketOutChatMessage;
import org.cubixmc.server.network.packets.play.PacketOutKeepAlive;
import org.cubixmc.server.network.packets.play.PacketOutPlayerListHeaderFooter;
import org.cubixmc.server.world.World;

import java.net.InetSocketAddress;
import java.util.UUID;

public class CubixPlayer extends CubixEntityLiving implements Player {
    private final @Getter Connection connection;
    private final UUID uniqueUserId;
    private final String username;
    private String displayName;
    private GameMode gameMode = GameMode.SURVIVAL;
    private @Setter(AccessLevel.PRIVATE) float xp;
    private @Setter(AccessLevel.PRIVATE) int levels;
    private @Setter(AccessLevel.PRIVATE) float totalxp;

    private @Getter long keepAliveCount;
    private @Getter int keepAliveId;
    private @Getter @Setter int ping;
    private String header, footer;


    public CubixPlayer(World world, Connection connection, UUID uuid, String name) {
        super(world);
        this.connection = connection;
        this.keepAliveCount = System.currentTimeMillis() + 5000L;
        this.uniqueUserId = uuid;
        this.username = name;
        this.displayName = username;
    }

    public void tick() {
        if (keepAliveCount < System.currentTimeMillis() - 5000L) {
            connection.sendPacket(new PacketOutKeepAlive(this.keepAliveId = random.nextInt(256)));
            this.keepAliveCount = System.currentTimeMillis();
        }
    }

    @Override
    public void setHeader(String message) {
        this.header = message;
        PacketOutPlayerListHeaderFooter packet = new PacketOutPlayerListHeaderFooter();
        packet.setHeader(message);
        packet.setFooter(footer);
        connection.sendPacket(packet);
    }

    @Override
    public void setFooter(String message) {
        this.footer = message;
        PacketOutPlayerListHeaderFooter packet = new PacketOutPlayerListHeaderFooter();
        packet.setFooter(message);
        packet.setHeader(header);
        connection.sendPacket(packet);
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
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
        for (Player p : CubixServer.getInstance().getOnlinePlayers()) {
            p.sendMessage(displayName + " : " + msg);
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
        setXp(amount + xp);
        setTotalxp(totalxp + xp);
        float temp = 0;
        if (levels < 16) {
            temp = (levels * levels) + (6 * levels);
        } else if (levels < 31) {
            temp = 2.5F * (levels * levels) - (40.5F * levels) + 360;
        } else {
            temp = 4.5F * (levels * levels) + (162.5F * levels) + 2220;
        }
        if (xp > temp) {
            xp -= temp;
            levels++;
        }
    }

    @Override
    public void giveExpLevels(int amount) {
        setLevels(amount + levels);
        int temp = levels;
        for (int i = 0; i < temp; i++) {
            if (temp < 16) {
                setTotalxp(totalxp + (levels * levels) + (6 * levels));
            } else if (levels < 31) {
                setTotalxp(totalxp + 2.5F * (levels * levels) - (40.5F * levels) + 360);
            } else {
                setTotalxp(totalxp + 4.5F * (levels * levels) + (162.5F * levels) + 2220);
            }
        }
    }

    @Override
    public float getExp() {
        return xp;
    }

    @Override
    public void setExp(float exp) {
        setXp((int) exp);
       //TODO ADD LEVELS
    }

    @Override
    public int getLevel() {
        return levels;
    }

    @Override
    public void setLevel(int level) {
        setLevels(level);
        //TODO ADD totalxp

    }

    @Override
    public float getTotalExperience() {
        return totalxp;
    }

    @Override
    public void setTotalExperience(float exp) {
        setTotalxp(exp);
        //TODO ADD LEVELS

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
