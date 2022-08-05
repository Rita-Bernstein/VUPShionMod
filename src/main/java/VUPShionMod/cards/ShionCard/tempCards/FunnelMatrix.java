package VUPShionMod.cards.ShionCard.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelPassiveAction;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@NoPools
public class FunnelMatrix extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID(FunnelMatrix.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/colorless/FunnelMatrix.png");

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public FunnelMatrix() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.exhaust = true;
        vupCardSetBanner(CardRarity.UNCOMMON,TYPE);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 039 Radial Transition", m.hb.cX, m.hb.cY + 50.0f * Settings.scale,
                    80.0f, 60.0f, 10.0f * Settings.scale, 2, false)));

        for (AbstractFinFunnel funnel : AbstractPlayerPatches.AddFields.finFunnelManager.get(p).finFunnelList) {
            addToBot(new TriggerFinFunnelPassiveAction(m, funnel.id,true));
        }
    }
}
