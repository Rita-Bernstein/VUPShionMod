package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.powers.Shion.StrikeIntegratedPower;
import VUPShionMod.powers.Shion.TrackingAnalysisPower;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TrackingAnalysis extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(TrackingAnalysis.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/anastasia/anastasia04.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public TrackingAnalysis() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new PortraitWindyPetalEffect("TeamWork"), 1.0f));
        addToBot(new ApplyPowerAction(p, p, new TrackingAnalysisPower(p)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(2);
        }
    }
}
