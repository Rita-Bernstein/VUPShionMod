package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.powers.DenergizePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.ApplyBulletTimeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OverspeedField extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("OverspeedField");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu14.png");
    private static final int COST = 3;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public OverspeedField() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DenergizePower(p, 2)));
        addToBot(new ApplyBulletTimeAction());
    }

    public AbstractCard makeCopy() {
        return new OverspeedField();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
