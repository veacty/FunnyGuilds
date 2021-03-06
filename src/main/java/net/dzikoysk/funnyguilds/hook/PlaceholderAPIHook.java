package net.dzikoysk.funnyguilds.hook;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLogger;
import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.basic.util.GuildUtils;
import net.dzikoysk.funnyguilds.basic.util.UserUtils;
import net.dzikoysk.funnyguilds.data.Messages;
import net.dzikoysk.funnyguilds.data.Settings;
import net.dzikoysk.funnyguilds.data.configs.MessagesConfig;
import net.dzikoysk.funnyguilds.data.configs.PluginConfig;
import net.dzikoysk.funnyguilds.util.IntegerRange;
import net.dzikoysk.funnyguilds.util.Parser;
import net.dzikoysk.funnyguilds.util.RandomUtils;
import net.dzikoysk.funnyguilds.util.commons.StringUtils;
import org.bukkit.entity.Player;

import java.util.Locale;

public class PlaceholderAPIHook {

    public static void initPlaceholderHook() {
        new FunnyGuildsPlaceholder().register();
        FunnyLogger.info("PlaceholderAPI hook has been enabled!");
    }
    
    public static String replacePlaceholders(Player user, String base) {
        return PlaceholderAPI.setPlaceholders(user, base);
    }
    
    private static class FunnyGuildsPlaceholder extends PlaceholderExpansion {

        @Override
        public String onPlaceholderRequest(Player player, String identifier) {
            PluginConfig config = Settings.getConfig();
            MessagesConfig messages = Messages.getInstance();
            
            if (player == null) {
                return "";
            }
            
            User u = User.get(player);
            if (u == null) {
                return "";
            }
            
            Guild g = u.getGuild();
            
            switch (identifier.toLowerCase()) {
                case "guilds":
                    return String.valueOf(GuildUtils.getGuilds().size());
                case "users":
                    return String.valueOf(UserUtils.getUsers().size());
                case "deaths":
                    return String.valueOf(u.getRank().getDeaths());
                case "kdr":
                    return String.format(Locale.US, "%.2f", u.getRank().getKDR());
                case "kills":
                    return String.valueOf(u.getRank().getKills());
                case "points-format":
                    return IntegerRange.inRange(u.getRank().getPoints(), config.pointsFormat, "POINTS").replace("{POINTS}", String.valueOf(u.getRank().getPoints()));
                case "points":
                    return String.valueOf(u.getRank().getPoints());
                case "position":
                    return String.valueOf(u.getRank().getPosition());
                case "g-allies":
                    return g == null ? "0" : String.valueOf(g.getAllies().size());
                case "g-deaths":
                    return g == null ? "0" : String.valueOf(g.getRank().getDeaths());
                case "g-deputies":
                    return g == null ? messages.gDeputiesNoValue : (g.getDeputies().isEmpty() ? messages.gDeputiesNoValue : StringUtils.toString(UserUtils.getNames(g.getDeputies()), false));
                case "g-deputy":
                    return g == null ? messages.gDeputyNoValue : (g.getDeputies().isEmpty() ? messages.gDeputyNoValue : g.getDeputies().get(RandomUtils.RANDOM_INSTANCE.nextInt(g.getDeputies().size())).getName());
                case "g-kdr":
                    return g == null ? "0.00" : String.format(Locale.US, "%.2f", g.getRank().getKDR());
                case "g-kills":
                    return g == null ? "0" : String.valueOf(g.getRank().getKills());
                case "g-lives":
                    return g == null ? "0" : String.valueOf(g.getLives());
                case "g-members-all":
                    return g == null ? "0" : String.valueOf(g.getMembers().size());
                case "g-members-online":
                    return g == null ? "0" : String.valueOf(g.getOnlineMembers().size());
                case "g-name":
                    return g == null ? messages.gNameNoValue : g.getName();
                case "g-owner":
                    return g == null ? messages.gOwnerNoValue : g.getOwner().getName();
                case "g-points-format":
                    return g == null ? IntegerRange.inRange(0, config.pointsFormat, "POINTS").replace("{POINTS}", "0") : IntegerRange.inRange(g.getRank().getPoints(), config.pointsFormat, "POINTS").replace("{POINTS}", String.valueOf(g.getRank().getPoints()));
                case "g-points":
                    return g == null ? "0" : String.valueOf(g.getRank().getPoints());
                case "g-position":
                    return g == null ? "0" : String.valueOf(g.getRank().getPosition());
                case "g-region-size":
                    return config.regionsEnabled ? (g == null ? messages.gRegionSizeNoValue : String.valueOf(g.getRegionData().getSize())) : messages.gRegionSizeNoValue;
                case "g-tag":
                    return g == null ? messages.gTagNoValue : g.getTag();
                case "g-validity":
                    return g == null ? messages.gValidityNoValue : config.dateFormat.format(g.getValidityDate());
                default:
                    return Parser.parseRank("{" + identifier.toUpperCase() + "}");
            }
        }

        @Override
        public String getAuthor() {
            return "FunnyGuilds Team";
        }

        @Override
        public String getIdentifier() {
            return "funnyguilds";
        }

        @Override
        public String getPlugin() {
            return "FunnyGuilds";
        }

        @Override
        public String getVersion() {
            return FunnyGuilds.getInstance().getDescription().getVersion();
        }
    }
    
}
