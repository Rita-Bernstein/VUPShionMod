package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Wangchuan.AcceleratorPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Accelerator extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("Accelerator");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc48.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public Accelerator() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new AcceleratorPower(p,this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
