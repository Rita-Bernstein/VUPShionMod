package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Shion.AntisequencerPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.TurnObruorPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Antisequencer extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("Antisequencer");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc52.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public Antisequencer() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AntisequencerPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new TurnObruorPower(p,1)));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeMagicNumber(1);
        }
    }
}
