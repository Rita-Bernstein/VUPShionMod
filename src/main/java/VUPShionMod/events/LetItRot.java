package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.Event.SapphireRoseNecklace;
import VUPShionMod.relics.Shion.InfiniteSushi;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

public class LetItRot extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(LetItRot.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public LetItRot() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/LetItRot.png"));
        this.imageEventText.setDialogOption(OPTIONS[0], new InfiniteSushi());
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }


    public void onEnterRoom() {

    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, new InfiniteSushi());
                        logMetricObtainRelic(NAME, "continue", new InfiniteSushi());
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                        logMetricHeal(NAME, "Rest", AbstractDungeon.player.maxHealth);
                        break;
                }

                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[2]);
                this.curScreen = CurrentScreen.COMPLETE;
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
