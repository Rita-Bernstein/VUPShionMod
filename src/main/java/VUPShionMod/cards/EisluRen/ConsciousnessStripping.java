package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.EisluRen.PlayerMinionTakeTurnAction;
import VUPShionMod.powers.Shion.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class    ConsciousnessStripping extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(ConsciousnessStripping.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ConsciousnessStripping.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public ConsciousnessStripping() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 1;
        this.magicNumber = this.baseMagicNumber = 4;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            addToBot(new SFXAction("ATTACK_HEAVY"));
            addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
            for (int i = 0; i < this.magicNumber; i++)
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE,true));

            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (monster != null && !monster.isDeadOrEscaped()) {
                        for (int i = 0; i < this.magicNumber; i++) {
                            int chance = AbstractDungeon.cardRng.random(99);
                            if (chance > 66)
                                addToBot(new ApplyPowerAction(monster, p, new WeakPower(monster, 1, false)));
                            else if (chance > 33)
                                addToBot(new ApplyPowerAction(monster, p, new VulnerablePower(monster, 1, false)));
                            else
                                addToBot(new ApplyPowerAction(monster, p, new BleedingPower(monster, p, 1)));
                        }
                    }
                }
            }

            Supplier<AbstractPower> powerToApply2 = () -> new StrengthPower(null, -this.secondaryM);
            addToBot(new ApplyPowerToAllEnemyAction(powerToApply2));
        }else {
            for (int i = 0; i < this.magicNumber; i++)
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            for (int i = 0; i < this.magicNumber; i++) {
                int chance = AbstractDungeon.cardRng.random(99);
                if (chance > 66)
                    addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false)));
                else if (chance > 33)
                    addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false)));
                else
                    addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, 1)));
            }

            addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.secondaryM)));
        }


        addToBot(new PlayerMinionTakeTurnAction());
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.target = CardTarget.ALL_ENEMY;
            this.isMultiDamage = true;
            upgradeBaseCost(0);
        }
    }
}
