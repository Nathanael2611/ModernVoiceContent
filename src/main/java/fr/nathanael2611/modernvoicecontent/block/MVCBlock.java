package fr.nathanael2611.modernvoicecontent.block;

import fr.nathanael2611.modernvoicecontent.ModernVoiceContent;
import fr.nathanael2611.modernvoicecontent.registry.MVCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class MVCBlock extends Block
{

    public MVCBlock(String name, Material material)
    {
        super(material);
        MVCBlocks.BLOCKS.add(this);
        this.setRegistryName(new ResourceLocation(ModernVoiceContent.MOD_ID, name));
        this.setUnlocalizedName(this.getRegistryName().toString());

    }

}
