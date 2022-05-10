package VUPShionMod.cards.ShionCard.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.MakeLoadedCardAction;
import VUPShionMod.actions.Shion.TriggerFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractShionKuroisuCard;
import VUPShionMod.cards.ShionCard.tempCards.QuickDefend;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeSlack extends AbstractShionKuroisuCard {
    public static final String ID = VUPShionMod.makeID("TimeSlack");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/kuroisu/kuroisu02.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TimeSlack() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 1;
        this.cardsToPreview = new QuickDefend();
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
        this.tags.add(CardTagsEnum.LOADED);
        ExhaustiveVariable.setBaseValue(this,6);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 008 Impact Radial", p.hb.cX, p.hb.cY,
                125.0f, 125.0f, 3.0f * Settings.scale, 2,false)));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID));
        addToBot(new MakeLoadedCardAction(new QuickDefend(),this.magicNumber,true));
//        addToBot(new MakeTempCardInDiscardAction(new QuickDefend(),this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }
}
