package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TransformFinFunnelAction;
import VUPShionMod.actions.Shion.TurnTriggerFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CalibrationDeployment extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(CalibrationDeployment.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami04.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public CalibrationDeployment() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.returnToHand = true;
        this.selfRetain = true;
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!FinFunnelManager.getFinFunnelList().isEmpty()) {
            addToBot(new TurnTriggerFinFunnelAction(m,true));
            addToBot(new TransformFinFunnelAction(FinFunnelManager.getSelectedFinFunnel()));
        }
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public void upgrade() {
    }
}