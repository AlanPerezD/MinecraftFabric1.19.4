package net.toni.tutorialmod.event;

import com.google.common.collect.Streams;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.toni.tutorialmod.networking.ModMessages;

import java.util.List;
import java.util.stream.Stream;

public class BlockBreaked {

    public static void Message() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (!world.isClient()) {

                BlockState state = world.getBlockState(pos);

                if (state.isToolRequired() && !player.isSpectator() &&
                        player.getMainHandStack().isEmpty()) {
//                    player.sendMessage(Text.literal("Block Punched"));
//                    player.sendMessage(Text.literal(pos.toShortString()));
//                    player.sendMessage(Text.literal(state.toString()));
//                    player.sendMessage(Text.literal(player.getUuidAsString()));
//                    player.sendMessage(Text.literal(direction.getName()));
                }


            }
            return ActionResult.PASS;
        });
    }

    public static void BlockBreakedEvent() {

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            Block block = state.getBlock();
            if (!world.isClient() && block == Blocks.SPRUCE_LOG && getDownBlock(world, player, pos)) {
                BlockPos.iterateOutwards(pos, 1, 0, 1).forEach(blockPos -> world.setBlockState(blockPos, Blocks.COBBLESTONE.getDefaultState()));
            }
        });
    }

    private static boolean getDownBlock(World world, PlayerEntity player, BlockPos pos) {
        // check if the block up of Pos is a spruce_log
        return BlockPos.stream(pos, pos.down()).map(world::getBlockState).filter(statePos -> statePos.isOf(Blocks.SPRUCE_LOG)).toArray().length > 0;
    }

    private static void printBlocks() {

    }
}
