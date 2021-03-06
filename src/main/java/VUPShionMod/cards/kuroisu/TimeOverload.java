package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.DenergizePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeOverload extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("TimeOverload");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu12.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TimeOverload() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 3;
        this.baseDamage = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new BadgeOfTimePower(p,this.magicNumber)));
        addToBot(new GainEnergyAction(2));
        if(upgraded)
            addToBot(new ApplyPowerAction(p,p,new DenergizePower(p,1)));
        else
            addToBot(new ApplyPowerAction(p,p,new DenergizePower(p,2)));
    }

    public AbstractCard makeCopy() {
        return new TimeOverload();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
