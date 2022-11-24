package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.PlayerMinionTakeTurnAction;
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
import com.megacrit.cardcrawl.powers.StrengthPower;

public class PhysicalMagic extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(PhysicalMagic.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/PhysicalMagic.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public PhysicalMagic() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 9;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        int chance = 0;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            chance += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount / 10;

        }

        if (chance > 0)
            if (AbstractDungeon.cardRandomRng.random(99) <= chance) {
                addToBot(new StunMonsterAction(m, p, 1));
            }

        addToBot(new PlayerMinionTakeTurnAction());

        if (m.getIntentBaseDmg() < 0) {
            addToBot(new DrawCardAction(this.upgraded ? 2 : 1));
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.selfRetain = true;
        }
    }
}
