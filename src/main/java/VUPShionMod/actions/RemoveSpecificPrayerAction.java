package VUPShionMod.actions;

import VUPShionMod.patches.AbstractPrayerPatches;
import VUPShionMod.prayers.AbstractPrayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveSpecificPrayerAction extends AbstractGameAction {
    private AbstractPrayer prayerInstance;
    private boolean justStart = true;

    public RemoveSpecificPrayerAction(AbstractPrayer prayerInstance) {
        this.prayerInstance = prayerInstance;
        this.duration = 0.1F;
    }


    @Override
    public void update() {
        if (justStart) {
            AbstractPrayer removeMe = null;
            if (this.prayerInstance != null && AbstractPrayerPatches.prayers.contains(this.prayerInstance)) {
                removeMe = this.prayerInstance;
            }

            if (removeMe != null) {
                AbstractPrayerPatches.prayers.remove(removeMe);
            } else {
                this.duration = 0.0F;
            }

            justStart = false;
        }

        this.tickDuration();
    }
}
