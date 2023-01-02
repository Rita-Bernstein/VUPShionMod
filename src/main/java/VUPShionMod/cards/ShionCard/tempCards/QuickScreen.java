package VUPShionMod.cards.ShionCard.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelPassiveAction;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.finfunnels.DissectingFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@NoPools
public class QuickScreen extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID(QuickScreen.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy10.png");

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public QuickScreen() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.secondaryM = this.baseSecondaryM = 4;
        this.tags.add(CardTagsEnum.LOADED);
        this.exhaust = true;

        vupCardSetBanner(CardRarity.UNCOMMON, TYPE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryAction(this.magicNumber));
        addToBot(new DrawCardAction(p, this.secondaryM));

        int times = this.upgraded ? 4 : 2;

        for (int i = 0; i < times; i++)
            addToBot(new TriggerFinFunnelPassiveAction(DissectingFinFunnel.ID, true, true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            upgradeSecondM(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


}
