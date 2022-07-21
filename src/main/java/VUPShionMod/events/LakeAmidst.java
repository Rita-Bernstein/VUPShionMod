package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.Moonstrider;
import VUPShionMod.cards.WangChuan.SeverCurrent;
import VUPShionMod.cards.WangChuan.Slide;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

public class LakeAmidst extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(LakeAmidst.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public LakeAmidst() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/LakeAmidst.png"));
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1], new SeverCurrent());

        if (CardHelper.hasCardWithID(Slide.ID))
            this.imageEventText.setDialogOption(OPTIONS[2], new Moonstrider());
        else
            this.imageEventText.setDialogOption(OPTIONS[3], true);

        this.imageEventText.setDialogOption(OPTIONS[4]);
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
                        AbstractDungeon.player.increaseMaxHp(10, true);
                        AbstractEvent.logMetricHeal(NAME, "Camp", 10);
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new SeverCurrent(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        logMetricObtainCard(NAME, "Look at the bottom", new SeverCurrent());
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                        ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
                        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                            if (c instanceof Slide)
                                cardsToRemove.add(c);
                        }

                        for (AbstractCard card : cardsToRemove) {
                            AbstractDungeon.effectList.add(new PurgeCardEffect(card));
                        }

                        AbstractDungeon.player.masterDeck.group.removeAll(cardsToRemove);
                        cardsToRemove.clear();

                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Moonstrider(), Settings.WIDTH * 0.2f, Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Moonstrider(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Moonstrider(), Settings.WIDTH * 0.8f, Settings.HEIGHT / 2.0F));
                        break;
                    case 3:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[4]);
                        break;
                }

                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[4]);
                this.curScreen = CurrentScreen.COMPLETE;
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
