package VUPShionMod.helpers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SheatheModifier extends AbstractCardModifier {
    public static String ID = "VUPShionMod:SheatheModifier";

    public SheatheModifier() {
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        boolean isMuti = ReflectionHacks.getPrivate(card, AbstractCard.class, "isMultiDamage");
        if (isMuti)
            return CardCrawlGame.languagePack.getCardStrings(VUPShionMod.makeID("Sheathe")).EXTENDED_DESCRIPTION[2] + rawDescription;
        else
            return CardCrawlGame.languagePack.getCardStrings(VUPShionMod.makeID("Sheathe")).EXTENDED_DESCRIPTION[1] + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return true;
    }

    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        boolean isMuti = false;
        if (target == null)
            isMuti = true;
        if (isMuti) {
            if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                        DamageInfo.createDamageMatrix(AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount, false),
                        DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));

        } else {
            if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target,
                        new DamageInfo(AbstractDungeon.player, AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    public AbstractCardModifier makeCopy() {
        return new SheatheModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
