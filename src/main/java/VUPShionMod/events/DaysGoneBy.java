package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.SeverCurrent;
import VUPShionMod.cards.WangChuan.Slide;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.SapphireRoseNecklace;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireRecallEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class DaysGoneBy extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID("DaysGoneBy");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public DaysGoneBy() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/DaysGoneBy.png"));
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1], new SapphireRoseNecklace());
        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu)
            this.imageEventText.setDialogOption(OPTIONS[2]);
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
                        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.RED));
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, new SapphireRoseNecklace());
                        logMetricObtainRelic(NAME, "Power", new SapphireRoseNecklace());
                        break;


                    case 2:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                        EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).addSan(10);
                        logMetricObtainRelic(NAME, "Recall", new SapphireRoseNecklace());
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
