package net.toni.tutorialmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.toni.tutorialmod.block.ModBlocks;
import net.toni.tutorialmod.event.BlockBreaked;
import net.toni.tutorialmod.event.PlayerTickHandler;
import net.toni.tutorialmod.fluid.ModFluids;
import net.toni.tutorialmod.item.ModItems;
import net.toni.tutorialmod.networking.ModMessages;
import net.toni.tutorialmod.painting.ModPaintings;
import net.toni.tutorialmod.villager.ModVillagers;
import net.toni.tutorialmod.world.feature.ModConfiguredFeatures;
import net.toni.tutorialmod.world.gen.ModOreGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModConfiguredFeatures.registerConfiguredFeatures();

		BlockBreaked.BlockBreakedEvent();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModVillagers.registerVillagers();
		ModVillagers.registerTrades();

		ModPaintings.registerPaintings();

		ModOreGeneration.generateOres();

		ModMessages.registerC2SPackets();

		ModFluids.register();

//		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
	}
}
