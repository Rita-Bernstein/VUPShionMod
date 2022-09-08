package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ExhaustDrawPileAction;
import VUPShionMod.actions.Shion.TriggerFinFunnelPassiveAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TacticalLayout extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(TacticalLayout.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami04.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public TacticalLayout() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 008 Impact Radial", p.hb.cX, p.hb.cY,
                125.0f, 125.0f, 3.0f * Settings.scale, 2, false)));
        addToBot(new ExhaustDrawPileAction(p, 1,true));
        addToBot(new TriggerFinFunnelPassiveAction(true, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new TacticalLayout();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
