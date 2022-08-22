package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WoodBombardment extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(WoodBombardment.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/WoodBombardment.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public WoodBombardment() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 21;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        int chance = 35;//this.upgraded ? 60 : 35;
        if (AbstractDungeon.cardRng.random(99) < chance) {
            addToBot(new StunMonsterAction(m, p, 1));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
