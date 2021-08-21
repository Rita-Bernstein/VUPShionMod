package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerFinFunnelAction;
import VUPShionMod.cards.AbstractShionCard;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.FlickerReturnToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class DefenseSystemCharging extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("DefenseSystemCharging");
    public static final String IMG =  VUPShionMod.assetPath("img/cards/shion/zy03.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public DefenseSystemCharging() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = this.block = 2;
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID));
        List<AbstractCard> cardList = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        if (cardList.size() >= 2) {
            AbstractCard card = cardList.get(cardList.size() - 2);
            if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
//                addToBot(new GainBlockAction(p, this.block));

                this.returnToHand = true;
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        DefenseSystemCharging.this.exhaust = true;
                        isDone = true;
                    }
                });
            }
        }
    }
}
