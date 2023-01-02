package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyCorGladiiAction;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.PreExecutionPower;
import VUPShionMod.powers.Wangchuan.PreExecutionPower2;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class PreExecution extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(PreExecution.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc03.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public PreExecution() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 2;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyCorGladiiAction(this.magicNumber));
        addToBot(new GainEnergyAction(1));

        addToBot(new ApplyPowerAction(p, p, new PreExecutionPower(p, 1)));


        if (!this.upgraded)
            addToBot(new ApplyStiffnessAction(this.secondaryM));
        else {
            addToBot(new ApplyPowerAction(p, p, new PreExecutionPower2(p, 1)));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.name = EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
            this.initializeDescription();
        }
    }


}
