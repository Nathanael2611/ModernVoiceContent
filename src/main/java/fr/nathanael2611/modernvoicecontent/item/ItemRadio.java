package fr.nathanael2611.modernvoicecontent.item;

import fr.nathanael2611.modernvoicecontent.api.IRadio;
import fr.nathanael2611.modernvoicecontent.proxy.ClientProxy;
import fr.nathanael2611.modernvoicecontent.util.MVCUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRadio extends MVCItem
{

    public ItemRadio()
    {
        super("radio");
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("running"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return isOn(stack) ? 1.0F : 0.0F;
            }
        });
    }

    public static IRadio getRadio(ItemStack stack)
    {
        return new ItemStackRadio(stack);
    }

    private static void setFrequency(ItemStack radio, int frequency)
    {
        if (radio.getItem() instanceof ItemRadio)
        {
            NBTTagCompound compound = MVCUtils.getCompoundTag(radio);
            compound.setInteger("Frequency", frequency);
            radio.setTagCompound(compound);
        }
    }

    private static int getFrequency(ItemStack radio)
    {
        if (radio.getItem() instanceof ItemRadio)
        {
            return MVCUtils.getCompoundTag(radio).getInteger("Frequency");
        }
        return 0;
    }

    private static void powerButton(ItemStack radio)
    {
        if (radio.getItem() instanceof ItemRadio)
        {
            NBTTagCompound compound = MVCUtils.getCompoundTag(radio);
            compound.setBoolean("Running", !compound.getBoolean("Running"));
            radio.setTagCompound(compound);
        }
    }

    private static boolean isOn(ItemStack radio)
    {
        if (radio.getItem() instanceof ItemRadio)
        {
            return MVCUtils.getCompoundTag(radio).getBoolean("Running");
        }
        return false;
    }


    public static class ItemStackRadio implements IRadio
    {

        private ItemStack stack;

        private ItemStackRadio(ItemStack stack)
        {
            this.stack = stack;
        }

        @Override
        public int getFrequency()
        {
            return ItemRadio.getFrequency(this.stack);
        }

        @Override
        public boolean isOn()
        {
            return ItemRadio.isOn(this.stack);
        }

        @Override
        public void setFrequency(int frequency)
        {
            ItemRadio.setFrequency(this.stack, frequency);
        }

        @Override
        public void powerButton()
        {
            ItemRadio.powerButton(this.stack);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(playerIn.isSneaking())
        {
            if(worldIn.isRemote)
            {
                ClientProxy.openRadioGui(handIn);
            }
        }
        else
        {
            getRadio(playerIn.getHeldItem(handIn)).powerButton();
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.addAll(getRadio(stack).getInformations());
    }

}




















