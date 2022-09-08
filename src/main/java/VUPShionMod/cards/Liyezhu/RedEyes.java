package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.LoseSansAction;
import VUPShionMod.ui.SansMeter;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.SkillFromDeckToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class RedEyes extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(RedEyes.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/RedEyes.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public RedEyes() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 1;

        this.exhaust = true;

        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseSansAction(this.secondaryM));
        addToBot(new LoseHPAction(p, p, 5));

        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber)));
        addToBot(new ApplyPowerAction(m, p, new DexterityPower(m, -this.magicNumber)));
        addToBot(new LoseHPAction(m, p, this.secondaryM));

        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber)));
        addToBot(new HealAction(p, p, this.upgraded ? 15 : 10));


        addToBot(new SkillFromDeckToHandAction(1));
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
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}