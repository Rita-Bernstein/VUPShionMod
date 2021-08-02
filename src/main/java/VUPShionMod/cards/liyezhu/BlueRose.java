package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import VUPShionMod.powers.MarkOfThePaleBlueCrossPower;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class BlueRose extends AbstractLiyezhuCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("BlueRose");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz09.png"); //TODO lyz15.png
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public BlueRose() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 6;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
            if (this.isBranchUpgrade()) {
                this.target = CardTarget.ALL_ENEMY;
                this.isMultiDamage = true;
                this.name = cardStrings.EXTENDED_DESCRIPTION[0];
                this.initializeTitle();
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            }
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.isBranchUpgrade()) {
            addToBot(new VFXAction(new CleaveEffect()));
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
            addToBot(new GainBlockAction(p, p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount * this.baseMagicNumber));
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!monster.isDeadOrEscaped()) {
                    addToBot(new RemoveSpecificPowerAction(monster, p, MarkOfThePaleBlueCrossPower.POWER_ID));
                }
            }
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            addToBot(new RemoveSpecificPowerAction(m, p, MarkOfThePaleBlueCrossPower.POWER_ID));
        }
        addToBot(new RemoveSpecificPowerAction(p, p, BadgeOfThePaleBlueCrossPower.POWER_ID));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        if (!this.isMultiDamage && mo != null) {
            float tmp = (float)this.baseDamage;

            if (player.hasPower(BadgeOfThePaleBlueCrossPower.POWER_ID)) {
                tmp += player.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount * this.baseMagicNumber;
            }

            if (mo.hasPower(MarkOfThePaleBlueCrossPower.POWER_ID)) {
                tmp += mo.getPower(MarkOfThePaleBlueCrossPower.POWER_ID).amount * this.baseMagicNumber;
            }

            for (AbstractRelic r : player.relics) {
                tmp = r.atDamageModify(tmp, this);
                if (this.baseDamage != (int) tmp) {
                    this.isDamageModified = true;
                }
            }

            for (AbstractPower p : player.powers) {
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseDamage != (int)tmp) {
                this.isDamageModified = true;
            }

            for (AbstractPower p : mo.powers) {
                tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
            }

            for (AbstractPower p : player.powers) {
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
            }

            for (AbstractPower p : mo.powers) {
                tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }

            this.damage = MathUtils.floor(tmp);
        } else {
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float[] tmp = new float[m.size()];

            int i;
            for(i = 0; i < tmp.length; ++i) {
                tmp[i] = (float)this.baseDamage;

                if (m.get(i).hasPower(MarkOfThePaleBlueCrossPower.POWER_ID)) {
                    tmp[i] += m.get(i).getPower(MarkOfThePaleBlueCrossPower.POWER_ID).amount * this.baseMagicNumber;
                }
            }

            Iterator var6;
            AbstractPower p;
            for(i = 0; i < tmp.length; ++i) {
                var6 = player.relics.iterator();

                while(var6.hasNext()) {
                    AbstractRelic r = (AbstractRelic)var6.next();
                    tmp[i] = r.atDamageModify(tmp[i], this);
                    if (this.baseDamage != (int)tmp[i]) {
                        this.isDamageModified = true;
                    }
                }

                for(var6 = player.powers.iterator(); var6.hasNext(); tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn, this)) {
                    p = (AbstractPower)var6.next();
                }

                tmp[i] = player.stance.atDamageGive(tmp[i], this.damageTypeForTurn, this);
                if (this.baseDamage != (int)tmp[i]) {
                    this.isDamageModified = true;
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                var6 = m.get(i).powers.iterator();

                while(var6.hasNext()) {
                    p = (AbstractPower)var6.next();
                    if (!m.get(i).isDying && !m.get(i).isEscaping) {
                        tmp[i] = p.atDamageReceive(tmp[i], this.damageTypeForTurn, this);
                    }
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                for(var6 = player.powers.iterator(); var6.hasNext(); tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn, this)) {
                    p = (AbstractPower)var6.next();
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                var6 = m.get(i).powers.iterator();

                while(var6.hasNext()) {
                    p = (AbstractPower)var6.next();
                    if (!m.get(i).isDying && !m.get(i).isEscaping) {
                        tmp[i] = p.atDamageFinalReceive(tmp[i], this.damageTypeForTurn, this);
                    }
                }
            }

            for(i = 0; i < tmp.length; ++i) {
                if (tmp[i] < 0.0F) {
                    tmp[i] = 0.0F;
                }
            }

            this.multiDamage = new int[tmp.length];

            for(i = 0; i < tmp.length; ++i) {
                if (this.baseDamage != MathUtils.floor(tmp[i])) {
                    this.isDamageModified = true;
                }

                this.multiDamage[i] = MathUtils.floor(tmp[i]);
            }

            this.damage = this.multiDamage[0];
        }
    }
}
