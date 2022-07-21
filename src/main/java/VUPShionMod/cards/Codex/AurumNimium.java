package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.UpgradeAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AurumNimium extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID(AurumNimium.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/jin.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public AurumNimium(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    public AurumNimium() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new UpgradeAction(p, this.magicNumber));
                break;
            case 1:
                addToBot(new ArmamentsAction(true));
                break;
            case 2:
                addToBot(new ApotheosisAction());
                break;
        }

    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                this.isEthereal = true;
            }

            if (this.timesUpgraded == 2) {
                this.isEthereal = false;
                this.exhaust = true;
            }
        }
    }
}
