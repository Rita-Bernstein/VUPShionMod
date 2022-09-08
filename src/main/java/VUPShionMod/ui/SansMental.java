package VUPShionMod.ui;

import basemod.abstracts.CustomSavable;

public class SansMental implements CustomSavable<Boolean> {
    public static boolean sansMental = false;

    @Override
    public Boolean onSave() {
        return sansMental;
    }

    @Override
    public void onLoad(Boolean aBoolean) {
        if (aBoolean == null)
            return;

        sansMental = aBoolean;

    }
}
