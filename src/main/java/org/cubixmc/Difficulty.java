package org.cubixmc;

public enum Difficulty {
    PEACEFUL(0),
    EASY(1),
    NORMAL(2),
    HARD(3);

    private final int id;

    private Difficulty(int id) {
        this.id = id;
    }

    public static Difficulty getById(int id) {
        Difficulty difficulty = PEACEFUL;
        for(Difficulty mode : values()) {
            if(difficulty.id == id) {
                difficulty = mode;
                break;
            }
        }

        return difficulty;
    }
}
