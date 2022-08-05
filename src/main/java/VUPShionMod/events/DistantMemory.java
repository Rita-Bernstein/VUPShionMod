package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.Liyezhu.HallowedCase;
import VUPShionMod.relics.Liyezhu.Inhibitor;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;

public class DistantMemory extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(DistantMemory.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public DistantMemory() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/DistantMemory.png"));

        this.imageEventText.setDialogOption(OPTIONS[0], new Inhibitor());
        this.imageEventText.setDialogOption(OPTIONS[1], new HallowedCase());

    }


    public void onEnterRoom() {

    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                if (buttonPressed == 0) {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, new Inhibitor());

                    this.curScreen = CurrentScreen.COMPLETE;

                } else {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, new HallowedCase());
                    this.curScreen = CurrentScreen.COMPLETE;
                }
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
