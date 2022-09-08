package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.EscapePlanAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ForestStalking extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(ForestStalking.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ForestStalking.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public ForestStalking() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        ExhaustiveVariable.setBaseValue(this, 10);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : DrawCardAction.drawnCards) {
                    card.freeToPlayOnce = true;
                }
                isDone = true;
            }
        }));


        int chance = 0;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            chance += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount / 5;
        }

        if (chance > 0)
            if (AbstractDungeon.cardRng.random(99) <= chance) {
                addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
            }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
