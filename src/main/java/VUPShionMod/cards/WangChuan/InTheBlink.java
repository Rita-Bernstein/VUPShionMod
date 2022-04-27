package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class InTheBlink extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("InTheBlink");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc05.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public InTheBlink(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.timesUpgraded = upgrades;
        this.magicNumber = this.baseMagicNumber = 6;
        this.baseSecondaryM = this.secondaryM = 1;
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[12] + EXTENDED_DESCRIPTION[15];
        initializeDescription();
    }

    public InTheBlink() {
        this(0);
    }

//=================请不要动这里的代码，因为就算是写的人也不知道它是怎么运行的


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int d = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;

        switch (this.timesUpgraded) {
            case 0:
                calculateCardDamage(m);

                addToBot(new VFXAction(new AbstractAtlasGameEffect("Sparks 041 Shot Right", m.hb.cX, m.hb.cY,
                        212.0f, 255.0f, 1.5f * Settings.scale, 2, false)));
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

                addToBot(new ReducePowerAction(p, p, CorGladiiPower.POWER_ID, 1));
                if (StiffnessPower.applyStiffness())
                    addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 3)));
                break;
            case 1:
                calculateCardDamage(m);

                addToBot(new VFXAction(new AbstractAtlasGameEffect("Sparks 041 Shot Right", m.hb.cX, m.hb.cY,
                        212.0f, 255.0f, 1.5f * Settings.scale, 2, false)));
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));


                addToBot(new ReducePowerAction(p, p, CorGladiiPower.POWER_ID, 1));
                if (StiffnessPower.applyStiffness())
                    addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 3)));
                break;
            case 2:
                calculateCardDamage(m);

                addToBot(new VFXAction(new AbstractAtlasGameEffect("Sparks 041 Shot Right", m.hb.cX, m.hb.cY,
                        212.0f, 255.0f, 1.5f * Settings.scale, 2, false)));
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

                if (StiffnessPower.applyStiffness())
                    addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 3)));
                break;
        }


        if (this.timesUpgraded >= 3) {
            addBaseAoeDamage();
            doAoeDamage();

            if (StiffnessPower.applyStiffness())
                addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 3)));

            if (this.timesUpgraded >= 9)
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
//        addToBot(new SFXAction("ATTACK_HEAVY"));
//        addToBot(new VFXAction(AbstractDungeon.player, new CleaveEffect(), 0.1F));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Sparks 041 Shot Right", mo.hb.cX, mo.hb.cY,
                        212.0f, 255.0f, 1.5f * Settings.scale, 2, false)));
            }
        }
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
                upgradeMagicNumber(2);
                upgradeBaseCost(1);
            }

            if (this.timesUpgraded == 1) {
                upgradeMagicNumber(4);
            }

            if (this.timesUpgraded == 2) {
                this.target = CardTarget.ALL_ENEMY;
                this.isMultiDamage = true;
                this.magicNumber = this.baseMagicNumber = 0;
            }

            if (this.timesUpgraded >= 3)
                upgradeSecondM(1);

            this.name = EXTENDED_DESCRIPTION[this.timesUpgraded];
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
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[12] + EXTENDED_DESCRIPTION[15];
                break;
            case 1:
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[12] + EXTENDED_DESCRIPTION[15];
                break;
            case 2:
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[15];
                break;
        }

        if (this.timesUpgraded >= 3) {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[15];
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
