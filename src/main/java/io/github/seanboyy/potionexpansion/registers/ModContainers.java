package io.github.seanboyy.potionexpansion.registers;

import io.github.seanboyy.potionexpansion.PotionExpansion;
import io.github.seanboyy.potionexpansion.objects.containers.inventory.PotionMixerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, PotionExpansion.MOD_ID);

    public static final RegistryObject<ContainerType<PotionMixerContainer>> MIXER_CONTAINER = CONTAINERS.register("potion_mixer", () -> IForgeContainerType.create(PotionMixerContainer::new));
}
