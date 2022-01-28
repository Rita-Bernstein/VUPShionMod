package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LunaMergo extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("LunaMergo");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc16.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public LunaMergo() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 6;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ReducePowerAction(p,p, StiffnessPower.POWER_ID,this.magicNumber));
        if(this.upgraded)
        addToBot(new ApplyPowerAction(p,p,new CorGladiiPower(p,2)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.name = EXTENDED_DESCRIPTION[0];
            initializeDescription();
            initializeTitle();
        }
    }

    @Override
    protected void upgradeBaseCost(int newBaseCost) {
        int diff = this.costForTurn - this.cost;
        this.cost = newBaseCost;
        if (this.costForTurn >= 0) {
            this.costForTurn = this.cost + diff;
        }

        if (this.costForTurn < 0) {
            this.costForTurn = 0;
        }

        this.upgradedCost = true;
    }
}
