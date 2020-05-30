package io.github.seanboyy.potionexpansion.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.seanboyy.potionexpansion.PotionExpansion;
import io.github.seanboyy.potionexpansion.objects.containers.inventory.PotionMixerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class PotionMixerScreen extends ContainerScreen<PotionMixerContainer> {
    private static final ResourceLocation POTION_MIXER_GUI_TEXTURES = new ResourceLocation(PotionExpansion.MOD_ID, "textures/gui/potion_mixer.png");
    private static final int[] BUBBLE_LENGTHS = new int[] {29, 24, 20, 16, 11, 6, 0};

    public PotionMixerScreen(PotionMixerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), this.xSize / 2F - this.font.getStringWidth(this.title.getFormattedText()) / 2F, 6F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8F, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(POTION_MIXER_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
        int fuel = this.container.getFuel();
        int l = MathHelper.clamp((18 * fuel + 20 - 1) / 20, 0, 18);
        if(l > 0) this.blit(i + 67, j + 67, 176, 29, l, 4);
        int mixTime = this.container.getMixTime();
        if(mixTime > 0) {
            int j1 = (int)(28F * (1F - mixTime / 400F));
            if(j1 > 0) this.blit(i + 88, j + 35, 188, 0, 9, j1);
            j1 = BUBBLE_LENGTHS[mixTime / 2 % BUBBLE_LENGTHS.length];
            if(j1 > 0) this.blit(i + 70, j + 38 + 29 - j1, 176, 29 - j1, 12, j1);
        }
    }
}
