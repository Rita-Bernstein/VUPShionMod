package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Common.DoubleDamageReceivePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class BeinglessMoment extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(BeinglessMoment.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/BeinglessMoment.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST =0;

    public BeinglessMoment() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 20;
        this.magicNumber = this.baseMagicNumber  =2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new ApplyPowerAction(p,p,new VulnerablePower(p,this.magicNumber,false)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(10);
        }
    }
}