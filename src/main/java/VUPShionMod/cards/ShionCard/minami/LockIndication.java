package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerDimensionSplitterAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LockIndication extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID("LockIndication");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami03.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public LockIndication() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = MathUtils.random(1);
        switch (count) {
            case 0:
                addToBot(new SFXAction("SHION_10"));
                break;
            case 1:
                addToBot(new SFXAction("SHION_11"));
                break;
        }
        addToBot(new TriggerDimensionSplitterAction(m, this.magicNumber, true));
    }

    public AbstractCard makeCopy() {
        return new LockIndication();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
        }
    }
}
