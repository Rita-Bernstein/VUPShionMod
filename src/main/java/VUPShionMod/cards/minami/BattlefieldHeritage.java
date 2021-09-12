package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.AttackFromDiscardToHandAction;
import VUPShionMod.actions.RandomDiscardPileToHandAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BattlefieldHeritage extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("BattlefieldHeritage");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami12.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public BattlefieldHeritage() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.magicNumber)));
        Predicate<AbstractCard> predicate = (pr) -> pr.type == CardType.ATTACK;
        Consumer<List<AbstractCard>> callback = cards -> {
            for(AbstractCard c : cards)
                c.setCostForTurn(c.costForTurn -1);
        };

        addToBot(new MoveCardsAction(p.hand, p.discardPile, predicate, this.secondaryM, callback));
    }

    public AbstractCard makeCopy() {
        return new BattlefieldHeritage();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondM(1);
        }
    }
}
