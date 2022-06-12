package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.SpatialTearPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class CirrocumulusChop extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(CirrocumulusChop.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/" + CirrocumulusChop.class.getSimpleName() + ".png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public CirrocumulusChop() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));


                break;
            case 2:
                if (m != null)
                    addToBot(new VFXAction(new AbstractAtlasGameEffect("Blue Line", m.hb.cX, m.hb.y + 700.0f * Settings.scale,
                            50.0f, 90.0f, 8.0f * Settings.scale, 1, false)));

                int amount = 3;
                if (p.hasPower(DexterityPower.POWER_ID)) {
                    amount += p.getPower(DexterityPower.POWER_ID).amount;
                }

                if (p.hasPower(CorGladiiPower.POWER_ID)) {
                    amount += p.getPower(CorGladiiPower.POWER_ID).amount;
                }

                addToBot(new ApplyPowerAction(m, p, new ConstrictedPower(m, p, amount)));

                break;
        }

        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ReducePowerAction(p, p, MagiamObruorPower.POWER_ID, this.secondaryM));
        addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, this.secondaryM));

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int trueDamage = this.baseDamage;

        if (this.timesUpgraded <= 1) {
            if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
                this.baseDamage += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }

        super.calculateCardDamage(mo);

        this.baseDamage = trueDamage;
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
                upgradeDamage(6);
            }

            if (this.timesUpgraded == 2) {
                upgradeMagicNumber(2);
                upgradeSecondM(2);
            }
        }
    }
}
