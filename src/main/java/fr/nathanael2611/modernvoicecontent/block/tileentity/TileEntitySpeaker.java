package fr.nathanael2611.modernvoicecontent.block.tileentity;

import fr.nathanael2611.modernvoicecontent.ModernVoiceContent;
import fr.nathanael2611.modernvoicecontent.api.IActivable;
import fr.nathanael2611.modernvoicecontent.block.BlockSpeaker;
import fr.nathanael2611.modularvoicechat.api.VoiceDispatchEvent;
import fr.nathanael2611.modularvoicechat.api.VoiceProperties;
import fr.nathanael2611.modularvoicechat.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public abstract class TileEntitySpeaker extends TileEntity implements IActivable, ITickable
{

    private int distance;

    public TileEntitySpeaker()
    {
        this.distance = 16;
    }

    @Override
    public abstract void update();

    @Override
    public boolean isOn()
    {
        IBlockState state = getState();
        return state.getBlock() instanceof BlockSpeaker && state.getValue(BlockSpeaker.ACTIVATE);
    }

    @Override
    public void powerButton()
    {
        this.world.setBlockState(this.pos, this.getState().withProperty(BlockSpeaker.ACTIVATE, !isOn()));
        this.world.setTileEntity(this.pos, this);
    }

    public IBlockState getState()
    {
        return this.world.getBlockState(pos);
    }

    public void setDistance(int distance)
    {
        this.distance = distance;
        this.save();
    }

    public void makeDispatch(VoiceDispatchEvent event)
    {
        EntityPlayerMP speaker = event.getSpeaker();
        for (EntityPlayerMP player : event.getVoiceServer().getConnectedPlayers())
        {
            double maxDistance = this.distance;
            double distanceBetween = player.getDistance(pos.getX(), pos.getY(), pos.getZ());
            if(distanceBetween <= maxDistance)
            {
                int volume = 100 - (int) Helpers.getPercent(distanceBetween, maxDistance);
                event.dispatchTo(speaker, volume, VoiceProperties.builder().with("IsRadio", true).build());
            }
        }
    }

    public void save()
    {
        this.markDirty();
        if(this.world != null)
        {
            IBlockState state = getState();
            this.world.notifyBlockUpdate(this.pos, state, state, 3);
            this.world.setTileEntity(this.pos, this);
        }
    }

    public int getDistance()
    {
        return distance;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }
}
