package com.sanmar.challenge1;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LangManager {

    private final JavaPlugin plugin;
    private final Map<String, FileConfiguration> languages = new HashMap<>();
    private String defaultLang = "en_US";


    public LangManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadLanguage("en_US");
        loadLanguage("ru_RU");
    }

    public void setDefaultLang(String lang) {
        this.defaultLang = lang;
    }

    private FileConfiguration loadLanguage(String lang) {
        if (languages.containsKey(lang)) return languages.get(lang);

        File langFile = new File(plugin.getDataFolder(), "lang/" + lang + ".yml");
        if (!langFile.exists()) plugin.saveResource("lang/" + lang + ".yml", false);

        FileConfiguration config = YamlConfiguration.loadConfiguration(langFile);
        languages.put(lang, config);
        return config;
    }

    public String getMessage(String key) {
        return getMessage(defaultLang, key, null);
    }

    public String getMessage(String lang, String key) {
        return getMessage(lang, key, null);
    }

    public String getMessage(String lang, String key, Map<String, String> placeholders) {
        FileConfiguration config = loadLanguage(lang);
        String msg = config.getString("messages." + key, "Message not found!");
        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                msg = msg.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return msg;
    }
}
