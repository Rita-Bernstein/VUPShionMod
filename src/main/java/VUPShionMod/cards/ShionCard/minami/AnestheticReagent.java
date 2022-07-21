package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class AnestheticReagent extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(AnestheticReagent.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami10.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AnestheticReagent() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 7;
        ExhaustiveVariable.setBaseValue(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.secondaryM));
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
    }


    public AbstractCard makeCopy() {
        return new AnestheticReagent();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.currentHealth <= this.secondaryM) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
//            upgradeMagicNumber(1);
            ExhaustiveVariable.upgrade(this,1);
        }
    }
}
