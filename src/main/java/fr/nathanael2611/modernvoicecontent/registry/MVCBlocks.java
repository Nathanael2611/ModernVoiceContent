package fr.nathanael2611.modernvoicecontent.registry;

import com.google.common.collect.Lists;
import fr.nathanael2611.modernvoicecontent.block.BlockRadio;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;

public class MVCBlocks
{

    public static final List<Block> BLOCKS = Lists.newArrayList();

    public static final Block RADIO_BLOCK = new BlockRadio();

    @SubscribeEvent
    public void onRegisterItem(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        for (Block block : BLOCKS)
        {
            registry.register(block);
        }
    }

}
