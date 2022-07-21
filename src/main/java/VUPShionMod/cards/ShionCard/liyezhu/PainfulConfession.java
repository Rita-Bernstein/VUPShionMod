package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class PainfulConfession extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(PainfulConfession.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz13.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public PainfulConfession() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 5;
        this.baseMagicNumber = this.magicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 3;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new SFXAction("BLUNT_FAST"));
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 002 Impact Radial", monster.hb.cX, monster.hb.cY + 0.0f * Settings.scale,
                        125.0f, 125.0f, 2.5f * Settings.scale, 2, false)));
            }
        }

        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.baseDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
        addToBot(new DamageAction(p, new DamageInfo(p, this.baseDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));


        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(monster, p, new VulnerablePower(monster, this.baseMagicNumber, false)));
            }
        }

        addToBot(new ApplyPowerAction(p,p,new RegenPower(p,this.secondaryM)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            upgradeBaseCost(0);
        }
    }

}
