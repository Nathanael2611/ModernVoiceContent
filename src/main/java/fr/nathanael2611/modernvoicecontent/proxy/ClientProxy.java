package fr.nathanael2611.modernvoicecontent.proxy;

import fr.nathanael2611.modernvoicecontent.block.tileentity.TileEntityRadio;
import fr.nathanael2611.modernvoicecontent.client.gui.GuiRadio;
import fr.nathanael2611.modernvoicecontent.client.gui.GuiRadioBlock;
import fr.nathanael2611.modernvoicecontent.client.gui.GuiRadioItem;
import fr.nathanael2611.modernvoicecontent.client.render.TileEntityRadioRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{

    @Override
    public void preInitialization(FMLPreInitializationEvent event)
    {
        super.preInitialization(event);
        //MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadio.class, new TileEntityRadioRenderer());
    }

    @Override
    public void initialization(FMLInitializationEvent event)
    {
        super.initialization(event);
    }

    @Override
    public void postInitialization(FMLPostInitializationEvent event)
    {
        super.postInitialization(event);
    }


    public static void openRadioGui(EnumHand hand)
    {
        Minecraft.getMinecraft().displayGuiScreen(new GuiRadioItem(hand));
    }

    public static void openRadioGui(TileEntityRadio pos)
    {
        Minecraft.getMinecraft().displayGuiScreen(new GuiRadioBlock(pos));
    }

}
