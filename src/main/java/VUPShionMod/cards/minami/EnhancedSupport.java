package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerFinFunnelAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.EnhancedWeaponPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EnhancedSupport extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("EnhancedSupport");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami11.png");
    private static final int COST = -1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public EnhancedSupport() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += ChemicalX.BOOST;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (this.upgraded) {
            effect++;
        }
        if (effect > 0) {
            addToBot(new TriggerFinFunnelAction(m));
            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
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
