package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

public class LignumConstans extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("LignumConstans");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/mu.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public LignumConstans(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.timesUpgraded = upgrades;
        this.exhaust =true;
    }

    public LignumConstans() {
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
            if (this.timesUpgraded == 1){
                this.exhaust =false;
            }


            if (this.timesUpgraded == 2){
                this.exhaust =true;
                upgradeMagicNumber(2);
            }
        }
    }
}
