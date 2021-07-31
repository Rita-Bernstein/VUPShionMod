package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerAllFinFunnelAction;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.SupportArmamentPower;
import VUPShionMod.powers.TempFinFunnelUpgradePower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FinFunnelSupport extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("FinFunnelSupport");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami09.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FinFunnelSupport() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

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
