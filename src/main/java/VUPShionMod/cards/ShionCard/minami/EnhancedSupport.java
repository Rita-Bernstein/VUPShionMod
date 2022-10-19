package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.actions.Shion.TriggerFinFunnelPassiveAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.function.Consumer;

public class EnhancedSupport extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(EnhancedSupport.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami11.png");
    private static final int COST = -1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public EnhancedSupport() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 008 Impact Radial", p.hb.cX, p.hb.cY,
                125.0f, 125.0f, 3.0f * Settings.scale, 2, false)));

        Consumer<Integer> actionConsumer = effect -> {
            addToTop(new GainHyperdimensionalLinksAction(effect));
            addToTop(new TriggerFinFunnelPassiveAction(m, upgraded ? effect + 2 : effect + 1));
        };
        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));


    }

    public AbstractCard makeCopy() {
        return new EnhancedSupport();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
