package fr.nathanael2611.modernvoicecontent.client.gui;

import fr.nathanael2611.modernvoicecontent.item.ItemRadio;
import fr.nathanael2611.modularvoicechat.util.Helpers;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumHand;

import java.awt.*;
import java.io.IOException;

public abstract class GuiRadio extends GuiScreen
{

    private GuiTextField frequencyField;
    private int initialFrequency;

    public GuiRadio(int initialFrequency)
    {
        this.initialFrequency = initialFrequency;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.frequencyField = new GuiTextField(0, mc.fontRenderer, width / 2 - 25, height / 2 - 10, 50, 20);
        this.frequencyField.setText(this.initialFrequency + "");
        this.buttonList.add(new GuiButton(1, width / 2 - 25, height / 2 + 20, 50, 20, "OK"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        String str = "Fréquence Radio";
        Gui.drawRect(width / 2 - 50, height / 2 - 50, width / 2 + 50, height / 2 + 50, new Color(0, 0, 0, 150).getRGB());
        mc.fontRenderer.drawStringWithShadow(str, width / 2 - fontRenderer.getStringWidth(str) / 2, height / 2 - 25, Color.WHITE.getRGB());
        this.frequencyField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        if(this.frequencyField.isFocused())
        {
            this.frequencyField.textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.frequencyField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 1)
        {
            this.changeFrequency(Helpers.parseOrZero(this.frequencyField.getText()));
            mc.displayGuiScreen(null);
        }
    }

    abstract void changeFrequency(int frequency);

}
