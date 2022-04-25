package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.StiffnessPower;
import VUPShionMod.util.SheatheModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Sheathe extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("Sheathe");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc04.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Sheathe() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 3;
        this.secondaryM = this.baseSecondaryM = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, this.secondaryM));

        Predicate<AbstractCard> predicate = (pr) -> pr.type == CardType.ATTACK;
        Consumer<List<AbstractCard>> callback = cards -> {
            for (AbstractCard c : cards) {
                c.setCostForTurn(c.costForTurn - 1);
                CardModifierManager.addModifier(c,new SheatheModifier());
            }
        };

        addToBot(new MoveCardsAction(p.hand, p.drawPile, predicate, 1, callback));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.name = EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
            this.initializeDescription();
        }
    }
}
