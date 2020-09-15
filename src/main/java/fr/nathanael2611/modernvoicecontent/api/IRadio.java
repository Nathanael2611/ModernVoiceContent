package fr.nathanael2611.modernvoicecontent.api;

import com.google.common.collect.Lists;

import java.util.List;

public interface IRadio extends IActivable
{

    int getFrequency();

    void setFrequency(int frequency);

    default List<String> getInformations()
    {
        return Lists.newArrayList(
                "§7Fréquence: " + getFrequency() + "Hz",
                "§7Etat: " + (isOn() ? "§aAllumé" : "§cEteint"),
                "§7§oSneak-click pour changer la fréquence."
        );
    }

}
