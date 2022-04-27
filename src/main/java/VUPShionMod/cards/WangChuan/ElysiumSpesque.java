package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class ElysiumSpesque extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("ElysiumSpesque");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc41.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public ElysiumSpesque() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new PlatedArmorPower(p,this.magicNumber)));
        addToBot(new ApplyPowerAction(p,p,new MagiamObruorPower(p,1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
