package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ExhaustDrawPileAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class BackToEarth extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(BackToEarth.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public BackToEarth() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (int i = AbstractDungeon.player.masterDeck.group.size() - 1; i >= 0; i--) {
                    if ((AbstractDungeon.player.masterDeck.group.get(i)).type == AbstractCard.CardType.CURSE &&
                            !(AbstractDungeon.player.masterDeck.group.get(i)).inBottleFlame &&
                            !(AbstractDungeon.player.masterDeck.group.get(i)).inBottleLightning) {

                        AbstractDungeon.effectList.add(new PurgeCardEffect(AbstractDungeon.player.masterDeck.group.get(i)));
                        AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.player.masterDeck.group.get(i));
                    }
                }
                isDone = true;
            }
        });

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                    if (card.type == CardType.CURSE || card.type == CardType.STATUS)
                        addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
                }

                for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    if (card.type == CardType.CURSE || card.type == CardType.STATUS)
                        addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
                }

                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card.type == CardType.CURSE || card.type == CardType.STATUS)
                        addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                }
                isDone = true;
            }
        });

        addToBot(new RemoveDebuffsAction(p));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.selfRetain = true;
        }
    }
}
