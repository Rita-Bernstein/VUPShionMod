package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Whisk extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(Whisk.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public Whisk() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DuelSinAction());
        addToBot(new ApplyPowerAction(m,p,new WeakPower(m,this.magicNumber,false)));
        addToBot(new ApplyPowerAction(p,p,new PsychicPower(p,this.secondaryM)));
        if(this.upgraded)
            addToBot(new DrawCardAction(1));
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