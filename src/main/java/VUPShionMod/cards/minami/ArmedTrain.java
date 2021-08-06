package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.SupportArmamentPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ArmedTrain extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("ArmedTrain");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami14.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public ArmedTrain() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.baseBlock = 4;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SupportArmamentPower(p, this.magicNumber)));
        applyPowers();
        addToBot(new GainBlockAction(p, this.block));


        this.baseDamage = 0;
        AbstractPower power = AbstractDungeon.player.getPower(SupportArmamentPower.POWER_ID);
        if (power != null) {
            this.baseDamage = power.amount * this.secondaryM;
        }

        calculateCardDamage(m);
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = 0;
        this.baseBlock = 4;

        AbstractPower power = AbstractDungeon.player.getPower(SupportArmamentPower.POWER_ID);
        if (power != null) {
            if (upgraded)
                this.baseBlock = power.amount * this.secondaryM + 8;
            else
                this.baseBlock = power.amount * this.secondaryM + 4;

            this.baseDamage = power.amount * this.secondaryM;
        }


        super.applyPowers();
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new ArmedTrain();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeSecondM(1);
        }
    }
}
