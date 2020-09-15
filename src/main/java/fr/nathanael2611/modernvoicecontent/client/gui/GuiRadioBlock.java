package fr.nathanael2611.modernvoicecontent.client.gui;

import fr.nathanael2611.modernvoicecontent.block.tileentity.TileEntityRadio;
import fr.nathanael2611.modernvoicecontent.item.ItemRadio;
import fr.nathanael2611.modernvoicecontent.network.MVCNetwork;
import fr.nathanael2611.modernvoicecontent.network.PacketChangeFrequency;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class GuiRadioBlock extends GuiRadio
{

    private BlockPos pos;

    public GuiRadioBlock(TileEntityRadio radio)
    {
        super(radio.getFrequency());
        this.pos = radio.getPos();
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    void changeFrequency(int frequency)
    {
        MVCNetwork.getInstance().getNetwork().sendToServer(new PacketChangeFrequency(
                frequency, this.pos.getX(), this.pos.getY(), this.pos.getZ()
        ));
    }

}
