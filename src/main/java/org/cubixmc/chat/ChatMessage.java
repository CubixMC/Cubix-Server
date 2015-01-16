package org.cubixmc.chat;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class ChatMessage {
    private final JsonObject object = new JsonObject();
    private final JsonArray extras = new JsonArray();

    public ChatMessage(String message) {
        object.addProperty("text", message);
        object.add("extra", extras);
    }

    public void bold(boolean bold) {
        object.addProperty("bold", bold);
    }

    public void italic(boolean italic) {
        object.addProperty("italic", italic);
    }

    public void underlined(boolean underlined) {
        object.addProperty("underlined", underlined);
    }

    public void strikeThrough(boolean strikeThrough) {
        object.addProperty("strikethrough", strikeThrough);
    }

    public void obfuscated(boolean obfuscated) {
        object.addProperty("obfuscated", obfuscated);
    }

    public void color(ChatColor color) {
        object.addProperty("color", color.toString().toLowerCase());
    }

    public void append(ChatMessage message) {
        extras.add(message.object);
        extras.addAll(message.extras);
    }

    @Override
    public String toString() {
        return object.toString();
    }

    public static ChatMessage fromString(String rawMessage) {
        rawMessage = rawMessage.replace("\n", ChatColor.RESET + "\n");
        boolean inMessage = true;
        List<ChatColor> colors = Lists.newArrayList();
        StringBuilder builder = new StringBuilder();
        ChatMessage chatMessage = null;

        for(int i = 0; i < rawMessage.length(); i++) {
            ChatColor color = getColor(rawMessage, i);
            if(color == null) {
                inMessage = true;
                builder.append(rawMessage.charAt(i));
            } else if(inMessage && color != null) {
                inMessage = false;
                ChatMessage current = new ChatMessage(builder.toString());
                for(ChatColor extra : colors) {
                    switch(extra) {
                        case BOLD:
                            current.bold(true);
                            break;
                        case UNDERLINE:
                            current.underlined(true);
                            break;
                        case STRIKE_THROUH:
                            current.strikeThrough(true);
                            break;
                        case OBFUSCATED:
                            current.obfuscated(true);
                            break;
                        case ITALIC:
                            current.italic(true);
                            break;
                        default:
                            current.bold(false);
                            current.underlined(false);
                            current.obfuscated(false);
                            current.italic(false);
                            current.strikeThrough(false);
                            current.color(extra);
                            break;
                    }
                }

                builder.setLength(0);
                colors.clear();
                if(chatMessage == null) {
                    chatMessage = current;
                } else {
                    chatMessage.append(current);
                }
            } else if(color != null) {
                inMessage = false;
                colors.add(color);
                i += 1;
            }
        }

        return chatMessage == null ? new ChatMessage("") : chatMessage;
    }

    private static ChatColor getColor(String message, int index) {
        if(message.length() < index + 2) {
            return null;
        }

        if(message.charAt(index) != ChatColor.getColorChar()) {
            return null;
        }

        return ChatColor.getByChar(message.charAt(index + 1));
    }
}
