package net.toni.tutorialmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.toni.tutorialmod.util.IEntityDataSaver;
import net.toni.tutorialmod.util.ThirstData;

public class DrinkingC2SPacket {
    private static final String MESSAGE_DRINKING_WATER = "message.tutorialmod.drank_water";
    private static final String MEESAGE_NO_WATER_NEARBY = "message.tutorialmod.no_water";

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender ) {
    // Everything here happens ONLY on the Server!
        ServerWorld world = player.getWorld();
        if(isAroundWaterThem(player, world, 2)) {
            // notify the player
            player.sendMessage(Text.translatable(MESSAGE_DRINKING_WATER)
                    .fillStyle(Style.EMPTY.withColor(Formatting.DARK_AQUA)), false);
            // Play the drinking sound
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS,
                    0.5f, world.random.nextFloat() * 0.1F + 0.9F);

            // actually add the water level to the player
            ThirstData.addThirst(((IEntityDataSaver) player), 1);

            // Output how much thirst level the player has
            player.sendMessage(Text.literal("Thirst: " + ((IEntityDataSaver) player).getPersistentData().getInt("thirst"))
                    .fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), true);
        } else {
            // Notify the player
            player.sendMessage(Text.translatable(MEESAGE_NO_WATER_NEARBY)
                    .fillStyle(Style.EMPTY.withColor(Formatting.RED)), false);
            // Output how much thirst level the player has
            player.sendMessage(Text.literal("Thirst: " + ((IEntityDataSaver) player).getPersistentData().getInt("thirst"))
                    .fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), true);
            // Sync thirst

        }
    }

    private static boolean isAroundWaterThem(ServerPlayerEntity player, ServerWorld world, int size) {
        return BlockPos.stream(player.getBoundingBox().expand(size))
                .map(world::getBlockState).filter(state -> state.isOf(Blocks.WATER)).toArray().length > 0;
    }
}
