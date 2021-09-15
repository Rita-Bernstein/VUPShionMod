package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.LoseHyperdimensionalLinksAction;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class Limit extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("Limit");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu13.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Limit() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.baseDamage = 9;

        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Sparks 090 Explosion Radial MIX", mo.hb.cX, mo.hb.cY + 20.0f * Settings.scale,
                        128.0f, 133.0f, 3.0f * Settings.scale, 2, false)));
            }
        }
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
    }

    public AbstractCard makeCopy() {
        return new Limit();
    }
//
//    @Override
//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
//            if (p.getPower(HyperdimensionalLinksPower.POWER_ID).amount >= this.magicNumber)
//                return super.canUse(p, m);
//        }
//        return false;
//    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);

        }
    }
}
