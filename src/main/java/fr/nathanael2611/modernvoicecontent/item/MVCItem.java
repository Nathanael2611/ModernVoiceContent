package fr.nathanael2611.modernvoicecontent.item;

import fr.nathanael2611.modernvoicecontent.ModernVoiceContent;
import fr.nathanael2611.modernvoicecontent.registry.MVCItems;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class MVCItem extends Item
{

    public MVCItem(String name)
    {
        MVCItems.ITEMS.add(this);
        this.setRegistryName(new ResourceLocation(ModernVoiceContent.MOD_ID, name));
        this.setUnlocalizedName(this.getRegistryName().toString());
    }

}
