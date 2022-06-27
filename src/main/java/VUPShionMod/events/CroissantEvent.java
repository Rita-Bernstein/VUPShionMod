package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.Event.Croissant;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class CroissantEvent extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID("CroissantEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int damage = 15;
    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public CroissantEvent() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/CroissantEvent.png"));
        this.imageEventText.setDialogOption(OPTIONS[0], new Croissant());
        this.imageEventText.setDialogOption(OPTIONS[1]);

    }


    public void onEnterRoom() {

    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                if (buttonPressed == 0) {
                    if (AbstractDungeon.miscRng.randomBoolean(0.25f)) {
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                        this.imageEventText.removeDialogOption(1);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.damage));
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.FIRE));
                    } else {
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        this.imageEventText.removeDialogOption(1);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, new Croissant());
                    }
                    this.curScreen = CurrentScreen.COMPLETE;

                } else {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    this.curScreen = CurrentScreen.COMPLETE;
                }
                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
