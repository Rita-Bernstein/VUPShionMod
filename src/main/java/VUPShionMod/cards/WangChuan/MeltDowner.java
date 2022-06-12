package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.DecreaseHPAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.SpatialTearPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MeltDowner extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(MeltDowner.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/" + MeltDowner.class.getSimpleName() + ".png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public MeltDowner() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 4;
        this.secondaryM = this.baseSecondaryM = 10;
        this.isMultiDamage = true;
        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded == 2) {
            addToBot(new DecreaseHPAction(p, p.currentHealth / 4));
            addToBot(new ExpertiseAction(p, BaseMod.MAX_HAND_SIZE));
        } else {
            addToBot(new DecreaseHPAction(p, this.secondaryM));
        }

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 010 Charge Impact Up", mo.hb.cX, mo.hb.cY + 720.0f * Settings.scale,
                        50.0f, 90.0f, 10.0f * Settings.scale, 2, false)));
            }
        }
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.currentHealth >= p.currentHealth - secondaryM && this.timesUpgraded <= 1)
            return super.canUse(p, m);

        if (p.currentHealth >= p.currentHealth / 4 && this.timesUpgraded > 1)
            return super.canUse(p, m);

        this.cantUseMessage = EXTENDED_DESCRIPTION[3];
        return false;

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = 0;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            this.baseDamage = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount * this.magicNumber;
        super.calculateCardDamage(mo);
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
                upgradeMagicNumber(2);
            }

            if (this.timesUpgraded == 2) {
                upgradeMagicNumber(4);
            }
        }
    }
}
