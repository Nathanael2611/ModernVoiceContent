package fr.nathanael2611.modernvoicecontent.client.render;

import fr.nathanael2611.modernvoicecontent.block.tileentity.TileEntityRadio;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

import java.awt.*;

public class TileEntityRadioRenderer extends TileEntitySpecialRenderer<TileEntityRadio>
{

    @Override
    public void render(TileEntityRadio te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(te.isOn())
        {
            Minecraft mc = Minecraft.getMinecraft();

            final float factor = 0.01f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 0.5 + (getFontRenderer().FONT_HEIGHT / 2) * factor, z + 0.5);
            GlStateManager.glNormal3f(1.0f, 1.0f, 1.0f);
            GlStateManager.scale(-factor * 1, -factor * 1, -factor * 1);
            //GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            //GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
            EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockHorizontal.FACING);
            GlStateManager.translate(0, -22, 0);
            if(facing == EnumFacing.NORTH)
            {
            }
            else if(facing == EnumFacing.EAST)
            {
                GlStateManager.rotate(-90, 0, 1, 0);
            }
            else if(facing == EnumFacing.WEST)
            {
                GlStateManager.rotate(90, 0, 1, 0);
            }
            else if(facing == EnumFacing.SOUTH)
            {
                GlStateManager.rotate(-180, 0, 1, 0);
            }
            GlStateManager.translate(0, 0, 48);
            GlStateManager.translate(-15, 0, 0);

            GlStateManager.glNormal3f(0.0F, 0.0F, -0.010416667F);
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.disableLighting();
            //GlStateManager.translate(0, - (lines / 2) * getFontRenderer().FONT_HEIGHT, 0);
            this.setLightmapDisabled(true);
            //RenderHelpers.drawCenteredStringMultiLine(te.getText(), 100, 0, 0 ,te.getColor());
            GlStateManager.translate(0, -0.3, -0.1);
            mc.fontRenderer.drawString(te.getFrequency() + "Hz", 0, 0, Color.BLACK.getRGB());
            //Gui.drawRect(0 - 100 / 2, 0, 100 / 2, lines * getFontRenderer().FONT_HEIGHT, new Color(0, 0, 0, 100).getRGB());
            this.setLightmapDisabled(false);
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();


        }
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }
}
