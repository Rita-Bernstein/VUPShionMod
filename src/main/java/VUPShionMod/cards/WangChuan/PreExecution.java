package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class PreExecution extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("PreExecution");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc03.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public PreExecution() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 3;
        this.magicNumber = this.baseMagicNumber = 4;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, this.magicNumber)));
        if (!this.upgraded)
            if (StiffnessPower.applyStiffness())
                addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, this.secondaryM)));

        if (this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(5);
            upgradeMagicNumber(4);
            upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.name = EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
            this.initializeDescription();
        }
    }


}
