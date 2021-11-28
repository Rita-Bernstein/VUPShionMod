package VUPShionMod.cards.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerFinFunnelAction;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuickDefend extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("QuickDefend");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy09.png");

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
        this.color = CardColor.COLORLESS;

        setDisplayRarity(CardRarity.UNCOMMON);
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
