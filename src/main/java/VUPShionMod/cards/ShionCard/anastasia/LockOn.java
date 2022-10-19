package VUPShionMod.cards.ShionCard.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionAnastasiaCard;
import VUPShionMod.powers.Shion.LockOnPower;
import VUPShionMod.powers.Shion.LockOnPower2;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LockOn extends AbstractShionAnastasiaCard {
    public static final String ID = VUPShionMod.makeID(LockOn.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/anastasia/anastasia08.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public LockOn() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
        this.selfRetain= true;
    }

    @Override
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

        addToBot(new ApplyPowerAction(m, p, new LockOnPower(m, this.magicNumber)));
        addToBot(new ApplyPowerAction(m, p, new LockOnPower2(m, this.secondaryM)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.returnToHand = true;
        }
    }


}
