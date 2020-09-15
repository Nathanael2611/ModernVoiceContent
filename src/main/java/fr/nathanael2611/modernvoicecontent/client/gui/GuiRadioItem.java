package fr.nathanael2611.modernvoicecontent.client.gui;

import fr.nathanael2611.modernvoicecontent.item.ItemRadio;
import fr.nathanael2611.modernvoicecontent.network.MVCNetwork;
import fr.nathanael2611.modernvoicecontent.network.PacketChangeFrequency;
import fr.nathanael2611.modularvoicechat.util.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;

public class GuiRadioItem extends GuiRadio
{

    private EnumHand hand;

    public GuiRadioItem(EnumHand hand)
    {
        super(ItemRadio.getRadio(Minecraft.getMinecraft().player.getHeldItem(hand)).getFrequency());
        this.hand = hand;
    }

    @Override
    void changeFrequency(int frequency)
    {
        MVCNetwork.getInstance().getNetwork().sendToServer(new PacketChangeFrequency(frequency, this.hand));
    }

}
