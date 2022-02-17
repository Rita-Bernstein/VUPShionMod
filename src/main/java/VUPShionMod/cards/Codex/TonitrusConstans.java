package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.ThreePowerPower;
import VUPShionMod.powers.TwoPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

public class TonitrusConstans extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("TonitrusConstans");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/lei.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public TonitrusConstans(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM =this.baseSecondaryM = 1;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    public TonitrusConstans() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new GainEnergyAction(this.magicNumber));
                break;
            case 1:
                addToBot(new GainEnergyAction(this.magicNumber));
                break;
            case 2:
                addToBot(new GainEnergyAction(this.magicNumber));
                addToBot(new DrawCardAction(p,this.secondaryM));
                break;
        }
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                this.exhaust = false;
            }


            if (this.timesUpgraded == 2) {
                upgradeBaseCost(0);
                upgradeSecondM(2);
                this.exhaust = true;
            }
        }
    }
}
