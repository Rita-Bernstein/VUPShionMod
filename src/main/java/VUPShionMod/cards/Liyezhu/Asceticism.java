package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.AddSansAction;
import VUPShionMod.powers.Liyezhu.Asceticism2Power;
import VUPShionMod.powers.Liyezhu.AsceticismPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Asceticism extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(Asceticism.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/Asceticism.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public Asceticism() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddSansAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new AsceticismPower(p, 2)));
        if (this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new Asceticism2Power(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}