package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.GameStatsPatch;
import VUPShionMod.powers.FourAttackPower;
import VUPShionMod.powers.ThreeAttackPower;
import VUPShionMod.powers.ThreeSkillPower;
import VUPShionMod.powers.TwoSkillPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BurstPower;
import com.megacrit.cardcrawl.powers.DoubleTapPower;

public class TerraNimia extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("TerraNimia");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/di.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public TerraNimia(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.timesUpgraded = upgrades;
    }

    public TerraNimia() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new ApplyPowerAction(p,p,new TwoSkillPower(p,1)));
                break;
            case 1:
                addToBot(new ApplyPowerAction(p,p,new FourAttackPower(p,1)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(p,p,new ThreeSkillPower(p,1)));
                break;
        }
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1){
                upgradeBaseCost(2);
                upgradeMagicNumber(2);
            }


            if (this.timesUpgraded == 2){
                upgradeBaseCost(1);
                upgradeMagicNumber(-1);
            }

        }
    }
}
