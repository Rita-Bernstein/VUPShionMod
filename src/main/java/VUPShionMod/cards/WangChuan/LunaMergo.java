package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyCorGladiiAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LunaMergo extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(LunaMergo.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc16.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public LunaMergo() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 2;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainEnergyAction(1));
        if (this.upgraded)
            addToBot(new ApplyCorGladiiAction(2));

        addToBot(new RemoveSpecificPowerAction(p, p, StiffnessPower.POWER_ID));

        AbstractPower power = p.getPower(StiffnessPower.POWER_ID);
        if (power != null) {
            addToBot(new ApplyCorGladiiAction(power.amount * this.secondaryM));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
            upgradeSecondM(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
