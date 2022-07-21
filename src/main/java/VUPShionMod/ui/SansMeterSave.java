package VUPShionMod.ui;

import VUPShionMod.patches.EnergyPanelPatches;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SansMeterSave implements CustomSavable<Integer> {
    public static int sansMeterSaveAmount = 100;

    @Override
    public Integer onSave() {
        return sansMeterSaveAmount;
    }

    @Override
    public void onLoad(Integer integer) {
        if (integer == null) {
            return;
        }
        sansMeterSaveAmount = integer;

        if (sansMeterSaveAmount < 0) {
            sansMeterSaveAmount = 0;
        }
        if (sansMeterSaveAmount > 100) {
            sansMeterSaveAmount = 100;
        }


    }
}
