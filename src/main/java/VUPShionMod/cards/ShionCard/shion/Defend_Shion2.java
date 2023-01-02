package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Shion.GainFinFunnelChargeAction;
import VUPShionMod.actions.Shion.TriggerFinFunnelPassiveAction;
import VUPShionMod.actions.Shion.TurnTriggerFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend_Shion2 extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(Defend_Shion2.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy02.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Defend_Shion2() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTags.STARTER_DEFEND);
        loadJokeCardImage(VUPShionMod.assetPath("img/cards/ShionCard/joke/zy02.png"));
        this.tags.add(CardTags.STARTER_DEFEND);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++)
            addToBot(new TriggerFinFunnelPassiveAction(m, GravityFinFunnel.ID, true));
        addToBot(new GainShieldAction(p, this.secondaryM));
        addToBot(new GainFinFunnelChargeAction(2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeMagicNumber(1);
        }
    }


}
