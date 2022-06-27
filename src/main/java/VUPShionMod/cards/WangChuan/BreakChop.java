package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.LoseCorGladiiAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class BreakChop extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(BreakChop.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/" + BreakChop.class.getSimpleName() + ".png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public BreakChop() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 14;
        this.baseBlock = 6;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                if(m != null)
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Sparks 041 Shot Right", m.hb.cX, m.hb.cY,
                        212.0f, 255.0f, 1.5f * Settings.scale, 2, false)));
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

                for(int i = 0;i<this.magicNumber;i++)
                addToBot(new ApplyPowerAction(m,p,new WeakPower(m,1,false)));
                break;
            case 2:
                if(m != null)
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Sparks 041 Shot Right", m.hb.cX, m.hb.cY,
                        212.0f, 255.0f, 1.5f * Settings.scale, 2, false)));
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                if (m.getIntentBaseDmg() >= 0) {
                    for(int i = 0;i<this.magicNumber;i++) {
                        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false)));
                        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false)));
                    }
                }
                addToBot(new ReducePowerAction(p,p,StiffnessPower.POWER_ID,1));
                break;
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int trueDamage = this.baseDamage;

        if (this.timesUpgraded >= 2) {
            this.baseDamage = 7;

            if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
                this.baseDamage += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        }

        super.calculateCardDamage(mo);

        this.baseDamage = trueDamage;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(m != null) {
            if (m.getIntentBaseDmg() >= 0)
                return super.canUse(p, m);
            else {
                if (this.timesUpgraded >= 2) {
                    return super.canUse(p, m);
                } else {
                    this.cantUseMessage = EXTENDED_DESCRIPTION[3];
                    return false;
                }
            }
        }else return super.canUse(p, m);
    }

    @Override
    public void onApplyCor() {
        addToBot(new DiscardToHandAction(this));

        if(this.timesUpgraded>=2)
            addToBot(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player, StiffnessPower.POWER_ID,1));
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
                upgradeBlock(3);
                upgradeMagicNumber(1);
            }

            if (this.timesUpgraded == 2) {

            }
        }
    }
}
