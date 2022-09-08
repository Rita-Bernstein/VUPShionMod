package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LunaMergo extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(LunaMergo.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc16.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public LunaMergo() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainEnergyAction(2));
        addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, this.secondaryM));
        addToBot(new ReducePowerAction(p, p, MagiamObruorPower.POWER_ID,this.secondaryM));
        if (this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, 5)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
