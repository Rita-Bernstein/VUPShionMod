package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.Codex.AbstractCodexCard;
import VUPShionMod.cards.Codex.ChaosNimius;
import VUPShionMod.cards.Codex.ChaosRapidus;
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
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class BoundaryOfChaos extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID("BoundaryOfChaos");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private AbstractCard codex;
    private AbstractCard codex1;
    private AbstractCard codex2;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public BoundaryOfChaos() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/BoundaryOfChaos.png"));
        if(AbstractDungeon.eventRng.randomBoolean(0.5f)){
            codex = new ChaosNimius();
            codex1 = new ChaosNimius();
            codex2 = new ChaosNimius();
        }else {
            codex = new ChaosRapidus();
            codex1 = new ChaosRapidus();
            codex2 = new ChaosRapidus();
        }

        codex1.upgrade();
        codex2.upgrade();
        codex2.upgrade();


        if (hasCodex())
            this.imageEventText.setDialogOption(OPTIONS[0],codex);
        else
            this.imageEventText.setDialogOption(OPTIONS[1], true);

        if (hasCodex1()) {
            this.imageEventText.setDialogOption(OPTIONS[2],codex1);
        }
        else
            this.imageEventText.setDialogOption(OPTIONS[3], true);

        if (hasCodex2()) {

            this.imageEventText.setDialogOption(OPTIONS[4],codex2);
        }
        else
            this.imageEventText.setDialogOption(OPTIONS[5], true);

        this.imageEventText.setDialogOption(OPTIONS[6]);
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
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(codex, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        logMetricObtainCard(NAME, "Contact", codex);
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(codex1, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        logMetricObtainCard(NAME, "Capture", codex1);
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(codex2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        logMetricObtainCard(NAME, "Control", codex2);
                        break;
                    case 3:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[4]);
                        break;
                }

                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[6]);
                this.curScreen = CurrentScreen.COMPLETE;
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }

    public static boolean hasCodex() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractCodexCard) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCodex1() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractCodexCard) {
                if (c.timesUpgraded == 1)
                    return true;
            }
        }
        return false;
    }

    public static boolean hasCodex2() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractCodexCard) {
                if (c.timesUpgraded >= 2)
                    return true;
            }
        }
        return false;
    }
}


