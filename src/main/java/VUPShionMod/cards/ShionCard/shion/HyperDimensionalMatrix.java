package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.EnduranceInitiationAction;
import VUPShionMod.actions.Shion.HyperDimensionalMatrixAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.powers.Shion.HyperdimensionalLinksPower;
import VUPShionMod.skins.SkinManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class HyperDimensionalMatrix extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(HyperDimensionalMatrix.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/anastasia/anastasia04.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public HyperDimensionalMatrix() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.selfRetain = true;
        this.magicNumber = this.baseMagicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("SHION_20"));
        addToBot(new HyperDimensionalMatrixAction(this.magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
            if (p.getPower(HyperdimensionalLinksPower.POWER_ID).amount >= this.magicNumber)
                return super.canUse(p, m);
        }

        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(-1);
        }
    }

}
