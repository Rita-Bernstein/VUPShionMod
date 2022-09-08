package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.powers.EisluRen.AttackIncreasePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WarmUp extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(WarmUp.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/WarmUp.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public WarmUp() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainRefundChargeAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new AttackIncreasePower(p, 100)));
            addToBot(new DrawCardAction(1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(3);
        }
    }
}
