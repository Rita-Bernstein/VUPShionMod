package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.liyezhu.HolySlashDown;
import VUPShionMod.cards.WangChuan.MensVirtusque;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.Event.SapphireRoseNecklace;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class HolyJudgement extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(HolyJudgement.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public HolyJudgement() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/HolyJudgement.png"));
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1], new HolySlashDown());
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
                        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new HolySlashDown(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        break;
                }

                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                this.curScreen = CurrentScreen.COMPLETE;
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
