package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.TranscendSoul2Power;
import VUPShionMod.powers.Liyezhu.TranscendSoulPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TranscendSoul extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(TranscendSoul.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/TranscendSoul.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public TranscendSoul() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded)
            addToBot(new ApplyPowerAction(p,p,new TranscendSoul2Power(p,this.magicNumber)));
        else
            addToBot(new ApplyPowerAction(p,p,new TranscendSoulPower(p,this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.name = EXTENDED_DESCRIPTION[0];
            initializeTitle();
        }
    }
}