package fr.nathanael2611.modernvoicecontent;

import fr.nathanael2611.modernvoicecontent.block.tileentity.TileEntityRadio;
import fr.nathanael2611.modernvoicecontent.features.FrequencyManager;
import fr.nathanael2611.modernvoicecontent.network.MVCNetwork;
import fr.nathanael2611.modernvoicecontent.proxy.CommonProxy;
import fr.nathanael2611.modernvoicecontent.registry.MVCBlocks;
import fr.nathanael2611.modernvoicecontent.registry.MVCItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ModernVoiceContent.MOD_ID, name = ModernVoiceContent.MOD_NAME)
public class ModernVoiceContent
{

    public static final String MOD_NAME = "Nathanael2611's Modern Voice Content";
    public static final String MOD_ID = "nmvc";

    @SidedProxy(clientSide = "fr.nathanael2611.modernvoicecontent.proxy.ClientProxy",
            serverSide = "fr.nathanael2611.modernvoicecontent.proxy.ServerProxy")
    public static CommonProxy PROXY;

    @Mod.Instance
    public static ModernVoiceContent INSTANCE;

    private FrequencyManager frequencyManager;

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent event)
    {
        PROXY.preInitialization(event);
        MVCNetwork.getInstance().registerPackets();
        GameRegistry.registerTileEntity(TileEntityRadio.class, new ResourceLocation(MOD_ID, ".TileEntityRadio"));
        MinecraftForge.EVENT_BUS.register(new MVCItems());
        MinecraftForge.EVENT_BUS.register(new MVCBlocks());
        MinecraftForge.EVENT_BUS.register(this.frequencyManager = new FrequencyManager(this));
    }

    @Mod.EventHandler
    public void initialization(FMLInitializationEvent event)
    {
        PROXY.initialization(event);
    }

    @Mod.EventHandler
    public void postInitialization(FMLPostInitializationEvent event)
    {
        PROXY.postInitialization(event);
    }

    public FrequencyManager getFrequencyManager()
    {
        return frequencyManager;
    }
}
