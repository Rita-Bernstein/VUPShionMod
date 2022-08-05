package VUPShionMod.ui;

import basemod.abstracts.CustomSavable;

public class WingShieldRefundSave implements CustomSavable<Integer> {
    public static int wingShieldRefundSaveAmount = 0;

    @Override
    public Integer onSave() {
        return wingShieldRefundSaveAmount;
    }

    @Override
    public void onLoad(Integer integer) {
        if (integer == null) {
            return;
        }
        wingShieldRefundSaveAmount = integer;

        if (wingShieldRefundSaveAmount < 0) {
            wingShieldRefundSaveAmount = 0;
        }
        if (wingShieldRefundSaveAmount > 2) {
            wingShieldRefundSaveAmount = 2;
        }

    }
}
