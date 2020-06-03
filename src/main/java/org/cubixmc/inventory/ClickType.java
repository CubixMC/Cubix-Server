package org.cubixmc.inventory;

public enum ClickType {
    /**
     * Normal click
     * Used for: Moving stack/half stack via cursor
     */
    LEFT_CLICK(0, 0),
    RIGHT_CLICK(0, 1),

    /**
     * Shift click
     * Used for: Moving stack/half stack instantly
     */
    SHIFT_LEFT_CLICK(1, 0),
    SHIFT_RIGHT_CLICK(1, 1),

    /**
     * Number press
     * Used for:
     */
    NUM_1(2, 0),
    NUM_2(2, 1),
    NUM_3(2, 2),
    NUM_4(2, 3),
    NUM_5(2, 4),
    NUM_6(2, 5),
    NUM_7(2, 6),
    NUM_8(2, 7),
    NUM_9(2, 8),

    /**
     * Middle button (mouse scrol wheel)
     * Used for:
     */
    MIDDLE_CLICK(3, 2),

    /**
     * Q & Ctrl+Q click
     * Used for: Dropping item/stack from inventory
     */
    DROP_SINGLE(4, 0),
    DROP_STACK(4, 1),

    /**
     * Drag click
     * Used for: Dragging items over screen, similar to NORMAL
     */
    START_LEFT_DRAG(5, 0),
    START_RIGHT_DRAG(5, 4),
    ADD_SLOT_LEFT_DRAG(5, 1),
    ADD_SLOT_RIGHT_DRAG(5, 5),
    END_LEFT_DRAG(5, 2),
    END_RIGHT_DRAG(5, 6),

    /**
     * Double click
     * Used for:
     */
    DOUBLE_CLICK(6, 0);

    private final int mode;
    private final int button;

    ClickType(int mode, int button) {
        this.mode = mode;
        this.button = button;
    }

    public static ClickType getById(int mode, int button) {
        for(ClickType type : values()) {
            if(type.mode == mode && type.button == button) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown mode or trigger!");
    }
}
