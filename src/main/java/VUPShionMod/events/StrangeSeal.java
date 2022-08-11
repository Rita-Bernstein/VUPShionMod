package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.liyezhu.HolySlashDown;
import VUPShionMod.monsters.RitaShop;
import VUPShionMod.relics.Event.WhiteSeal;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class StrangeSeal extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(StrangeSeal.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private int curScreen = 0;

    private AbstractRelic sealRelic = new WhiteSeal();


    public StrangeSeal() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/StrangeSeal.png"));
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }


    public void onEnterRoom() {

    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[6 + AbstractDungeon.eventRng.random(2)]
                                + eventStrings.DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3], sealRelic);
                        this.curScreen = 11;
                        break;

                    case 1:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4], sealRelic);
                        this.curScreen = 12;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        this.imageEventText.setDialogOption(OPTIONS[6]);
                        this.curScreen = 13;
                        break;
                }
                return;
            case 11:
                (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter("Slavers");
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                AbstractDungeon.getCurrRoom().addRelicToRewards(this.sealRelic.makeCopy());
                AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                enterCombatFromImage();
                break;
            case 12:
                (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter("Slavers");
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                AbstractDungeon.getCurrRoom().addRelicToRewards(this.sealRelic.makeCopy());
                enterCombatFromImage();
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player,
                        DamageInfo.createDamageMatrix(12, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
                break;
            case 13:
                switch (buttonPressed) {
                    case 0:
                        openMap();
                        break;
                    default:

                        if (AbstractDungeon.miscRng.randomBoolean(0.5f)) {
                            this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[4]);
                            this.imageEventText.clearAllDialogs();
                            this.imageEventText.setDialogOption(OPTIONS[7]);
                            this.curScreen = 21;
                        } else {
                            this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[5]);
                            this.imageEventText.clearAllDialogs();
                            this.imageEventText.setDialogOption(OPTIONS[8]);
                            this.curScreen = 22;
                        }
                        break;
                }
                break;
            case 21:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, this.sealRelic.makeCopy());
                openMap();
                break;
            case 22:
                (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter("Slavers");
                (AbstractDungeon.getCurrRoom()).rewards.clear();
                AbstractDungeon.getCurrRoom().addRelicToRewards(this.sealRelic.makeCopy());
                enterCombatFromImage();

                break;
        }
    }


}
