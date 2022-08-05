package VUPShionMod.ui;

import basemod.abstracts.CustomSavable;

public class WingShieldDamageSave implements CustomSavable<Integer> {
    public static int wingShieldDamageSaveAmount = 0;

    @Override
    public Integer onSave() {
        return wingShieldDamageSaveAmount;
    }

    @Override
    public void onLoad(Integer integer) {
        if (integer == null) {
            return;
        }
        wingShieldDamageSaveAmount = integer;

        if (wingShieldDamageSaveAmount < 0) {
            wingShieldDamageSaveAmount = 0;
        }

    }
}
