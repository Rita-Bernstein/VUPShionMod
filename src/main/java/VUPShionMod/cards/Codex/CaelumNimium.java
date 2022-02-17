package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.GameStatsPatch;
import VUPShionMod.powers.FourAttackPower;
import VUPShionMod.powers.ThreeAttackPower;
import VUPShionMod.powers.TwoAttackPower;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleTapPower;

public class CaelumNimium extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("CaelumNimium");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/tian.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public CaelumNimium(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.timesUpgraded = upgrades;
    }

    public CaelumNimium() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new ApplyPowerAction(p,p,new TwoAttackPower(p,1)));
                break;
            case 1:
                addToBot(new ApplyPowerAction(p,p,new FourAttackPower(p,1)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(p,p,new ThreeAttackPower(p,1)));
                break;
        }
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1){
                upgradeMagicNumber(2);
            }


            if (this.timesUpgraded == 2){
                upgradeBaseCost(0);
                upgradeMagicNumber(-1);
            }

        }
    }
}