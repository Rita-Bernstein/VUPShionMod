package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.HeliumLuxquePower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.SpatialTearPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CoronaChop extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(CoronaChop.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/" + CoronaChop.class.getSimpleName() + ".png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;

    public CoronaChop() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 20;
        this.magicNumber = this.baseMagicNumber = 8;
        this.secondaryM = this.baseSecondaryM = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new ExhaustAction(1, true, false, false));
                if (p.hasPower(CorGladiiPower.POWER_ID))
                    addToBot(new ApplyPowerAction(p, p, new HeliumLuxquePower(p, p.getPower(CorGladiiPower.POWER_ID).amount)));

                addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, this.magicNumber)));
                addToBot(new ApplyStiffnessAction(this.secondaryM));
                break;
            case 1:
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new ExhaustAction(1, false, false, false));
                if (p.hasPower(CorGladiiPower.POWER_ID))
                    addToBot(new ApplyPowerAction(p, p, new HeliumLuxquePower(p, p.getPower(CorGladiiPower.POWER_ID).amount)));

                addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, this.magicNumber)));
                addToBot(new ApplyStiffnessAction(this.secondaryM));
                break;
            case 2:
                int amount = 15;
                if (p.hasPower(CorGladiiPower.POWER_ID)) {
                    amount += p.getPower(CorGladiiPower.POWER_ID).amount;
                }

                if(m != null)
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Red Line", m.hb.cX, m.hb.cY + 720.0f * Settings.scale,
                        50.0f, 90.0f, 10.0f * Settings.scale, 2, false)));

                addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, amount)));
                addToBot(new ExhaustAction(1, false, true, true));
                addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, this.magicNumber)));
                addToBot(new ApplyStiffnessAction(this.secondaryM));
                addToBot(new ApplyPowerAction(p,p,new MagiamObruorPower(p,1)));
                break;
        }
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
                upgradeDamage(5);
            }

            if (this.timesUpgraded == 2) {
                upgradeBaseCost(1);
            }
        }
    }
}
