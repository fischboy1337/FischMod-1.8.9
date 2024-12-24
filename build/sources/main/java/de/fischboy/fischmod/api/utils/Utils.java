package de.fischboy.fischmod.api.utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.fischboy.fischmod.api.utils.interfaces.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Utils implements IMinecraft {

    public static List<String> getHypixelIPs() {
        return Arrays.asList(
                "hypixel.net",
                "mc.hypixel.net",
                "play.hypixel.net",
                "stuck.hypixel.net",
                "lag.hypixel.net",
                "www.hypixel.net",
                "skyblock.hypixel.net",
                "arcade.hypixel.net",
                "bedwars.hypixel.net",
                "murdermystery.hypixel.net",
                "buildbattle.hypixel.net"
        );
    }

    public static boolean isHypixel() {
        return !mc.isSingleplayer() && mc.getCurrentServerData() != null && getHypixelIPs().contains(mc.getCurrentServerData().serverIP);
    }

    public static net.minecraft.util.Timer getTimer() {
        return ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "timer", "field_71428_T");
    }

    public static int getChroma(long speed, long... delay) {
        long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
        return Color.getHSBColor((float) (time % (15000L / speed)) / (15000.0F / (float) speed), 1.0F, 1.0F).getRGB();
    }

    public static String stripColor(final String s) {
        if (s.isEmpty()) {
            return s;
        }
        final char[] array = StringUtils.stripControlCodes(s).toCharArray();
        final StringBuilder sb = new StringBuilder();
        for (final char c : array) {
            if (c < '\u007f' && c > '\u0014') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static List<String> gsl() {
        List<String> lines = new ArrayList<>();
        if (mc.theWorld == null) {
            return lines;
        } else {
            Scoreboard scoreboard = mc.theWorld.getScoreboard();
            if (scoreboard == null) {
                return lines;
            } else {
                ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
                if (objective == null) {
                    return lines;
                } else {
                    Collection<Score> scores = scoreboard.getSortedScores(objective);
                    List<Score> list = new ArrayList();
                    Iterator var5 = scores.iterator();

                    Score score;
                    while (var5.hasNext()) {
                        score = (Score) var5.next();
                        if (score != null && score.getPlayerName() != null && !score.getPlayerName().startsWith("#")) {
                            list.add(score);
                        }
                    }

                    if (list.size() > 15) {
                        scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
                    } else {
                        scores = list;
                    }

                    var5 = scores.iterator();

                    while (var5.hasNext()) {
                        score = (Score) var5.next();
                        ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
                        lines.add(ScorePlayerTeam.formatPlayerName(team, score.getPlayerName()));
                    }

                    return lines;
                }
            }
        }
    }
}
