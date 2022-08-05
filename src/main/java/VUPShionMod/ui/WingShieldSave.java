package VUPShionMod.ui;

import basemod.abstracts.CustomSavable;

public class WingShieldSave implements CustomSavable<Integer> {
    public static int wingShieldSaveAmount = 7;

    @Override
    public Integer onSave() {
        return wingShieldSaveAmount;
    }

    @Override
    public void onLoad(Integer integer) {
        if (integer == null) {
            return;
        }
        wingShieldSaveAmount = integer;

        if (wingShieldSaveAmount < 0) {
            wingShieldSaveAmount = 0;
        }

    }
}
