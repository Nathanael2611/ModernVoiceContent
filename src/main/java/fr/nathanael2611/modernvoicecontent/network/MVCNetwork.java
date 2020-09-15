package fr.nathanael2611.modernvoicecontent.network;

import fr.nathanael2611.modernvoicecontent.ModernVoiceContent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MVCNetwork
{

    /**
     * Define the mod-network, we will use it to send packets ! ;D
     */
    private SimpleNetworkWrapper network;

    private static MVCNetwork instance;

    private int nextID = 0;

    public static MVCNetwork getInstance()
    {
        if(instance == null) instance = new MVCNetwork();
        return instance;
    }

    public SimpleNetworkWrapper getNetwork()
    {
        return network;
    }

    /**
     * This method will register all our packets.
     */
    public void registerPackets()
    {
        this.network = NetworkRegistry.INSTANCE.newSimpleChannel(ModernVoiceContent.MOD_ID.toUpperCase());

        this.registerPacket(PacketChangeFrequency.Message.class, PacketChangeFrequency.class, Side.SERVER);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        network.registerMessage(messageHandler, requestMessageType, nextID, side);
        nextID++;
    }

}
