package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class BreakAppointment extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(BreakAppointment.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;
    private boolean pickCard = false;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public BreakAppointment() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/BreakAppointment.png"));
        if (AbstractDungeon.player.gold >= 50)
            this.imageEventText.setDialogOption(OPTIONS[0]);
        else
            this.imageEventText.setDialogOption(OPTIONS[1], true);

        this.imageEventText.setDialogOption(OPTIONS[2]);

    }


    public void onEnterRoom() {

    }


    @Override
    public void update() {
        super.update();
        if (this.pickCard &&
                !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeCopy();
            logMetricObtainCard("The Library", "Read", c);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                if (buttonPressed == 0) {
                    AbstractDungeon.player.loseGold(50);
                    if (AbstractDungeon.eventRng.randomBoolean(0.5f)) {
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        this.imageEventText.removeDialogOption(1);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.loadImage(VUPShionMod.assetPath("img/events/BreakAppointment2.png"));
                    } else {
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        this.imageEventText.removeDialogOption(1);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
//                        AbstractCard c = VUPShionMod.mi_Cards.get(AbstractDungeon.eventRng.random(VUPShionMod.mi_Cards.size() - 1)).makeCopy();
//                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

                        this.pickCard = true;
                        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                        for (AbstractCard card : VUPShionMod.mi_Cards) {
                            if (card.rarity != AbstractCard.CardRarity.BASIC) {
                                group.addToTop(card);
                                UnlockTracker.markCardAsSeen(card.cardID);
                            }
                        }

                        AbstractDungeon.gridSelectScreen.open(group, 1, OPTIONS[4], false);

                    }
                    this.curScreen = CurrentScreen.COMPLETE;

                } else {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    this.imageEventText.loadImage(VUPShionMod.assetPath("img/events/BreakAppointment3.png"));
                    this.curScreen = CurrentScreen.COMPLETE;
                }
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
