package fr.nathanael2611.modernvoicecontent.network;

import fr.nathanael2611.modernvoicecontent.block.tileentity.TileEntityRadio;
import fr.nathanael2611.modernvoicecontent.item.ItemRadio;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketChangeFrequency implements IMessage
{

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_BLOCK = 1;

    private int radioType;
    private int frequency;
    private int x, y, z;
    private EnumHand hand;

    public PacketChangeFrequency()
    {
    }

    public PacketChangeFrequency(int frequency, EnumHand hand)
    {
        this.radioType = TYPE_ITEM;
        this.frequency = frequency;
        this.hand = hand;
    }

    public PacketChangeFrequency(int frequency, int x, int y, int z)
    {
        this.radioType = TYPE_BLOCK;
        this.frequency = frequency;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.radioType = buf.readInt();
        this.frequency = buf.readInt();
        if(this.radioType == TYPE_ITEM)
        {
            this.hand = buf.readInt() == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        }
        else
        {
            this.x = buf.readInt();
            this.y = buf.readInt();
            this.z = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.radioType);
        buf.writeInt(this.frequency);
        if(this.radioType == TYPE_ITEM)
        {
            buf.writeInt(this.hand == EnumHand.OFF_HAND ? 1 : 0);
        }
        else
        {
            buf.writeInt(this.x);
            buf.writeInt(this.y);
            buf.writeInt(this.z);
        }
    }


    public static class Message implements IMessageHandler<PacketChangeFrequency, IMessage>
    {
        public Message()
        {
        }

        public IMessage onMessage(PacketChangeFrequency message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            if(message.radioType == TYPE_ITEM)
            {
                ItemRadio.getRadio(player.getHeldItem(message.hand)).setFrequency(message.frequency);
            }
            else
            {
                if (player.getDistance(message.x, message.y, message.z) < 6)
                {
                    TileEntity te = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
                    if(te instanceof TileEntityRadio)
                    {
                        TileEntityRadio radio = (TileEntityRadio) te;
                        radio.setFrequency(message.frequency);
                    }
                }
            }
            ctx.getServerHandler().player.sendMessage(new TextComponentString("§7Fréquence radio définie à: " + message.frequency));
            return null;
        }
    }

}
