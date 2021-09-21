package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerFinFunnelAction;
import VUPShionMod.cards.AbstractShionCard;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.FlickerReturnToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class DefenseSystemCharging extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("DefenseSystemCharging");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy03.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
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
    public void postReturnToHand() {
        this.returnToHand = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 008 Impact Radial", p.hb.cX, p.hb.cY,
                125.0f, 125.0f, 3.0f * Settings.scale, 2,false)));

        addToBot(new GainBlockAction(p, this.block));
        addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID));
        List<AbstractCard> cardList = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        if (cardList.size() >= 2) {
            AbstractCard card = cardList.get(cardList.size() - 2);
            if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
                this.returnToHand = true;
            }
        }
    }


    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        List<AbstractCard> cardList = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        if (cardList.size() >= 1) {
            AbstractCard card = cardList.get(cardList.size() - 1);
            if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        }
    }
}
