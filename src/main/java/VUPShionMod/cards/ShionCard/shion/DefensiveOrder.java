package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.powers.Shion.ChainPursuitPower;
import VUPShionMod.powers.Shion.DefensiveOrderPower;
import VUPShionMod.powers.Shion.LoseFinFunnelUpgradePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefensiveOrder extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(DefensiveOrder.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami03.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public DefensiveOrder() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.selfRetain = true;
        this.baseBlock = 0;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        addToBot(new GainBlockAction(p, p, this.block));
        if (!this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new LoseFinFunnelUpgradePower(p, 1)));
    }

    @Override
    public void applyPowers() {
        this.baseBlock = AbstractFinFunnel.calculateTotalFinFunnelLevel() * this.magicNumber;
        super.applyPowers();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
