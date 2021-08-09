package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ArmedToTheTeeth extends AbstractMinamiCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("ArmedToTheTeeth");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami15.png");
    private static final int COST = 3;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ArmedToTheTeeth() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (p.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
                    this.amount = p.getPower(HyperdimensionalLinksPower.POWER_ID).amount;
                    addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.amount), this.amount));
                    if (upgraded && getUpgradeType() == UpgradeType.BRANCH_UPGRADE)
                        addToTop(new ReducePowerAction(p, p, HyperdimensionalLinksPower.POWER_ID, (int) Math.ceil(amount * 0.5f)));
                    else
                        addToTop(new RemoveSpecificPowerAction(p, p, HyperdimensionalLinksPower.POWER_ID));
                }
                isDone = true;
            }
        });
    }

    public AbstractCard makeCopy() {
        return new ArmedToTheTeeth();
    }


    private void baseUpgrade() {
        upgradeBaseCost(2);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    private void branchUpgrade() {
        upgradeBaseCost(0);
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }
}
