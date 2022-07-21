package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

public class LignumNimium extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID(LignumNimium.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/mu.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public LignumNimium(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    public LignumNimium() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, this.magicNumber)));
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeBaseCost(1);
                upgradeMagicNumber(4);
                this.isEthereal =true;
            }


            if (this.timesUpgraded == 2) {
                upgradeMagicNumber(5);
                upgradeBaseCost(0);
                this.isEthereal =false;
            }
        }
    }
}
