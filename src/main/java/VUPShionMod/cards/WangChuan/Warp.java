package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.MagiamObruorPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Warp extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("Warp");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/Warp.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public Warp() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        Predicate<AbstractCard> predicate = (pr) -> pr.type == CardType.ATTACK;
        addToBot(new MoveCardsAction(p.hand, p.discardPile, predicate, 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(4);
        }
    }
}