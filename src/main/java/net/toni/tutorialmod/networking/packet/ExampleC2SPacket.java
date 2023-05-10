package net.toni.tutorialmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;


public class ExampleC2SPacket {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender ) {
//         Only server events
         EntityType.COW.spawn((ServerWorld) player.world,null,null,player, player.getBlockPos(),
                SpawnReason.TRIGGERED, true, false);
//         trigger this in the class
//         ClientPlayNetworking.send(ModMessages.EXAMPLE_ID, PacketByteBufs.create());
    }
}
