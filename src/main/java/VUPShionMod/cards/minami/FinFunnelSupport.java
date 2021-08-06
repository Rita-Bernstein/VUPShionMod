package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.SupportArmamentPower;
import VUPShionMod.powers.TempFinFunnelUpgradePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FinFunnelSupport extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("FinFunnelSupport");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami09.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FinFunnelSupport() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower power = p.getPower(SupportArmamentPower.POWER_ID);
        if (power != null) {
            int a = (int) Math.ceil(power.amount * 0.5f);
            int b = (int) Math.floor(power.amount * 0.25f);

            if (a > 0) {
                addToBot(new ReducePowerAction(p, p, power.ID, a));
                if (upgraded)
                    addToTop(new ApplyPowerAction(p, p, new TempFinFunnelUpgradePower(p, a)));
                else if (b > 0)
                    addToTop(new ApplyPowerAction(p, p, new TempFinFunnelUpgradePower(p, b)));
            }
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        AbstractPower power = p.getPower(SupportArmamentPower.POWER_ID);
        if (power != null)
            if (power.amount > 1) return super.canUse(p, m);

        return false;
    }

    public AbstractCard makeCopy() {
        return new FinFunnelSupport();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
