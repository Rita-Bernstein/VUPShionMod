package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractLiyezhuCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
        this.baseBlock = 9;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHyperdimensionalLinksAction(this.magicNumber));
//        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.magicNumber),this.magicNumber));
        addToBot(new GainBlockAction(p,this.block));
    }

    public AbstractCard makeCopy() {
        return new DivineRedemption();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
//            upgradeMagicNumber(1);
        }
    }
}