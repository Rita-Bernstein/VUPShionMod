package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.helpers.SheatheModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Sheathe extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(Sheathe.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc04.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Sheathe() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 0;
        this.secondaryM = this.baseSecondaryM = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, this.secondaryM));

        Predicate<AbstractCard> predicate = (pr) -> (pr.type == CardType.ATTACK) && !pr.cardID.equals(BombardaMagica.ID) && !pr.cardID.equals(PhantomChop.ID);
        Consumer<List<AbstractCard>> callback = cards -> {
            for (AbstractCard c : cards) {
                c.setCostForTurn(c.costForTurn - 1);
                CardModifierManager.addModifier(c, new SheatheModifier());
                if (upgraded)
                    CardModifierManager.addModifier(c, new SheatheModifier());
            }
        };

        addToBot(new MoveCardsAction(p.hand, p.drawPile, predicate, 1, callback));
    }

    @Override
    public void applyPowers() {
        int b = this.secondaryM;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID)) {
            b += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        }

        this.baseBlock = b;

        super.applyPowers();

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.name = EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
            this.initializeDescription();
        }
    }
}
