package org.cubixmc.chat;

public enum ChatColor {
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    CYAN('3'),
    DARK_RED('4'),
    PURPLE('5'),
    GOLD('6'),
    GREY('7'),
    DARK_GREY('8'),
    BLUE('9'),
    OBFUSCATED('k'),
    BOLD('l'),
    STRIKE_THROUH('m'),
    UNDERLINE('n'),
    ITALIC('i'),
    RESET('r');

    private static final char COLOR_CHAR = '\u00A7';
    private final char identifier;

    private ChatColor(char identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return String.valueOf(COLOR_CHAR) + identifier;
    }

    /**
     * Replace all colors with a custom color character with the default character.
     *
     * @param colorChar The chosen character
     * @param message The message to parse
     * @return The message with replaced color codes
     */
    public static String replace(char colorChar, String message) {
        char[] chars = message.toCharArray();
        for(int i = 0; i < chars.length - 1; i++) {
            if(chars[i] == colorChar && "0123456789ABCDEFabcdef".indexOf(chars[i + 1]) > -1) {
                chars[i] = COLOR_CHAR;
                chars[i + 1] = Character.toLowerCase(chars[i + 1]);
            }
        }

        return new String(chars);
    }

    public static ChatColor getByChar(char c) {
        for(ChatColor color : values()) {
            if(color.identifier == c) {
                return color;
            }
        }

        return null;
    }

    /**
     * Get the default color identifier.
     *
     * @return Default color code character
     */
    public static char getColorChar() {
        return COLOR_CHAR;
    }
}
