package org.cubixmc;

/**
 * @author Matthew Hogan
 */
public enum GameMode {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private final int id;

    private GameMode(int id) {
        this.id = id;
    }

    public static GameMode getById(int id) {
        for(GameMode mode : values()) {
            if(mode.id == id) {
                return mode;

            }
        }
        return null;
    }
}
