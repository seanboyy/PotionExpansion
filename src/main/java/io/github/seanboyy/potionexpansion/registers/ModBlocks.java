package io.github.seanboyy.potionexpansion.registers;

import io.github.seanboyy.potionexpansion.PotionExpansion;
import io.github.seanboyy.potionexpansion.objects.blocks.ModBrewingStandBlock;
import io.github.seanboyy.potionexpansion.objects.blocks.PotionMixerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, PotionExpansion.MOD_ID);
    public static final DeferredRegister<Block> OVERRIDE_BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, "minecraft");

    public static final RegistryObject<Block> MIXER = BLOCKS.register("potion_mixer", () -> new PotionMixerBlock(Block.Properties.from(Blocks.CAULDRON)));

    public static final RegistryObject<Block> OVERRIDE_BREWING_STAND = OVERRIDE_BLOCKS.register("brewing_stand", () -> new ModBrewingStandBlock(Block.Properties.from(Blocks.BREWING_STAND)));
}
