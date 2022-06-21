package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.Event.SapphireRoseNecklace;
import VUPShionMod.relics.Liyezhu.DemonSword;
import VUPShionMod.relics.Liyezhu.QueenShield;
import VUPShionMod.relics.Liyezhu.TimeReversalBullet;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

public class SpiritSinkingRampage extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(SpiritSinkingRampage.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public SpiritSinkingRampage() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/SpiritSinkingRampage.png"));
        this.imageEventText.setDialogOption(OPTIONS[0], new TimeReversalBullet());
        this.imageEventText.setDialogOption(OPTIONS[1], new DemonSword());
        this.imageEventText.setDialogOption(OPTIONS[2], new QueenShield());
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
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, new TimeReversalBullet());
                        logMetricObtainRelic(NAME, "Power", new SapphireRoseNecklace());
                        this.imageEventText.loadImage("VUPShionMod/img/events/SpiritSinkingRampage1.png");
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, new DemonSword());
                        logMetricObtainRelic(NAME, "Power", new SapphireRoseNecklace());
                        this.imageEventText.loadImage("VUPShionMod/img/events/LostEquipment.png");
                        break;


                    case 2:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, new QueenShield());
                        logMetricObtainRelic(NAME, "Power", new SapphireRoseNecklace());
                        this.imageEventText.loadImage("VUPShionMod/img/events/LostEquipment.png");
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
