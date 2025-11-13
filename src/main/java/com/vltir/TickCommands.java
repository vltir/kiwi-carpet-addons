package com.vltir;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;

import static com.vltir.KiwiExtension.LOGGER;
import static net.minecraft.server.command.CommandManager.*;

public class TickCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        registerTickSprintEasyInterrupt(dispatcher);
    }

    public static void registerTickSprintEasyInterrupt(CommandDispatcher<ServerCommandSource> dispatcher) {
        CommandNode<ServerCommandSource> tickNode = dispatcher.getRoot().getChild("tick");
        if (tickNode == null) {
            LOGGER.warn("tick command not found");
            return;
        }
        CommandNode<ServerCommandSource> sprintNode = tickNode.getChild("sprint");
        if (sprintNode == null) {
            LOGGER.warn("tick sprint command not found");
            return;
        }
        CommandNode<ServerCommandSource> stopNode = sprintNode.getChild("stop");
        if (stopNode == null) {
            LOGGER.warn("tick sprint stop command not found");
            return;
        }
        sprintNode.addChild(literal("")
                .requires(source -> KiwiSettings.tickSprintEasyInterrupt)
                .executes(stopNode.getCommand())
                .build()
        );
        LiteralCommandNode<ServerCommandSource> newSprintNode = literal("sprint")
                .requires(source -> KiwiSettings.tickSprintEasyInterrupt)
                .executes(stopNode.getCommand())
                .build();
        sprintNode.getChildren().forEach(newSprintNode::addChild);
        tickNode.addChild(newSprintNode);
    }
}
