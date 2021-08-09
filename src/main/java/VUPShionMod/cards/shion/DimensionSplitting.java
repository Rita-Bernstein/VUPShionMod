package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.relics.DimensionSplitterAria;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import javax.smartcardio.Card;
import java.util.List;

public class DimensionSplitting extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("DimensionSplitting");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy17.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public DimensionSplitting() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.baseMagicNumber = 6;
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        List<AbstractCard> cardList = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        int ctr = 0;
        for (AbstractCard card : cardList) {
            if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
                ctr++;
            }
        }
        this.baseDamage += ctr * this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        List<AbstractCard> cardList = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        int ctr = 0;
        for (AbstractCard card : cardList) {
            if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
                ctr++;
            }
        }
        this.baseDamage += ctr * this.baseMagicNumber;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelList.get(p);
        addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        for (AbstractFinFunnel funnel : funnelList) {
            AbstractDungeon.effectList.add(new BorderFlashEffect(Color.SKY));
            AbstractDungeon.effectList.add(new SmallLaserEffect(m.hb.cX, m.hb.cY, funnel.hb.cX, funnel.hb.cY));
        }
        AbstractRelic relic = p.getRelic(DimensionSplitterAria.ID);
        if (relic != null) {
            AbstractDungeon.effectList.add(new BorderFlashEffect(Color.SKY));
            AbstractDungeon.effectList.add(new SmallLaserEffect(m.hb.cX, m.hb.cY, relic.hb.cX, relic.hb.cY));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }
}
