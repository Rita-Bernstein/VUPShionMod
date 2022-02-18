package VUPShionMod.cards.ShionCard.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FunnelMatrix extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("FunnelMatrix");
    public static final String IMG = VUPShionMod.assetPath("img/cards/colorless/FunnelMatrix.png");

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public FunnelMatrix() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
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
        addToBot(new TriggerFinFunnelAction(m, InvestigationFinFunnel.ID));
        addToBot(new TriggerFinFunnelAction(m, InvestigationFinFunnel.ID));

        addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID));
        addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID));

        addToBot(new TriggerFinFunnelAction(m, PursuitFinFunnel.ID));
        addToBot(new TriggerFinFunnelAction(m, PursuitFinFunnel.ID));
        addToBot(new TriggerFinFunnelAction(m, PursuitFinFunnel.ID));
    }
}
