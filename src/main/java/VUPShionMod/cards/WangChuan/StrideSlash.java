package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StrideSlash extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(StrideSlash.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc07.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public StrideSlash() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 10;
        this.magicNumber = this.baseMagicNumber = 5;
        this.baseSecondaryM = this.secondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, this.magicNumber)));

        if(this.timesUpgraded>=2)
        if (p.hasPower(CorGladiiPower.POWER_ID)) {
            int temp = this.baseDamage;
            this.baseDamage = p.getPower(CorGladiiPower.POWER_ID).amount + this.magicNumber;
            calculateCardDamage(m);
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            this.baseDamage = temp;
        }

        addToBot(new DrawCardAction(1));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
    }


    @Override
    public void upgrade() {
        if (timesUpgraded <= 1) {
            this.upgraded = true;
            this.name = EXTENDED_DESCRIPTION[timesUpgraded];
            this.initializeTitle();
            if (timesUpgraded < 1)
                this.rawDescription = EXTENDED_DESCRIPTION[2];
            else
                this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.timesUpgraded++;
        }

        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeDamage(-2);
            }

            if (this.timesUpgraded == 2) {
                upgradeDamage(1);
                upgradeMagicNumber(2);
                upgradeBaseCost(0);
            }

        }
    }
}
