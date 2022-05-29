package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.powers.Liyezhu.SinPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VerdictUponHeart extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(VerdictUponHeart.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/VerdictUponHeart.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 2;

    public VerdictUponHeart() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++)
            addToBot(new DuelSinAction());

        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if(mo != null)
            addToBot(new RemoveSpecificPowerAction(mo, p, SinPower.POWER_ID));
        }

        if(!p.isDeadOrEscaped())
        addToBot(new RemoveSpecificPowerAction(p, p, SinPower.POWER_ID));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }
}