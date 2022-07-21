package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Codex.TwoSkillPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TerraRapida extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID(TerraRapida.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/di.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public TerraRapida(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.timesUpgraded = upgrades;
        this.exhaust =true;
    }

    public TerraRapida() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new ApplyPowerAction(p,p,new TwoSkillPower(p,1)));
                break;
            case 1:
                addToBot(new ApplyPowerAction(p,p,new TwoSkillPower(p,1)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(p,p,new TwoSkillPower(p,1)));
                break;
        }
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                this.exhaust =false;
                this.isEthereal = true;
            }

            if (this.timesUpgraded == 2){
                this.isEthereal =false;
                upgradeBaseCost(0);
            }

        }
    }
}
