package fr.nathanael2611.modernvoicecontent.features;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.nathanael2611.modernvoicecontent.ModernVoiceContent;
import fr.nathanael2611.modernvoicecontent.api.IRadio;
import fr.nathanael2611.modernvoicecontent.block.BlockRadio;
import fr.nathanael2611.modernvoicecontent.block.tileentity.TileEntityRadio;
import fr.nathanael2611.modernvoicecontent.item.ItemRadio;
import fr.nathanael2611.modularvoicechat.api.VoiceDispatchEvent;
import fr.nathanael2611.modularvoicechat.api.VoiceProperties;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FrequencyManager
{

    private ModernVoiceContent mod;
    private final HashMap<EntityPlayerMP, List<Integer>> FREQUENCIES = Maps.newHashMap();
    private final ConcurrentHashMap<BlockPos, TileEntityRadio> RADIOS_BLOCK_FREQUENCIES = new ConcurrentHashMap<>();
    private long lastFrequencyUpdate = -1;

    public FrequencyManager(ModernVoiceContent mod)
    {
        this.mod = mod;
    }

    private void updateFrequencies(EntityPlayerMP player, List<Integer> frequencies)
    {
        this.FREQUENCIES.put(player, frequencies);
    }

    public List<Integer> getActiveFrequencies(EntityPlayerMP player)
    {
        this.FREQUENCIES.putIfAbsent(player, Lists.newArrayList());
        return this.FREQUENCIES.get(player);
    }

    public boolean isListening(EntityPlayerMP playerMP, int frequency)
    {
        return this.getActiveFrequencies(playerMP).contains(frequency);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        long time = System.currentTimeMillis();
        if(this.lastFrequencyUpdate == -1)
        {
            this.lastFrequencyUpdate = time - 1000;
        }
        if(time - this.lastFrequencyUpdate > 1000)
        {
            for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
            {
                List<Integer> playerFrequencies = Lists.newArrayList();
                for (int i = 0; i < player.inventory.getSizeInventory(); i++)
                {
                    ItemStack stack = player.inventory.getStackInSlot(i);
                    if(stack.getItem() instanceof ItemRadio)
                    {
                        IRadio radio = ItemRadio.getRadio(stack);
                        if(radio.isOn())
                        {
                            playerFrequencies.add(radio.getFrequency());
                        }
                    }
                }
                this.updateFrequencies(player, playerFrequencies);
            }
            for (BlockPos pos : this.RADIOS_BLOCK_FREQUENCIES.keySet())
            {
                TileEntityRadio radio = this.RADIOS_BLOCK_FREQUENCIES.get(pos);
                if(radio == null || !(radio.getState().getBlock() instanceof BlockRadio))
                {
                    this.RADIOS_BLOCK_FREQUENCIES.remove(pos);
                }
            }
        }

    }

    public void addRadio(TileEntityRadio radio)
    {
        if(radio != null)
        {
            this.RADIOS_BLOCK_FREQUENCIES.put(radio.getPos(), radio);
        }
    }

    @SubscribeEvent
    public void onVoiceDispatch(VoiceDispatchEvent event)
    {
        EntityPlayerMP speaker = event.getSpeaker();
        for (EntityPlayerMP player : event.getVoiceServer().getConnectedPlayers())
        {
            if(player != speaker)
            {
                for (Integer activeFrequency : this.mod.getFrequencyManager().getActiveFrequencies(speaker))
                {
                    if(this.isListening(player, activeFrequency))
                    {
                        event.dispatchTo(player, 100, VoiceProperties.builder().with("IsRadio", true).build());
                        break;
                    }
                }
            }
        }
        for (TileEntityRadio value : this.RADIOS_BLOCK_FREQUENCIES.values())
        {
            if(value != null)
            {
                for (Integer activeFrequency : this.mod.getFrequencyManager().getActiveFrequencies(speaker))
                {
                    if(value.isOn() && value.getFrequency() == activeFrequency)
                    {
                        value.makeDispatch(event);
                    }
                }
            }
        }
    }

}
