package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.LoseHyperdimensionalLinksAction;
import VUPShionMod.actions.TimeWarpAction;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeWarp extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("TimeWarp");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu10.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public TimeWarp() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 3;
        this.baseDamage = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHyperdimensionalLinksAction(this.secondaryM));

        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        addToBot(new TimeWarpAction(randomMonster, 2, this.magicNumber, this.damage, this.damageTypeForTurn));
    }

    public AbstractCard makeCopy() {
        return new TimeWarp();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
            if (p.getPower(HyperdimensionalLinksPower.POWER_ID).amount >= this.secondaryM)
                return super.canUse(p, m);
        }
        return false;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
