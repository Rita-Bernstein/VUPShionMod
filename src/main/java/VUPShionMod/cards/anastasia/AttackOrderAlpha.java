package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.powers.AttackOrderAlphaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class AttackOrderAlpha extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("AttackOrderAlpha");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia01.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AttackOrderAlpha() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.isInnate = true;
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new AttackOrderAlphaPower(p)));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        addToBot(new DrawCardAction(AbstractDungeon.player,1));
    }



    @Override
    public void upgrade() {

    }


}
