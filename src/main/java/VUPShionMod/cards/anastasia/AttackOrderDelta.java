package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.powers.AttackOrderDeltaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AttackOrderDelta extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("AttackOrderDelta");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia04.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AttackOrderDelta() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.isInnate = true;
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new AttackOrderDeltaPower(p)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        addToBot(new DrawCardAction(AbstractDungeon.player,1));
    }
}
