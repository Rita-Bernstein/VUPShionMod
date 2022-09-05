package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainMaxHPAction;
import VUPShionMod.powers.Liyezhu.SinPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EvilOnMe extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(EvilOnMe.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/EvilOnMe.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 2;

    public EvilOnMe() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 0;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        secondaryM = 0;
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (mo.hasPower(SinPower.POWER_ID)) {
                secondaryM += mo.getPower(SinPower.POWER_ID).amount;
                addToBot(new RemoveSpecificPowerAction(mo, p, SinPower.POWER_ID));
            }
        }

        if (p.hasPower(SinPower.POWER_ID)) {
            secondaryM += p.getPower(SinPower.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(p, p, SinPower.POWER_ID));
        }

        addToBot(new HealAction(p, p, secondaryM * this.magicNumber));

        if (this.secondaryM >= 5)
            addToBot(new GainMaxHPAction(p,secondaryM / 5));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeMagicNumber(1);
        }
    }
}