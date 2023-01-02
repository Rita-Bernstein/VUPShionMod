package VUPShionMod.helpers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ArtificiumMod extends AbstractCardModifier {
    public static String ID = VUPShionMod.makeID("ArtificiumMod");

    public ArtificiumMod() {
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }


    public void onRemove(AbstractCard card) {
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MagiamObruorPower(AbstractDungeon.player, 1)));
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    public AbstractCardModifier makeCopy() {
        return new ArtificiumMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
