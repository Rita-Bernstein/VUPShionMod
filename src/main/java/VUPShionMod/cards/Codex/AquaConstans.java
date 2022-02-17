package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.ThreePowerPower;
import VUPShionMod.powers.TwoPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class AquaConstans extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("AquaConstans");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/shui.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public AquaConstans(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
        this.secondaryM =this.baseSecondaryM = 0;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    public AquaConstans() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new EnergizedPower(p,this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.secondaryM)));
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1){
                this.exhaust =false;
            }

            if (this.timesUpgraded == 2){
                upgradeBaseCost(0);
                upgradeMagicNumber(-1);
                upgradeSecondM(2);
            }
        }
    }
}
