package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.actions.Shion.TurnTriggerAllFinFunnelAction;
import VUPShionMod.actions.Shion.TurnTriggerFinFunnelsAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.function.Consumer;

public class SpeedShot extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(SpeedShot.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy11.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = -1;

    public SpeedShot() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.baseDamage = 0;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Consumer<Integer> actionConsumer = effect -> {
            AbstractFinFunnel funnel = AbstractPlayerPatches.AddFields.finFunnelManager.get(p).selectedFinFunnel;

            int times = upgraded ? effect + 2 : effect + 1;

            if (times < FinFunnelManager.getFinFunnelList().size()) {
                addToTop(new TurnTriggerFinFunnelsAction(times,true));
            } else {
                addToTop(new TurnTriggerFinFunnelsAction(times % FinFunnelManager.getFinFunnelList().size(),true));

                for (int i = 0; i < times / FinFunnelManager.getFinFunnelList().size(); i++)
                    addToTop(new TurnTriggerAllFinFunnelAction(true, true));
            }
        };
        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));

        if (this.upgraded)
            addToBot(new GainEnergyAction(1));

    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
