package net.dzikoysk.funnyguilds.data.flat;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.FunnyLogger;
import net.dzikoysk.funnyguilds.util.commons.IOUtils;

import java.io.File;

public class FlatPatcher {

    public void patch() {
        File guilds = new File(FunnyGuilds.getInstance().getDataFolder() + File.separator + "guilds");
        File regions = new File(FunnyGuilds.getInstance().getDataFolder() + File.separator + "regions");

        boolean g = guilds.exists();
        boolean r = regions.exists();

        if (g || r) {
            FunnyLogger.update("Updating flat files ...");

            FunnyLogger.update("Scanning files ...");
            int todo = 0;
            int tg = 0;
            int tr = 0;

            File[] guildsList = guilds.listFiles();
            File[] regionsList = regions.listFiles();

            if (g && guildsList != null) {
                tg += guildsList.length;
            }
            if (r && regionsList != null) {
                tr += regionsList.length;
            }

            todo = tg + tr;
            FunnyLogger.update(todo + " files found ...");

            FunnyLogger.update("Updating files ...");
            if (g) {
                guilds.renameTo(Flat.GUILDS);
            }
            if (r) {
                regions.renameTo(Flat.REGIONS);
            }

            guildsList = guilds.listFiles();
            regionsList = regions.listFiles();

            if (guildsList == null || guildsList.length == 0) {
                IOUtils.delete(guilds);
            }
            if (regionsList == null || regionsList.length == 0) {
                IOUtils.delete(regions);
            }

            FunnyLogger.update("Done!");
            FunnyLogger.update("Updated files: " + todo);
        }
    }

}
