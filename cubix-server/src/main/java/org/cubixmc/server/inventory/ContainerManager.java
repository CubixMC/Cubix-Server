package org.cubixmc.server.inventory;

import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.packets.play.PacketInClickWindow;
import org.cubixmc.server.network.packets.play.PacketOutCloseWindow;
import org.cubixmc.server.network.packets.play.PacketOutConfirmTransaction;
import org.cubixmc.server.network.packets.play.PacketOutOpenWindow;

/**
 * ContainerManager
 *
 * Manages the inventory containers.
 * Also handles transaction confirmation and inventory opening/closing.
 */
public class ContainerManager {
    private final CubixPlayer holder;
    private Container playerContainer;
    private Container currentContainer;
    private int currentWindowId = 1;
    private short askingForApology = -1; // Client apologies, when an action goes wrong we wait for client confirmation.

    public ContainerManager(CubixPlayer player, CubixPlayerInventory inventory) {
        this.holder = player;
        this.playerContainer = new Container(0, player, inventory);
        inventory.addContainer(playerContainer);
    }

    /**
     * Create a new container for a given inventory.
     *
     * @param inventory to contain
     * @return Container for inventory
     */
    public Container newContainer(CubixInventory inventory) {
        Container container = new Container(nextWindowId(), holder, inventory);
        container.markAll(); // Mark all slots for update
        return container;
    }

    /**
     * Handle Client Confirm Transaction packet.
     * Response to error in inventory action.
     * If id correct, re-enable inventory action handling.
     *
     * @param actionId ID of action
     */
    public void handleApology(int actionId) {
        System.out.println("Received apology " + actionId);
        if(actionId != askingForApology) return;
        this.askingForApology = -1;
    }

    /**
     * Perform an inventory action.
     *
     * @param packet Packet containing action info.
     */
    public void performInventoryAction(PacketInClickWindow packet) {
        if(askingForApology >= 0) {
            return; // We are waiting for an apology from the client.
        }

        boolean success;
        Container container;
        if(packet.getWindowID() == 0) {
            // Player clicked in their own inventory
            success = (container = playerContainer).performInventoryAction(packet);
        } else if(currentContainer != null && packet.getWindowID() == currentContainer.getWindowId()) {
            success = (container = currentContainer).performInventoryAction(packet);
        } else {
            System.out.println("ERROR: Unknown window id!");
            return;
        }

        if(!success) {
            // Something went wrong, don't accept any more actions until the client has apologized.
            askingForApology = packet.getActionNumber(); // Lock container until apology is received.
            System.out.println("Asking for apology " + askingForApology);
            PacketOutConfirmTransaction confirm = new PacketOutConfirmTransaction(packet.getWindowID(), askingForApology, false);

            container.refresh(); // Send our current inventory as we know it.
            holder.getConnection().sendPacket(confirm); // Tell client to apologize.
        }
    }

    /**
     * The current container opened by the player.
     *
     * @return Current container.
     */
    public Container getCurrentContainer() {
        return currentContainer;
    }

    /**
     * Update the current container if exists.
     * Note: Also updates the player container.
     */
    public void updateCurrentContainer() {
        playerContainer.update();
        if(currentContainer == null) return;
        currentContainer.update();
    }

    /**
     * Open a container for the player being managed.
     *
     * @param container to be opened
     */
    public void openContainer(Container container) {
        if(currentContainer != null) {
            // Close current container first...
            closeCurrentContainer();
        }

        this.currentContainer = container;
        CubixInventory inventory = container.getInventory();
        inventory.addContainer(container);
        holder.getConnection().sendPacket(new PacketOutOpenWindow(container.getWindowId(), inventory.getType(),
                inventory.getTitle(), inventory.size(), 0));
    }

    /**
     * Close the currently open container.
     */
    public void closeCurrentContainer() {
        closeContainer(currentContainer);
    }

    public void closeContainer(Container container) {
        holder.getConnection().sendPacket(new PacketOutCloseWindow(container.getWindowId()));
        container.getInventory().removeContainer(container);
    }

    private int nextWindowId() {
        this.currentWindowId = currentWindowId % 100 + 1;
        return currentWindowId;
    }
}
