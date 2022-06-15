package VUPShionMod.cards.ShionCard.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuickDefend extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("QuickDefend");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy09.png");

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;

    public QuickDefend() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = this.block = 2;
        this.secondaryM = this.baseSecondaryM = 2;
        this.tags.add(CardTagsEnum.LOADED);
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
        this.exhaust = true;

        vupCardSetBanner(CardRarity.UNCOMMON,TYPE);
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
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 008 Impact Radial", p.hb.cX, p.hb.cY,
                125.0f, 125.0f, 3.0f * Settings.scale, 2,false)));
        addToBot(new SFXAction("SHION_9"));
        applyPowers();
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID));
    }
}
