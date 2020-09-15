package fr.nathanael2611.modernvoicecontent.registry;

import com.google.common.collect.Lists;
import fr.nathanael2611.modernvoicecontent.item.ItemRadio;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;

public class MVCItems
{

    public static final List<Item> ITEMS = Lists.newArrayList();

    public static final Item RADIO = new ItemRadio();

    @SubscribeEvent
    public void onRegisterItem(RegistryEvent.Register<Item > event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item item : ITEMS)
        {
            registry.register(item);
        }
        for (Block block : MVCBlocks.BLOCKS)
        {
            registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRegisterModel(ModelRegistryEvent event)
    {
        for (Item item : ITEMS)
        {
            registerItemModel(item);
        }
        for (Block block : MVCBlocks.BLOCKS)
        {
            registerItemModel(Item.getItemFromBlock(block));
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Item item)
    {
        if(item.getHasSubtypes())
        {
            NonNullList<ItemStack> items = NonNullList.create();
            item.getSubItems(item.getCreativeTab(), items);
            for(ItemStack stack : items) ModelLoader.setCustomModelResourceLocation(item, stack.getMetadata(), new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    /**
     *  Register an item with an metadata and a variant
     */
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Item item, int metadata, String variant)
    {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
    }

}
