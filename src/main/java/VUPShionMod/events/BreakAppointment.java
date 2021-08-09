package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.kuroisu.BlackHand;
import VUPShionMod.relics.Croissant;
import VUPShionMod.relics.OpticalCamouflage;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BreakAppointment extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID("BreakAppointment");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public BreakAppointment() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/BreakAppointment.jpg"));
        if (AbstractDungeon.player.gold >= 50)
            this.imageEventText.setDialogOption(OPTIONS[0]);
        else
            this.imageEventText.setDialogOption(OPTIONS[1], true);

        this.imageEventText.setDialogOption(OPTIONS[2]);

    }


    public void onEnterRoom() {

    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                if (buttonPressed == 0) {
                    if (AbstractDungeon.eventRng.randomBoolean(0.25f)) {
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        this.imageEventText.removeDialogOption(1);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.loadImage(VUPShionMod.assetPath("img/events/BreakAppointment2.jpg"));
                    } else {
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        this.imageEventText.removeDialogOption(1);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        AbstractCard c = VUPShionMod.ku_Cards.get(AbstractDungeon.eventRng.random(VUPShionMod.ku_Cards.size() - 1)).makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    }
                    this.curScreen = CurrentScreen.COMPLETE;

                } else {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    this.imageEventText.loadImage(VUPShionMod.assetPath("img/events/BreakAppointment3.jpg"));
                    this.curScreen = CurrentScreen.COMPLETE;
                }
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
