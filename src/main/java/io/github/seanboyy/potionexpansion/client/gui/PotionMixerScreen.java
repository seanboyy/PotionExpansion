package io.github.seanboyy.potionexpansion.client.gui;

import io.github.seanboyy.potionexpansion.objects.containers.inventory.PotionMixerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class PotionMixerScreen extends ContainerScreen<PotionMixerContainer> {
    //TODO: complete class
    public PotionMixerScreen(PotionMixerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
