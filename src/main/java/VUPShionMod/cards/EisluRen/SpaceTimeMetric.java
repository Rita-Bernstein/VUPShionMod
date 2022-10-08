package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.DiscardAnyCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import java.util.function.Consumer;

public class SpaceTimeMetric extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(SpaceTimeMetric.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/SpaceTimeMetric.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public SpaceTimeMetric() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            Consumer<Integer> consumer = effect -> {
                addToTop(new ApplyPowerAction(p, p, new LoseDexterityPower(p, effect * 3)));
                addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, effect * 3)));
            };
            addToBot(new DiscardAnyCardAction(this.magicNumber, consumer));
        } else {
            addToBot(new DiscardAction(p, p, this.magicNumber, false));
        }

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int toDraw = magicNumber - AbstractDungeon.player.hand.size();
                if (toDraw > 0) {
                    addToTop(new DrawCardAction(toDraw, new AbstractGameAction() {
                        @Override
                        public void update() {
                            addToTop(new ApplyPowerAction(p, p, new LoseDexterityPower(p, DrawCardAction.drawnCards.size() * 3)));
                            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, DrawCardAction.drawnCards.size() * 3)));
                            isDone = true;
                        }
                    }));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeBaseCost(0);
        }
    }
}
