package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyCorGladiiAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

public class Slide extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(Slide.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc02.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;

    public Slide() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 2;
        this.tags.add(CardTags.STARTER_DEFEND);
        this.secondaryM = this.baseSecondaryM = 1;
        this.magicNumber =this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new GainBlockAction(p, this.block));

        }
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyCorGladiiAction(this.secondaryM));


        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,this.magicNumber)));
        addToBot(new ApplyPowerAction(p,p,new LoseDexterityPower(p,this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(1);
            upgradeSecondM(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.name = EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
            this.initializeDescription();
        }
    }
}
