package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.LoseSansAction;
import VUPShionMod.powers.Liyezhu.EnsanguinedFigurePower;
import VUPShionMod.powers.Liyezhu.EnsanguinedFigurePower2;
import VUPShionMod.ui.SansMeter;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnsanguinedFigure extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(EnsanguinedFigure.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/EnsanguinedFigure.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public EnsanguinedFigure() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseSansAction(this.secondaryM));
        addToBot(new LoseHPAction(p, p, 5));
        if (!this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new EnsanguinedFigurePower(p, this.magicNumber)));
        else
            addToBot(new ApplyPowerAction(p, p, new EnsanguinedFigurePower2(p, this.magicNumber)));
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
            upgradeMagicNumber(1);
            upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}