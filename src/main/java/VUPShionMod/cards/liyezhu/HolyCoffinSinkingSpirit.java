package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import VUPShionMod.powers.MarkOfThePaleBlueCrossPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;

public class HolyCoffinSinkingSpirit extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("HolyCoffinSinkingSpirit");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz11.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public HolyCoffinSinkingSpirit() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 4;
        this.baseMagicNumber = 1;
        this.isMultiDamage = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new ReaperEffect()));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                if (monster.hasPower(MarkOfThePaleBlueCrossPower.POWER_ID)) {
                    AbstractPower power = monster.getPower(MarkOfThePaleBlueCrossPower.POWER_ID);
                    addToBot(new ApplyPowerAction(p, p, new BadgeOfThePaleBlueCrossPower(p, power.amount)));
                    addToBot(new RemoveSpecificPowerAction(monster, p, power));
                }
            }
        }
        if(p.hasPower(BadgeOfThePaleBlueCrossPower.POWER_ID))
        addToBot(new HealAction(p, p, p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount * HolyCoffinSinkingSpirit.this.baseMagicNumber));
        addToBot(new RemoveSpecificPowerAction(p, p, BadgeOfThePaleBlueCrossPower.POWER_ID));

    }
}
