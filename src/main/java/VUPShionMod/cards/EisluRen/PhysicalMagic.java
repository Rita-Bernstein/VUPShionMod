package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PhysicalMagic extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(PhysicalMagic.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public PhysicalMagic() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (AbstractDungeon.cardRng.random(99) < 10) {
            addToBot(new StunMonsterAction(m, p, 1));
        }

        if (m.getIntentBaseDmg() < 0) {
            addToBot(new DrawCardAction(1));
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
