package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SeverPetal extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("SeverPetal");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc12.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public SeverPetal() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 10;
        this.magicNumber = this.baseMagicNumber = 5;
        this.secondaryM = this.baseSecondaryM = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        int d = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;

        calculateCardDamage(m);

        if (m != null)
            addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 019 Ray Up", m.hb.cX, m.hb.y + 700.0f * Settings.scale,
                    50.0f, 90.0f, 8.0f * Settings.scale, 1, false)));

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE));

        this.rawDescription = getDescription(timesUpgraded);
        initializeDescription();

        addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, this.secondaryM)));
        if (StiffnessPower.applyStiffness())
            addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 2)));

        addToBot(new DrawCardAction(1));
    }


    public void applyPowers() {
        int d = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;
        super.applyPowers();

        this.rawDescription = getDescription(timesUpgraded);
        this.rawDescription += EXTENDED_DESCRIPTION[3];
        initializeDescription();
    }


    public void onMoveToDiscard() {
        this.rawDescription = getDescription(timesUpgraded);
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = getDescription(timesUpgraded);
        this.rawDescription += EXTENDED_DESCRIPTION[3];
        initializeDescription();
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
    }

    private String getDescription(int times) {
        switch (times) {
            case 1:
                return EXTENDED_DESCRIPTION[2];
            case 2:
                return UPGRADE_DESCRIPTION;
            default:
                return DESCRIPTION;
        }
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
                upgradeMagicNumber(2);
                upgradeSecondM(2);
            }

            if (this.timesUpgraded == 2) {
                upgradeMagicNumber(6);
                upgradeSecondM(2);
            }

        }
    }
}
