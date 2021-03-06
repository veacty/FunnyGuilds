package net.dzikoysk.funnyguilds.system.war;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.data.Messages;
import net.dzikoysk.funnyguilds.data.configs.MessagesConfig;
import net.dzikoysk.funnyguilds.util.commons.StringUtils;
import net.dzikoysk.funnyguilds.util.commons.TimeUtils;
import org.bukkit.entity.Player;

public final class WarUtils {

    public static void message(Player player, int i, Object... values) {
        MessagesConfig m = Messages.getInstance();
        String message = null;
        
        switch (i) {
            case 0:
                message = m.warHasNotGuild;
                break;
            case 1:
                message = m.warAlly;
                break;
            case 2:
                message = m.warWait;
                message = StringUtils.replace(message, "{TIME}", TimeUtils.getDurationBreakdown((long) values[0]));
                break;
            case 3:
                message = m.warAttacker;
                message = StringUtils.replace(message, "{ATTACKED}", ((Guild) values[0]).getTag());
                break;
            case 4: 
                message = m.warAttacked;
                message = StringUtils.replace(message, "{ATTACKER}", ((Guild) values[0]).getTag());
                break;
        }
        
        player.sendMessage(message);
    }

    public static String getWinMessage(Guild conqueror, Guild loser) {
        return Messages.getInstance().warWin
                .replace("{WINNER}", conqueror.getTag())
                .replace("{LOSER}", loser.getTag());
    }

    public static String getLoseMessage(Guild conqueror, Guild loser) {
        return Messages.getInstance().warLose
                .replace("{WINNER}", conqueror.getTag())
                .replace("{LOSER}", loser.getTag());
    }

    public static String getBroadcastMessage(Guild conqueror, Guild loser) {
        return Messages.getInstance().broadcastWar
                .replace("{WINNER}", conqueror.getTag())
                .replace("{LOSER}", loser.getTag());
    }

    private WarUtils() {}
}
