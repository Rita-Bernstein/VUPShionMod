package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.monsters.RitaShop;
import VUPShionMod.relics.Event.FruitCake;
import VUPShionMod.relics.Event.TrackingBeacon;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

public class FruitStall extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(FruitStall.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;
    private OptionChosen option = OptionChosen.NONE;
    private FruitChosen selectedFruit = FruitChosen.NONE;
    private boolean[] Fruit = new boolean[]{false, false, false, false};

    private int StrawberryGold = 72;
    private int MANGOGold = 40;
    private int PEARGold = 36;

    private int goldLoss = 100;

    private AbstractRelic cake = new FruitCake();


    private enum CurrentScreen {
        INTRO, DONE,
    }

    private enum OptionChosen {
        STEAL, BUY, SELL, NONE, INTRO, DONE
    }

    private enum FruitChosen {
        PEAR, MANGO, Strawberry, NONE, INTRO,
    }

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

    void getPlayerFruit() {

        if (AbstractDungeon.player.hasRelic("Pear")) {
            Fruit[0] = true;
        }
        if (AbstractDungeon.player.hasRelic("Mango")) {
            Fruit[1] = true;
        }
        if (AbstractDungeon.player.hasRelic("Strawberry")) {
            Fruit[2] = true;
        }

        if (Fruit[0] || Fruit[1] || Fruit[2]) {
            Fruit[3] = true;
        }

    }

    public boolean hasFruit(){
        boolean fruit = false;
        if (AbstractDungeon.player.hasRelic("Pear")) {
            fruit = true;
        }
        if (AbstractDungeon.player.hasRelic("Mango")) {
            fruit = true;
        }
        if (AbstractDungeon.player.hasRelic("Strawberry")) {
            fruit = true;
        }
       return fruit;
    }


    public void onEnterRoom() {

    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (selectedFruit) {
            case NONE:
                switch (option) {
                    case NONE:
                        switch (curScreen) {
                            case INTRO:
                                switch (buttonPressed) {
                                    case 0:
                                        imageEventText.updateBodyText(DESCRIPTIONS[1]);
                                        imageEventText.clearRemainingOptions();
                                        imageEventText.optionList.set(0,new LargeDialogOptionButton(0,OPTIONS[2],cake));
//                                        imageEventText.updateDialogOption(0, OPTIONS[2]);

                                        if (AbstractDungeon.player.gold >= this.goldLoss) {
                                            imageEventText.updateDialogOption(1, String.format(OPTIONS[3], this.goldLoss));
                                        } else {
                                            imageEventText.updateDialogOption(1, String.format(OPTIONS[4], this.goldLoss), true);
                                        }

                                        if (Fruit[3]) {
                                            imageEventText.updateDialogOption(2, OPTIONS[5]);
                                        } else {
                                            imageEventText.updateDialogOption(2, OPTIONS[9], true);
                                        }

                                        imageEventText.updateDialogOption(3, OPTIONS[10]);

                                        option = OptionChosen.INTRO;
                                        break;
                                    case 1:
                                        imageEventText.updateBodyText(DESCRIPTIONS[7]);
                                        imageEventText.updateDialogOption(0, OPTIONS[10]);
                                        imageEventText.clearRemainingOptions();
                                        option = OptionChosen.NONE;
                                        logMetricIgnored(ID);
                                        curScreen = CurrentScreen.DONE;
                                        break;
                                }
                                break;

                            case DONE:
                                this.imageEventText.setDialogOption(OPTIONS[10]);
                                imageEventText.clearRemainingOptions();
                                openMap();
                                break;
                        }
                        break;

                    case INTRO:
                        switch (buttonPressed) {
                            case 0:
                                option = OptionChosen.STEAL;
                                imageEventText.updateBodyText(DESCRIPTIONS[2]);
                                imageEventText.clearRemainingOptions();
                                imageEventText.updateDialogOption(0, OPTIONS[10]);

                                this.imageEventText.loadImage("VUPShionMod/img/events/FruitStall2.png");
                                break;

                            case 1:
                                option = OptionChosen.BUY;
                                imageEventText.updateBodyText(DESCRIPTIONS[3]);
                                imageEventText.clearRemainingOptions();
                                AbstractDungeon.player.loseGold(this.goldLoss);
                                AbstractDungeon.player.increaseMaxHp(10, true);
                                imageEventText.updateDialogOption(0, OPTIONS[10]);
                                break;

                            case 2:
                                option = OptionChosen.SELL;
                                imageEventText.updateBodyText(DESCRIPTIONS[4]);
                                imageEventText.clearRemainingOptions();
                                if (Fruit[0]) {
                                    imageEventText.updateDialogOption(0, String.format(OPTIONS[6], PEARGold));
                                } else {
                                    imageEventText.updateDialogOption(0, OPTIONS[9], true);
                                }
                                if (Fruit[1]) {
                                    imageEventText.updateDialogOption(1, String.format(OPTIONS[7], MANGOGold));
                                } else {
                                    imageEventText.updateDialogOption(1, OPTIONS[9], true);
                                }
                                if (Fruit[2]) {
                                    imageEventText.updateDialogOption(2, String.format(OPTIONS[8], StrawberryGold));
                                } else {
                                    imageEventText.updateDialogOption(2, OPTIONS[9], true);
                                }
                                imageEventText.updateDialogOption(3, OPTIONS[11]);

                                break;
                            case 3:
                                imageEventText.updateBodyText(DESCRIPTIONS[7]);
                                imageEventText.updateDialogOption(0, OPTIONS[10]);
                                imageEventText.clearRemainingOptions();
                                option = OptionChosen.NONE;
                                logMetricIgnored(ID);
                                curScreen = CurrentScreen.DONE;
                                break;

                        }

                        break;

                    case STEAL:
                        steal();
                        break;

                    case BUY:
                        openMap();
                        break;

                    case SELL:
                        switch (buttonPressed) {
                            case 0:
                                Fruit[0] = false;
                                if (Fruit[1] || Fruit[2]) {
                                    Fruit[3] = true;
                                } else {
                                    Fruit[3] = false;
                                }
                                AbstractDungeon.player.loseRelic("Pear");
                                imageEventText.updateBodyText(DESCRIPTIONS[6]);
                                AbstractDungeon.effectList.add(new RainingGoldEffect(this.PEARGold));
                                AbstractDungeon.player.gainGold(this.PEARGold);
                                imageEventText.updateDialogOption(0, OPTIONS[9], true);


                                break;
                            case 1:
                                Fruit[1] = false;
                                if (Fruit[0] || Fruit[2]) {
                                    Fruit[3] = true;
                                } else {
                                    Fruit[3] = false;
                                }
                                AbstractDungeon.player.loseRelic("Mango");
                                imageEventText.updateBodyText(DESCRIPTIONS[6]);
                                AbstractDungeon.effectList.add(new RainingGoldEffect(this.MANGOGold));
                                AbstractDungeon.player.gainGold(this.MANGOGold);
                                imageEventText.updateDialogOption(1, OPTIONS[9], true);
                                break;
                            case 2:
                                Fruit[2] = false;
                                if (Fruit[0] || Fruit[1]) {
                                    Fruit[3] = true;
                                } else {
                                    Fruit[3] = false;
                                }
                                imageEventText.updateBodyText(DESCRIPTIONS[5]);
                                AbstractDungeon.player.loseRelic("Strawberry");
                                AbstractDungeon.effectList.add(new RainingGoldEffect(this.StrawberryGold));
                                AbstractDungeon.player.gainGold(this.StrawberryGold);
                                imageEventText.updateDialogOption(2, OPTIONS[9], true);
                                break;
                            case 3:
                                imageEventText.updateBodyText(DESCRIPTIONS[1]);
                                imageEventText.optionList.set(0,new LargeDialogOptionButton(0,OPTIONS[2],cake));
//                                imageEventText.updateDialogOption(0, OPTIONS[2]);

                                if (AbstractDungeon.player.gold >= this.goldLoss) {
                                    imageEventText.updateDialogOption(1, String.format(OPTIONS[3], goldLoss));
                                } else {
                                    imageEventText.updateDialogOption(1, String.format(OPTIONS[4], goldLoss), true);
                                }

                                if (Fruit[3]) {
                                    imageEventText.updateDialogOption(2, OPTIONS[5]);
                                } else {
                                    imageEventText.updateDialogOption(2, OPTIONS[9], true);
                                }
                                imageEventText.updateDialogOption(3, OPTIONS[10]);
                                option = OptionChosen.INTRO;

                                break;


                        }
                        break;

                    case DONE:
                        logMetricIgnored(ID);
                        openMap();
                        break;
                }


        }
    }

    private void steal() {
        (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter(RitaShop.ID);
        (AbstractDungeon.getCurrRoom()).rewards.clear();
        AbstractDungeon.getCurrRoom().addPotionToRewards(new FruitJuice());
        AbstractDungeon.getCurrRoom().addPotionToRewards(new FruitJuice());
        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
//        AbstractDungeon.getCurrRoom().addRelicToRewards(new FruitCake());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, cake);
        enterCombatFromImage();

    }

}
