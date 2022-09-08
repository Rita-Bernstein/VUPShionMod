package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

public class MagicTransform extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(MagicTransform.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/MagicTransform.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = -1;

    public MagicTransform() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Consumer<Integer> actionConsumer = effect -> {
            addToTop(new GainRefundChargeAction(upgraded ? (4 * effect + 3) : (4 * effect + 1)));
        };
        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));
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
