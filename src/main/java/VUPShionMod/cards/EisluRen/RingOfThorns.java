package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.powers.Wangchuan.MorsLibraquePower;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.function.Supplier;

public class RingOfThorns extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(RingOfThorns.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/RingOfThorns.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public RingOfThorns() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber)));

        Supplier<AbstractPower> powerToApply = () -> new WeakPower(null, 1, false);
        for (int i = 0; i < this.magicNumber; i++)
            addToBot(new ApplyPowerToAllEnemyAction(powerToApply));

        Supplier<AbstractPower> powerToApply2 = () -> new ConstrictedPower(null, p, this.magicNumber);
        addToBot(new ApplyPowerToAllEnemyAction(powerToApply2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
