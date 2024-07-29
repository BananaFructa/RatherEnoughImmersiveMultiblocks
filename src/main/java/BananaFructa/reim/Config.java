package BananaFructa.reim;


import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    public static String[] multiblocksToDisplay;

    public static Configuration config;

    public static void load(File configDirectory) {
        config = new Configuration(new File(configDirectory,"reim.cfg"));
    }

}
