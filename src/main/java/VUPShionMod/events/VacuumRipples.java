package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.MensVirtusque;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class VacuumRipples extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(VacuumRipples.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public VacuumRipples() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/VacuumRipples.png"));

        int counter = 0;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.hasTag(CardTagsEnum.MagiamObruor_CARD))
                counter++;
        }
        if (counter >= 3)
            this.imageEventText.setDialogOption(OPTIONS[0], new MensVirtusque());
        else
            this.imageEventText.setDialogOption(OPTIONS[2], true);

        this.imageEventText.setDialogOption(OPTIONS[1]);

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
                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new MensVirtusque(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

                    this.curScreen = CurrentScreen.COMPLETE;

                } else {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    AbstractDungeon.player.increaseMaxHp(20, true);
                    this.curScreen = CurrentScreen.COMPLETE;
                }
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
