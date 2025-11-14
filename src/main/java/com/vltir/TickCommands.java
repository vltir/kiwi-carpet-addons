package com.vltir;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;

import static com.vltir.KiwiExtension.LOGGER;
import static net.minecraft.server.command.CommandManager.*;

public class TickCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        registerTickSprintEasyInterrupt(dispatcher);
        registerTickWarpAlias(dispatcher);
    }

    public static void registerTickSprintEasyInterrupt(CommandDispatcher<ServerCommandSource> dispatcher) {
        CommandNode<ServerCommandSource> tickNode, sprintNode, stopNode;
        if ((tickNode = dispatcher.getRoot().getChild("tick")) == null ||
                (sprintNode = tickNode.getChild("sprint")) == null ||
                (stopNode = sprintNode.getChild("stop")) == null) {
            LOGGER.warn("tick sprint stop command not found");
            return;
        }

        LiteralCommandNode<ServerCommandSource> newSprintNode = literal("sprint")
                .executes(ctx -> {
                    if (!KiwiSettings.tickSprintEasyInterrupt) {
                        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create();
                    }
                    return stopNode.getCommand().run(ctx);
                })
                .build();
        sprintNode.getChildren().forEach(newSprintNode::addChild);
        tickNode.addChild(newSprintNode);
    }

    public static void registerTickWarpAlias(CommandDispatcher<ServerCommandSource> dispatcher) {
        CommandNode<ServerCommandSource> tickNode, sprintNode;
        if ((tickNode = dispatcher.getRoot().getChild("tick")) == null ||
                (sprintNode = tickNode.getChild("sprint")) == null) {
            LOGGER.warn("tick sprint command not found");
            return;
        }

        tickNode.addChild(literal("warp")
                .requires(source -> KiwiSettings.tickWarpAlias)
                .executes(sprintNode.getCommand())
                .redirect(sprintNode)
                .build()
        );
    }
}
