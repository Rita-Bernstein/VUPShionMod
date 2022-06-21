package VUPShionMod.cards.ShionCard.optionCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.powers.Shion.DissectingFinFunnelUpgradePower;
import VUPShionMod.powers.Shion.GravityFinFunnelUpgradePower;
import VUPShionMod.powers.Shion.InvestigationFinFunnelUpgradePower;
import VUPShionMod.powers.Shion.PursuitFinFunnelUpgradePower;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@NoPools
public class DissectingFinFunnelUpgrade extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("DissectingUpgrade");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/colorless/DissectingFinFunnelUpgrade.png");

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = -2;

    public DissectingFinFunnelUpgrade() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);


//        vupCardSetBanner(CardRarity.RARE,TYPE);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasPower(InvestigationFinFunnelUpgradePower.POWER_ID) &&
                !p.hasPower(GravityFinFunnelUpgradePower.POWER_ID) &&
                !p.hasPower(DissectingFinFunnelUpgradePower.POWER_ID) &&
                !p.hasPower(PursuitFinFunnelUpgradePower.POWER_ID)
        )
        addToBot(new ApplyPowerAction(p, p, new DissectingFinFunnelUpgradePower(p, 5)));
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
