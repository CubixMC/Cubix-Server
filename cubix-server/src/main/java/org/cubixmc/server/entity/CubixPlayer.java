package org.cubixmc.server.entity;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.chat.ComponentSerializer;
import org.cubixmc.GameMode;
import org.cubixmc.chat.ChatColor;
import org.cubixmc.chat.ChatMessage;
import org.cubixmc.entity.Player;
import org.cubixmc.inventory.Inventory;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.inventory.Material;
import org.cubixmc.inventory.PlayerInventory;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.inventory.Container;
import org.cubixmc.server.inventory.ContainerManager;
import org.cubixmc.server.inventory.CubixInventory;
import org.cubixmc.server.inventory.CubixPlayerInventory;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.play.*;
import org.cubixmc.server.network.packets.play.PacketOutPlayerListItem.ListAction;
import org.cubixmc.server.util.auth.GameProfile;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.server.world.PlayerChunkMap;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Position;
import org.cubixmc.util.Vector3D;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CubixPlayer extends CubixEntityLiving implements Player {
    private final @Getter Set<Integer> keepAliveIds = ConcurrentHashMap.newKeySet();
    private final @Getter Connection connection;
    private final @Getter PlayerChunkMap playerChunkMap;
    private final @Getter GameProfile profile;
    private final CubixPlayerInventory inventory;
    private final @Getter ContainerManager containerManager;
    private GameMode gameMode = GameMode.SURVIVAL;
    
    private @Getter long keepAliveCount;
    private @Getter @Setter int ping;

    private String displayName;
    private String header = "";
    private String footer = "";

    public CubixPlayer(CubixWorld world, Connection connection, GameProfile profile) {
        super(world);
        this.connection = connection;
        this.keepAliveCount = System.currentTimeMillis() + 5000L;
        this.profile = profile;
        this.playerChunkMap = new PlayerChunkMap(this);
        this.displayName = profile.getName();
        this.inventory = new CubixPlayerInventory(this);
        this.containerManager = new ContainerManager(this, inventory);
        inventory.setItem(1, new ItemStack(Material.WOOD, 64));
        inventory.setItem(2, new ItemStack(Material.WOOD, 64));
        inventory.setItem(3, new ItemStack(Material.WOOD, 64));
        setBounds(new Vector3D(0.6, 1.8, 0.6));

        // Set default settings (for now)
        metadata.set(10, (byte) 127);
        metadata.set(16, (byte) 0);
        metadata.set(17, 0F);
        metadata.set(18, 0);
    }

    @Override
    public void move(double dx, double dy, double dz) {
        int cx = MathHelper.floor(position.getX()) >> 4;
        int cz = MathHelper.floor(position.getZ()) >> 4;
        super.move(dx, dy, dz);
        int newX = MathHelper.floor(position.getX()) >> 4;
        int newZ = MathHelper.floor(position.getZ()) >> 4;
        if(cx != newX || cz != newZ) {
            playerChunkMap.movePlayer();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(keepAliveCount < System.currentTimeMillis() - 5000L) {
            int keepAliveId = random.nextInt(1024);
            while(keepAliveIds.contains(keepAliveId)) {
                keepAliveId = random.nextInt(1024);
            }

            keepAliveIds.add(keepAliveId);
            connection.sendPacket(new PacketOutKeepAlive(keepAliveId));
            this.keepAliveCount = System.currentTimeMillis();
        }

        // Update inventory
        containerManager.updateCurrentContainer();
    }

    @Override
    public boolean spawn(final Position position) {
        metadata.set(10, (byte) 127);
        if(!super.spawn(position)) {
            return false;
        }

        // Send properties fo world and player
        PacketOutJoinGame join = new PacketOutJoinGame(entityId, 0, 0, 0, 60, "default", false);
        PacketOutSpawnPosition compass = new PacketOutSpawnPosition(position);
        PacketOutPlayerAbilities abilities = new PacketOutPlayerAbilities(6, 0.05F, 0.1F);
        connection.sendPackets(join, compass, abilities);

        // Send the chunks
        playerChunkMap.sendAll();

        // Send the player's spawn coordinate
        PacketOutPlayerPositionLook coords = new PacketOutPlayerPositionLook(position.getX(), position.getY(), position.getZ());
        connection.sendPacket(coords);

        // Send the spawn packet to other players
        connection.sendPacket(new PacketOutPlayerListItem(ListAction.ADD_PLAYER, CubixServer.getInstance().getOnlinePlayers()));
        connection.sendPacket(new PacketOutEntityMetadata(entityId, metadata));
        CubixServer.broadcast(new PacketOutPlayerListItem(ListAction.ADD_PLAYER, Collections.singletonList(CubixPlayer.this)), world, CubixPlayer.this);
        CubixServer.broadcast(CubixPlayer.this.getSpawnPackets().get(0), world, CubixPlayer.this);

        // Spawn entities
        for(CubixEntity entity : world.getEntityList()) {
            if(entity.equals(CubixPlayer.this)) {
                continue;
            }

            // Spawn players
            connection.sendPackets(entity.getSpawnPackets().toArray(new PacketOut[0]));
        }

        // Set window
        inventory.syncAllSlots();

        // Announce join
        BaseComponent[] message = new ComponentBuilder(getName())
                .color(net.md_5.bungee.api.ChatColor.YELLOW)
                .append(" has joined the game")
                .color(net.md_5.bungee.api.ChatColor.GRAY)
                .create();
        CubixServer.getInstance().getOnlinePlayers().forEach(p -> p.sendMessage(message));
        return true;
    }

    public CubixWorld getWorld() {
        return world;
    }

    @Override
    public String getName() {
        return profile.getName();
    }

    @Override
    public UUID getUniqueId() {
        return profile.getUuid();
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
        return inventory;
    }

    @Override
    public void closeInventory() {
        containerManager.closeCurrentContainer();
    }

    @Override
    public boolean isInsideInventory() {
        return containerManager.getCurrentContainer() != null;
    }

    @Override
    public Inventory getOpenInventory() {
        return containerManager.getCurrentContainer().getInventory();
    }

    @Override
    public void kickPlayer(String message) {
        connection.disconnect(message);
    }

    @Override
    public void chat(String msg) {
        for(Player p : CubixServer.getInstance().getOnlinePlayers()){
            p.sendMessage(getDisplayName() + " : " + msg);
        }
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
    public boolean isSprinting() {
        return false;
    }

    @Override
    public void sendMessage(String message) {
        message = ChatColor.replace('&', message);
        ChatMessage chatMessage = ChatMessage.fromString(message);
        connection.sendPacket(new PacketOutChatMessage(chatMessage.toString(), 0));
    }

    public void sendMessage(BaseComponent... components) {
        connection.sendPacket(new PacketOutChatMessage(ComponentSerializer.toString(components), 0));
    }

    @Override
    public void openInventory(Inventory inventory) {
        Container container = containerManager.newContainer((CubixInventory) inventory);
        containerManager.openContainer(container);
    }

    @Override
    public InetSocketAddress getAddress() {
        return connection.getSocketAddress();
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
        if(message == null) {
            throw new IllegalArgumentException("The message cannot be null!");
        }

        message = ChatMessage.fromString(message).toString();
        PacketOutPlayerListHeaderFooter packet = new PacketOutPlayerListHeaderFooter(this.header = message, footer);
        connection.sendPacket(packet);
    }

    @Override
    public void setFooter(String message) {
        if(message == null) {
            throw new IllegalArgumentException("The message cannot be null!");
        }

        message = ChatMessage.fromString(message).toString();
        PacketOutPlayerListHeaderFooter packet = new PacketOutPlayerListHeaderFooter(header, this.footer = message);
        connection.sendPacket(packet);
    }

    @Override
    public List<PacketOut> getSpawnPackets() {
        List<PacketOut> list = Lists.newArrayList();
        PacketOutSpawnPlayer packet = new PacketOutSpawnPlayer();
        packet.setEntityID(entityId);
        packet.setPlayerUUID(profile.getUuid());
        packet.setCurrentItem((short) 0);
        packet.setX(MathHelper.floor(position.getX() * 32.0));
        packet.setY(MathHelper.floor(position.getY() * 32.0));
        packet.setZ(MathHelper.floor(position.getZ() * 32.0));
        packet.setYaw(MathHelper.byteToDegree(position.getYaw()));
        packet.setPitch(MathHelper.byteToDegree(position.getPitch()));
        packet.setMetadata(metadata);
        list.add(packet);
        return list;
    }
}
