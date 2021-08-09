package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerAllFinFunnelAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinFunnelActive extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("FinFunnelActive");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami01.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FinFunnelActive() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TriggerAllFinFunnelAction());
    }

    public AbstractCard makeCopy() {
        return new FinFunnelActive();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
