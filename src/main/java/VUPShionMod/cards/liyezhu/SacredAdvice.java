package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SacredAdvice extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("SacredAdvice");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz02.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SacredAdvice() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 4;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BadgeOfThePaleBlueCrossPower(p, this.magicNumber)));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainEnergyAction(2));
    }

    public AbstractCard makeCopy() {
        return new SacredAdvice();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            upgradeBlock(4);
        }
    }
}
