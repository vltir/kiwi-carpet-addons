package com.vltir.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.vltir.KiwiSettings;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TickCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TickCommand.class)
public abstract class TickCommandMixin {
    @ModifyVariable(
            method = "executeFreeze",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/server/MinecraftServer;getTickManager()Lnet/minecraft/server/ServerTickManager;"
            ),
            argsOnly = true
    )
    private static boolean injected(boolean freeze, @Local ServerTickManager serverTickManager) {
        return freeze && (!KiwiSettings.tickFreezeToggle || !serverTickManager.isFrozen());
    }

    /*@Inject(method = "register", at = @At("TAIL"))
    private static void onRegister(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo ci) {
        CommandNode<ServerCommandSource> tickNode = dispatcher.getRoot().getChild("tick");
        if (tickNode == null) return;

        CommandNode<ServerCommandSource> sprintNode = tickNode.getChild("sprint");
        if (sprintNode == null) return;

        // no arguments -> sprint stop

        CommandNode<ServerCommandSource> newSprint = CommandManager.literal("sprint").executes(
                ctx -> TickCommandInvoker.callExecuteStopSprint(ctx.getSource())
        ).build();

        sprintNode.getChildren().forEach(newSprint::addChild);
        tickNode.addChild(newSprint);

        sprintNode.addChild(CommandManager.literal("").executes(
                ctx -> TickCommandInvoker.callExecuteStopSprint(ctx.getSource())
        ).build());

        // warp as sprint copy

        CommandNode<ServerCommandSource> warpNode = CommandManager.literal("warp")
                .executes(newSprint.getCommand())
                .build();
        newSprint.getChildren().forEach(warpNode::addChild);
        tickNode.addChild(warpNode);
    }*/

}
