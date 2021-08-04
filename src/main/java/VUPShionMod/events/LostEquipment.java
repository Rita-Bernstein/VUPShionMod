package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.Croissant;
import VUPShionMod.relics.OpticalCamouflage;
import VUPShionMod.relics.Sniperscope;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class LostEquipment extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID("LostEquipment");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public LostEquipment() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/LostEquipment.jpg"));
        if (AbstractDungeon.player.gold >= 100)
            this.imageEventText.setDialogOption(OPTIONS[0],new OpticalCamouflage());
        else
            this.imageEventText.setDialogOption(OPTIONS[1], true);

        if (AbstractDungeon.player.masterDeck.group.size() >= 1)
            this.imageEventText.setDialogOption(OPTIONS[2], new Sniperscope());
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
                switch (buttonPressed){
                    case 0:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        AbstractDungeon.player.loseGold(100);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, new OpticalCamouflage());
                        this.curScreen = CurrentScreen.COMPLETE;
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        AbstractCard card = AbstractDungeon.player.masterDeck.getRandomCard(AbstractDungeon.eventRng);CardCrawlGame.sound.play("CARD_EXHAUST");
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, Settings.WIDTH *0.5f, Settings.HEIGHT *0.5f));
                        AbstractDungeon.player.masterDeck.removeCard(card);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, new Sniperscope());
                        this.curScreen = CurrentScreen.COMPLETE;
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.curScreen = CurrentScreen.COMPLETE;
                        return;
                }

            case COMPLETE:
                openMap();
                break;
        }
    }
}
