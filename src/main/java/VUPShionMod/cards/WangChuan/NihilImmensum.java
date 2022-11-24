package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Wangchuan.ImmuneDamagePower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.NihilImmensum2Power;
import VUPShionMod.powers.Wangchuan.NihilImmensumPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NihilImmensum extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(NihilImmensum.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc55.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public NihilImmensum() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 15;
        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ImmuneDamagePower(p)));

        if (upgraded)
            addToBot(new ApplyPowerAction(p, p, new NihilImmensum2Power(p, 1)));
        else
            addToBot(new ApplyPowerAction(p, p, new NihilImmensumPower(p, 1)));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 5)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-5);
        }
    }
}
