package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.shion.CollaborativeInvestigation;
import VUPShionMod.cards.ShionCard.shion.GravityRepression;
import VUPShionMod.cards.ShionCard.shion.TrackingAnalysis;
import VUPShionMod.relics.Shion.InfiniteSushi;
import VUPShionMod.skins.SkinManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class IntelligentFinFunnel extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(IntelligentFinFunnel.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public IntelligentFinFunnel() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/IntelligentFinFunnel.png"));
        this.imageEventText.setDialogOption(OPTIONS[0], new GravityRepression());
        this.imageEventText.setDialogOption(OPTIONS[1], new TrackingAnalysis());

        if (SkinManager.getSkinCharacter(0).reskinCount == 3)
            this.imageEventText.setDialogOption(OPTIONS[2], new CollaborativeInvestigation());
        else
            this.imageEventText.setDialogOption(OPTIONS[3], true);
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
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new GravityRepression(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        logMetricObtainCard(NAME, "continue",new GravityRepression());
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new TrackingAnalysis(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        logMetricObtainCard(NAME, "continue",new TrackingAnalysis());
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new CollaborativeInvestigation(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        logMetricObtainCard(NAME, "continue",new CollaborativeInvestigation());
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
