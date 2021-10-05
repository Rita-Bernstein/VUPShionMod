package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.GainHyperdimensionalLinksAction;
import VUPShionMod.actions.TriggerFinFunnelAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.finfunnels.GravityFinFunnel;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GravityCharging extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("GravityCharging");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami16.png");
    private static final int COST = 3;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public GravityCharging() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID, this.magicNumber));
    }

    @Override
    public void onTriggerLoaded() {
        updateCost(-1);
    }

    public AbstractCard makeCopy() {
        return new GravityCharging();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
