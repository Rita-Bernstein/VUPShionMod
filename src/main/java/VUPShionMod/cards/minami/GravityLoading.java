package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class GravityLoading extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("GravityLoading");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami13.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public GravityLoading() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.magicNumber)));

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDead && !mo.isDying)
            addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.secondaryM, false), this.secondaryM));
        }

    }

    public AbstractCard makeCopy() {
        return new GravityLoading();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            upgradeMagicNumber(1);
            upgradeSecondM(1);
        }
    }
}
