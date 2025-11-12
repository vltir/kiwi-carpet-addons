package com.vltir;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;

public class KiwiExtension implements CarpetExtension, ModInitializer {
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
        ExampleCommand.register(dispatcher);
    }

    @Override
    public void onInitialize() {
    }
}
