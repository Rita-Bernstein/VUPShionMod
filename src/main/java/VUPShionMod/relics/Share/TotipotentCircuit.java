package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.PlayTmpCardAction;
import VUPShionMod.patches.BottledStasisPatch;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.function.Predicate;

public class TotipotentCircuit extends AbstractShionRelic implements CustomBottleRelic, CustomSavable<Integer> {
    public static final String ID = VUPShionMod.makeID(TotipotentCircuit.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/AttackCircuit.png";
    private static final String OUTLINE_PATH = "img/relics/outline/AttackCircuit.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public AbstractCard card = null;
    private boolean cardSelected = true;

    public TotipotentCircuit() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    private void setDescriptionAfterLoading() {
        this.description = String.format(this.DESCRIPTIONS[1], this.card.name);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottledStasisPatch.inBottledTotipotentCircuit::get;
    }

    public AbstractCard getCard() {
        return card.makeCopy();
    }


    @Override
    public Integer onSave() {
        return AbstractDungeon.player.masterDeck.group.indexOf(card);
    }

    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                BottledStasisPatch.inBottledTotipotentCircuit.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }


    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {

            tmp.addToTop(c);
        }
        AbstractDungeon.gridSelectScreen.open(tmp,
                1, DESCRIPTIONS[2],
                false, false, false, false);
    }


    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                BottledStasisPatch.inBottledTotipotentCircuit.set(cardInDeck, false);
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottledStasisPatch.inBottledTotipotentCircuit.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }


    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        if(this.card != null && !AbstractDungeon.player.drawPile.group.isEmpty())
        for (AbstractCard ca : AbstractDungeon.player.drawPile.group) {
            if (this.card.uuid == ca.uuid) {
                AbstractDungeon.player.drawPile.removeCard(ca);
                break;
            }
        }
    }


    @Override
    public void atBattleStart() {
        if (this.card != null) {
            addToBot(new PlayTmpCardAction(this.card));

        }
    }

    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
