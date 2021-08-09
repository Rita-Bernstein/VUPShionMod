package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnergyReserve extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("EnergyReserve");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia09.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public EnergyReserve() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 40;
        this.secondaryM = this.baseSecondaryM = 3;
        this.tags.add(CardTagsEnum.LOADED);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(2));
        addToBot(new DrawCardAction(p,this.magicNumber));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(10);
        }
    }


}
