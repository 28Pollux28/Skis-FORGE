package eu.pollux28.skis.block.metalpress;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import eu.pollux28.skis.Skis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class MetalPressScreen extends ContainerScreen<MetalPressContainer>{
    private final ResourceLocation GUI = new ResourceLocation(Skis.MOD_ID, "textures/gui/container/metal_press_gui.png");
    public MetalPressScreen(MetalPressContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        this.renderBg(stack,partialTicks,mouseX,mouseY);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack,mouseX,mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("container."+Skis.MOD_ID+".metal_press.name"), 60, 15, 0xffffff);
//        drawString(matrixStack, Minecraft.getInstance().font, "Progress: " + menu.getPressingProgressPercent()+"%", 90, 10, 0xffffff);
    }


    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (minecraft == null) return;
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        int i = this.leftPos;
        int j = this.topPos;
        int percent = this.menu.getPressingProgressPercent();
        int width = 24;
        int l = percent*width/100;
        this.blit(matrixStack, i + 79, j + 34, 176, 14, l+1, 16);

        int height = 56;
        int k = this.menu.getEnergyPercent()*height/100;
        this.blit(matrixStack,i+11,j+12-k+56,176,31,24,k);


    }

    @Override
    protected void renderTooltip(MatrixStack stack, int x, int y) {
        if(isHovering(12,13,22,54,x,y)){
            List<ITextComponent> text = ImmutableList.of(
                    new TranslationTextComponent("container."+ Skis.MOD_ID+".metal_press.energy",
                            String.format("%d",menu.getEnergy()),
                            String.format("%d", menu.getTotalEnergy()))
            );
            this.renderComponentTooltip(stack,text,x,y);
        }
        super.renderTooltip(stack,x,y);
    }
}
