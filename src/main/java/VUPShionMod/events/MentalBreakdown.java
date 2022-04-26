package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.Liyezhu.Calamity;
import VUPShionMod.cards.WangChuan.Moonstrider;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbyssalCrux;
import VUPShionMod.relics.Croissant;
import VUPShionMod.relics.TrackingBeacon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class MentalBreakdown extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID(MentalBreakdown.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE,
    }


    public MentalBreakdown() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/MentalBreakdown.png"));
        this.imageEventText.setDialogOption(OPTIONS[0], new AbyssalCrux());
        this.imageEventText.setDialogOption(OPTIONS[1], new Calamity());
        this.imageEventText.setDialogOption(OPTIONS[2]);

    }


    public void onEnterRoom() {

    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                if (buttonPressed == 0) {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[4]);

                    if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
                        EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).loseSan(100);
                    }
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, new AbyssalCrux());
                    this.curScreen = CurrentScreen.COMPLETE;

                }

                if (buttonPressed == 1) {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[2]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[4]);

                    for (int i = AbstractDungeon.player.masterDeck.group.size() - 1; i >= 0; i--) {
                        AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
                        if (card.hasTag(CardTagsEnum.Prayer_CARD) && !card.inBottleFlame && !card.inBottleLightning && !card.inBottleTornado) {
                            AbstractDungeon.effectList.add(new PurgeCardEffect(card));
                            AbstractDungeon.player.masterDeck.group.remove(card);
                        }
                    }
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Calamity(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

                    this.curScreen = CurrentScreen.COMPLETE;
                }

                if (buttonPressed == 2) {
                    this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[3]);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[4]);

                    if (CardHelper.hasCardWithType(AbstractCard.CardType.POWER)) {
                        AbstractCard card = CardHelper.returnCardOfType(AbstractCard.CardType.POWER, AbstractDungeon.miscRng);
                        AbstractDungeon.effectList.add(new PurgeCardEffect(card));
                        AbstractDungeon.player.masterDeck.removeCard(card);
                    }
                    AbstractDungeon.player.increaseMaxHp(10, true);
                    if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
                        EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).addSan(50);
                    }
                    this.curScreen = CurrentScreen.COMPLETE;
                }


                return;
            case COMPLETE:
                openMap();
                break;
        }
    }
}
