package fr.nathanael2611.modernvoicecontent.block.tileentity;

import fr.nathanael2611.modernvoicecontent.ModernVoiceContent;
import fr.nathanael2611.modernvoicecontent.api.IRadio;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRadio extends TileEntitySpeaker implements IRadio
{

    private int frequency;

    public TileEntityRadio()
    {
        this.frequency = 0;
    }

    @Override
    public void update()
    {
        ModernVoiceContent.INSTANCE.getFrequencyManager().addRadio(this);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.frequency = compound.getInteger("Frequency");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("Frequency", this.frequency);
        return compound;
    }

    @Override
    public int getFrequency()
    {
        return this.frequency;
    }

    @Override
    public void setFrequency(int frequency)
    {
        this.frequency = frequency;
        setDistance(90);
        this.save();
    }


}
