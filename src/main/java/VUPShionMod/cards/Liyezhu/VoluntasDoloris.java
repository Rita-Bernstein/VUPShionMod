package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VoluntasDoloris extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(VoluntasDoloris.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public VoluntasDoloris() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 10;
        this.secondaryM = this.baseSecondaryM = 1;
        this.tags.add(CardTagsEnum.Suffering_CARD);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p,p,this.baseMagicNumber));
        addToBot(new GainEnergyAction(1));
        addToBot(new DrawCardAction(this.secondaryM));
        addToBot(new MakeTempCardInDiscardAction(new VoluntasDoloris(), 1));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}