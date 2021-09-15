package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.powers.DoubleCardPower;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AnastasiaPlan extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("AnastasiaPlan");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia07.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AnastasiaPlan() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 0;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new GainBlockAction(p, p, this.block));
//        this.rawDescription = cardStrings.DESCRIPTION;

        addToBot(new ApplyPowerAction(p,p,new DoubleCardPower(p,1)));
    }
/*

    public void applyPowers() {
        this.baseBlock = 0;
        if (AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
            this.baseBlock = AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount * this.magicNumber;
        }

//        if (this.upgraded) {
//            this.baseBlock += 3;
//        }
        super.applyPowers();

//        if (!this.upgraded) {
//            this.rawDescription = cardStrings.DESCRIPTION;
//        } else {
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//        }
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

*/
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
//            upgradeMagicNumber(1);
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            initializeDescription();
            upgradeBaseCost(0);
        }
    }

}
