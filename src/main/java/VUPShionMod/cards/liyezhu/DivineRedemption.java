package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.UpgradeAndZeroCostAction;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DivineRedemption extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("DivineRedemption");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz03.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DivineRedemption() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p,p,2));
        addToBot(new ApplyPowerAction(p, p, new BadgeOfThePaleBlueCrossPower(p, this.magicNumber)));
        addToBot(new UpgradeAndZeroCostAction(p,this.secondaryM));
    }

    public AbstractCard makeCopy() {
        return new DivineRedemption();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            upgradeSecondM(1);
        }
    }
}
