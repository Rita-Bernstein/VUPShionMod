package VUPShionMod.ui;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class SynchroOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(SynchroOption.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractRelic synchroRelic;

    public SynchroOption(AbstractRelic synchroRelic) {
        this.label = TEXT[0];
        this.usable = true;
        this.synchroRelic = synchroRelic;
        updateUsability(true);
    }

    public void updateUsability(boolean canUse) {
        this.description = TEXT[1];
        this.img = ImageMaster.loadImage("VUPShionMod/img/ui/Campfire/SynchroOption.png");
    }


    public void useOption() {
        if (this.usable && synchroRelic != null) {
            CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_2");
            synchroRelic.counter += 3;

            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
            AbstractRoom.waitTimer = 0.0F;
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        }
    }
}