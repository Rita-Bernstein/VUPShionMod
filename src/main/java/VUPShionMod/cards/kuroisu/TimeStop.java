package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.LoadCardDiscardPileToTopOfDeckAction;
import VUPShionMod.cards.AbstractKuroisuCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeStop extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("TimeStop");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu03.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public TimeStop() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new StunMonsterAction(m, p, 1));
        addToBot(new LoadCardDiscardPileToTopOfDeckAction(p, upgraded));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
