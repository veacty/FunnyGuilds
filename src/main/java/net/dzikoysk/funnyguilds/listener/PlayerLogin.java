package net.dzikoysk.funnyguilds.listener;

import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.basic.util.GuildRegex;
import net.dzikoysk.funnyguilds.basic.util.UserUtils;
import net.dzikoysk.funnyguilds.system.ban.BanUtils;
import net.dzikoysk.funnyguilds.util.commons.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLogin implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (Bukkit.hasWhitelist()) {
            return;
        }
        
        String name = event.getPlayer().getName();
        if (name.length() < 2) {
            event.disallow(Result.KICK_OTHER, StringUtils.colored("&cNick jest za krotki!"));
        }
        
        if (name.length() > 16) {
            event.disallow(Result.KICK_OTHER, StringUtils.colored("&cNick jest za dlugi!"));
        }
        
        if (!name.matches(GuildRegex.LETTERS_DIGITS_UNDERSCORE.getPattern())) {
            event.disallow(Result.KICK_OTHER, StringUtils.colored("&cNick zawiera niedozwolone znaki!"));
        }
        
        if (!UserUtils.playedBefore(event.getPlayer().getName())) {
            return;
        }

        User user = User.get(event.getPlayer());
        BanUtils.checkIfBanShouldExpire(user);

        if (!user.isBanned()) {
            return;
        }
        
        event.disallow(Result.KICK_BANNED, BanUtils.getBanMessage(user));
    }

}
