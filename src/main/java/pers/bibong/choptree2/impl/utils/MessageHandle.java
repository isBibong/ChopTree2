package pers.bibong.choptree2.impl.utils;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import pers.bibong.choptree2.ChopTree2;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHandle {
    //控制台警告訊息
    public static void WARN(String msg) {
        ChopTree2.inst().getLogger().log(Level.WARNING, toColor(msg));
    }

    //控制台一般訊息
    public static void INFO(String msg) {
        ChopTree2.inst().getLogger().log(Level.INFO, toColor(msg));
    }

    //廣播
    public static void broadcast(String msg) {
        ChopTree2.inst().getServer().broadcast(Component.text(toColor(msg)));
    }

    public static @NotNull String replace(@NotNull String s1, String s2, Object s3) {
        if (s1.contains(s2)) return s1.replace(s2, s3.toString());
        return s1;
    }

    public static @NotNull String toColor(String s1) {
        String msg = MessageHandle.toRGB(s1);

        if (msg.contains("&")) {
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }

        return msg;
    }

    public static String toRGB(final @NotNull String message) {
        String msg = message;

        final Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]){6}>");
        Matcher       matcher    = hexPattern.matcher(msg);

        while (matcher.find()) {
            final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String    before   = msg.substring(0, matcher.start());
            final String    after    = msg.substring(matcher.end());
            msg     = before + hexColor + after;
            matcher = hexPattern.matcher(msg);
        }

        return msg;
    }
}
