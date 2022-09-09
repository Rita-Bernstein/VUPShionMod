package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.monsters.Rita.RitaShop;
import VUPShionMod.relics.Event.FruitCake;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Mango;
import com.megacrit.cardcrawl.relics.Pear;
import com.megacrit.cardcrawl.relics.Strawberry;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

public class FruitStall extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(FruitStall.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private int screenCount = 0;

    private boolean[] Fruit = new boolean[]{false, false, false, false};

    private int StrawberryGold = 72;
    private int MANGOGold = 40;
    private int PEARGold = 36;

    private int goldLoss = 100;

    private AbstractRelic cake = new FruitCake();


    public FruitStall() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/FruitStall.png"));

        getPlayerFruit();

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.StrawberryGold = 60;
            this.MANGOGold = 30;
            this.PEARGold = 30;

            this.goldLoss = 125;
        }
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    private void getPlayerFruit() {
        Fruit[0] = AbstractDungeon.player.hasRelic("Pear");
        Fruit[1] = AbstractDungeon.player.hasRelic("Mango");
        Fruit[2] = AbstractDungeon.player.hasRelic("Strawberry");

        Fruit[3] = Fruit[0] || Fruit[1] || Fruit[2];
    }

    public void onEnterRoom() {

    }

    private void refreshMainScreen() {
        imageEventText.updateBodyText(DESCRIPTIONS[2]);
        imageEventText.clearRemainingOptions();
        imageEventText.optionList.set(0, new LargeDialogOptionButton(0, OPTIONS[3], cake));

        if (AbstractDungeon.player.gold >= this.goldLoss) {
            imageEventText.updateDialogOption(1, String.format(OPTIONS[4], this.goldLoss));
        } else {
            imageEventText.updateDialogOption(1, String.format(OPTIONS[5], this.goldLoss), true);
        }

        if (Fruit[3]) {
            imageEventText.updateDialogOption(2, OPTIONS[6]);
        } else {
            imageEventText.updateDialogOption(2, OPTIONS[7], true);
        }

        imageEventText.updateDialogOption(3, OPTIONS[8]);
        this.screenCount = 20;
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screenCount) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        imageEventText.clearRemainingOptions();
                        imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.screenCount = 10;
                        break;
                    case 1:
                        imageEventText.updateBodyText(DESCRIPTIONS[8]);
                        imageEventText.clearRemainingOptions();
                        imageEventText.updateDialogOption(0, OPTIONS[8]);
                        logMetricIgnored(ID);
                        this.screenCount = 40;
                        break;
                }
                break;

            case 10:
                refreshMainScreen();
                break;
            case 20:
                switch (buttonPressed) {
                    case 0:
                        imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        imageEventText.clearRemainingOptions();
                        imageEventText.updateDialogOption(0, OPTIONS[11]);
                        imageEventText.updateDialogOption(1, OPTIONS[12], true);
                        imageEventText.updateDialogOption(2, OPTIONS[13], true);
                        this.imageEventText.loadImage("VUPShionMod/img/events/FruitStall2.png");

                        this.screenCount = 30;
                        if (!AbstractDungeon.player.hasRelic(FruitCake.ID))
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, cake);
                        break;
                    case 1:
                        imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        imageEventText.clearRemainingOptions();
                        AbstractDungeon.player.loseGold(this.goldLoss);
                        AbstractDungeon.player.increaseMaxHp(10, true);
                        imageEventText.updateDialogOption(0, OPTIONS[8]);
                        this.screenCount = 40;
                        break;
                    case 2:
                        imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        imageEventText.clearRemainingOptions();
                        if (Fruit[0]) {
                            imageEventText.updateDialogOption(0, String.format(OPTIONS[9], CardCrawlGame.languagePack.getRelicStrings(Pear.ID).NAME, PEARGold));
                        } else {
                            imageEventText.updateDialogOption(0, OPTIONS[7], true);
                        }
                        if (Fruit[1]) {
                            imageEventText.updateDialogOption(1, String.format(OPTIONS[9], CardCrawlGame.languagePack.getRelicStrings(Mango.ID).NAME, MANGOGold));
                        } else {
                            imageEventText.updateDialogOption(1, OPTIONS[7], true);
                        }
                        if (Fruit[2]) {
                            imageEventText.updateDialogOption(2, String.format(OPTIONS[9], CardCrawlGame.languagePack.getRelicStrings(Strawberry.ID).NAME, StrawberryGold));
                        } else {
                            imageEventText.updateDialogOption(2, OPTIONS[7], true);
                        }
                        imageEventText.updateDialogOption(3, OPTIONS[10]);
                        this.screenCount = 50;
                        break;
                    case 3:
                        imageEventText.updateBodyText(DESCRIPTIONS[9]);
                        imageEventText.clearRemainingOptions();
                        imageEventText.updateDialogOption(0, OPTIONS[8]);
                        this.screenCount = 40;
                        break;
                }
                break;
            case 30:
                switch (buttonPressed) {
                    case 0:
                        steal();
                        break;
                }
                break;

            case 40:
                openMap();
                break;
            case 50:
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.player.loseRelic(Pear.ID);
                        getPlayerFruit();
                        imageEventText.updateBodyText(DESCRIPTIONS[7]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.PEARGold));
                        AbstractDungeon.player.gainGold(this.PEARGold);
                        imageEventText.updateDialogOption(0, OPTIONS[7], true);
                        break;
                    case 1:
                        AbstractDungeon.player.loseRelic(Mango.ID);
                        getPlayerFruit();
                        imageEventText.updateBodyText(DESCRIPTIONS[7]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.MANGOGold));
                        AbstractDungeon.player.gainGold(this.MANGOGold);
                        imageEventText.updateDialogOption(1, OPTIONS[7], true);
                        break;
                    case 2:
                        AbstractDungeon.player.loseRelic(Strawberry.ID);
                        getPlayerFruit();
                        imageEventText.updateBodyText(DESCRIPTIONS[6]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.StrawberryGold));
                        AbstractDungeon.player.gainGold(this.StrawberryGold);
                        imageEventText.updateDialogOption(2, OPTIONS[7], true);
                        break;
                    case 3:
                        refreshMainScreen();
                        break;
                }
                break;


        }
    }

    private void steal() {
        (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter(RitaShop.ID);
        (AbstractDungeon.getCurrRoom()).rewards.clear();
        AbstractDungeon.getCurrRoom().addPotionToRewards(new FruitJuice());
        AbstractDungeon.getCurrRoom().addPotionToRewards(new FruitJuice());
        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
//        AbstractDungeon.getCurrRoom().addRelicToRewards(new FruitCake());
        enterCombatFromImage();

    }

}
