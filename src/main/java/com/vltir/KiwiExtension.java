package com.vltir;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KiwiExtension implements CarpetExtension, ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("kiwi-carpet-addons");

    public static void noop() {
    }

    static {
        CarpetServer.manageExtension(new KiwiExtension());
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(KiwiSettings.class);
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, final CommandRegistryAccess commandBuildContext) {
        TickCommands.register(dispatcher);
    }

    @Override
    public void onInitialize() {
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        InputStream langFile = KiwiExtension.class.getClassLoader().getResourceAsStream("assets/kiwi-carpet-addons/lang/%s.json".formatted(lang));
        if (langFile == null) {
            return Map.of();
        }
        String jsonData;
        try {
            jsonData = IOUtils.toString(langFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return Map.of();
        }
        return new Gson().fromJson(jsonData, new TypeToken<Map<String, String>>() {
        }.getType());
    }
}
