package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class InTheBlink extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("InTheBlink");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc05.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public InTheBlink(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.timesUpgraded = upgrades;
        this.magicNumber = this.baseMagicNumber = 3;
        this.baseSecondaryM = this.secondaryM = 1;
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[12] + EXTENDED_DESCRIPTION[13];
        initializeDescription();
    }

    public InTheBlink() {
        this(0);
    }

//=================请不要动这里的代码，因为就算是写的人也不知道它是怎么运行的


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            case 0:
                int d = this.magicNumber;
                if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
                    d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
                this.baseDamage = d;

                calculateCardDamage(m);

                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

                addToBot(new ReducePowerAction(p, p, CorGladiiPower.POWER_ID, 1));
                addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 1)));
                break;
            case 1:
                addBaseAoeDamage();
                doAoeDamage();
                addToBot(new ReducePowerAction(p, p, CorGladiiPower.POWER_ID, 1));
                addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 3)));
                break;
            case 2:
                addBaseAoeDamage();
                doAoeDamage();
                addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 3)));
                break;
        }


        if (this.timesUpgraded >= 3) {
            addBaseAoeDamage();
            doAoeDamage();
            addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 3)));
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
        }
    }

    private void addBaseAoeDamage() {
        addBaseAoeDamage(1);
    }

    private void addBaseAoeDamage(int scale) {
        int d = this.magicNumber;

        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d * scale;

        calculateCardDamage(null);
    }

    private void doAoeDamage() {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(AbstractDungeon.player, new CleaveEffect(), 0.1F));
        for (int i = 0; i < this.secondaryM; i++)
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
    }


    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded <= 8;
    }

    @Override
    public void upgrade() {
        if (this.timesUpgraded <= 8) {

            if (this.timesUpgraded == 0) {
                this.target = CardTarget.ALL_ENEMY;
                this.isMultiDamage = true;
                upgradeMagicNumber(1);
                upgradeBaseCost(2);
                this.rarity = CardRarity.COMMON;
                vupCardSetBanner();
            }

            if (this.timesUpgraded == 1) {
                upgradeMagicNumber(4);
                this.rarity = CardRarity.UNCOMMON;
                vupCardSetBanner();
            }

            if (this.timesUpgraded == 2) {
                this.magicNumber = this.baseMagicNumber = 0;
                this.rarity = CardRarity.RARE;
                vupCardSetBanner();
            }

            if (this.timesUpgraded >= 3)
                upgradeSecondM(1);


            this.name = EXTENDED_DESCRIPTION[this.timesUpgraded] + (this.timesUpgraded + 1);
            this.upgraded = true;
            this.timesUpgraded++;
            initializeTitle();
            setDescription();
        }
    }


    public void applyPowers() {
        int d = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;
        super.applyPowers();

    }

    private void setDescription() {
        switch (this.timesUpgraded) {
            case 0:
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[12] + EXTENDED_DESCRIPTION[13];
                break;
            case 1:
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[10] + EXTENDED_DESCRIPTION[12] + EXTENDED_DESCRIPTION[15];
                break;
            case 2:
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[10] + EXTENDED_DESCRIPTION[15];
                break;
        }

        if (this.timesUpgraded >= 3) {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[15] + EXTENDED_DESCRIPTION[18];
        }
        if (this.timesUpgraded >= 9) {
            this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[15] + EXTENDED_DESCRIPTION[18];
        }


        initializeDescription();
    }


//    @Override
//    public AbstractCard makeCopy() {
//        return new InTheBlink(this.timesUpgraded);
//    }
}
