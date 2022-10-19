package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.LoseSansAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Liyezhu.ProphecyOfDestruction2Power;
import VUPShionMod.powers.Liyezhu.ProphecyOfDestructionPower;
import VUPShionMod.ui.SansMeter;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ProphecyOfDestruction extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(ProphecyOfDestruction.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/ProphecyOfDestruction.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public ProphecyOfDestruction() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseSansAction(this.secondaryM));

        addToBot(new ApplyPowerAction(p, p, new ProphecyOfDestructionPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new ProphecyOfDestruction2Power(p, this.upgraded ? 2 : 1)));

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (SansMeter.getSans().amount < this.secondaryM) {
            cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:SansMeter").TEXT[5];
            return false;
        }

        return super.canUse(p, m);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
            upgradeBaseCost(1);
        }
    }
}