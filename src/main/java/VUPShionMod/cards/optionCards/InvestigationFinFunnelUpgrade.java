package VUPShionMod.cards.optionCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.powers.GravityFinFunnelUpgradePower;
import VUPShionMod.powers.InvestigationFinFunnelUpgradePower;
import VUPShionMod.powers.PursuitFinFunnelUpgradePower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InvestigationFinFunnelUpgrade extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("InvestigationUpgrade");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG = VUPShionMod.assetPath("img/cards/colorless/InvestigationFinFunnelUpgrade.png");

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = -2;

    public InvestigationFinFunnelUpgrade() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.color = CardColor.COLORLESS;

        setDisplayRarity(CardRarity.RARE);
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
                !p.hasPower(PursuitFinFunnelUpgradePower.POWER_ID)
        )
            addToBot(new ApplyPowerAction(p, p, new InvestigationFinFunnelUpgradePower(p, 5)));
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
