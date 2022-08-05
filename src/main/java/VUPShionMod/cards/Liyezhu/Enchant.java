package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Enchant extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(Enchant.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/Enchant.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public Enchant(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        this.timesUpgraded = upgrades;
    }

    public Enchant() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber)));

        if (p.hasPower(PsychicPower.POWER_ID)) {
            addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
            addToBot(new ReducePowerAction(p, p, PsychicPower.POWER_ID, 1));
        }
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Enchant(this.timesUpgraded);
    }
}