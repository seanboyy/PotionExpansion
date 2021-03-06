package io.github.seanboyy.potionexpansion.registers;

import io.github.seanboyy.potionexpansion.PotionExpansion;
import io.github.seanboyy.potionexpansion.objects.tileentity.ModBrewingStandTileEntity;
import io.github.seanboyy.potionexpansion.objects.tileentity.PotionMixerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, PotionExpansion.MOD_ID);

    public static final RegistryObject<TileEntityType<ModBrewingStandTileEntity>> BREWING_STAND_TILE_ENTITY = TILE_ENTITIES.register("brewing_stand", () -> TileEntityType.Builder.create(ModBrewingStandTileEntity::new, ModBlocks.OVERRIDE_BREWING_STAND.get()).build(null));
    public static final RegistryObject<TileEntityType<PotionMixerTileEntity>> POTION_MIXER = TILE_ENTITIES.register("potion_mixer", () -> TileEntityType.Builder.create(PotionMixerTileEntity::new, ModBlocks.POTION_MIXER.get()).build(null));
}
