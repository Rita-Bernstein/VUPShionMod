package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ExitiumMaerorisExhaustAction;
import VUPShionMod.patches.CardTagsEnum;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ExitiumMaeroris extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(ExitiumMaeroris.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public ExitiumMaeroris() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 5;
        this.selfRetain = true;
        this.tags.add(CardTagsEnum.Suffering_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExitiumMaerorisExhaustAction());
        addToBot(new ExpertiseAction(p, BaseMod.MAX_HAND_SIZE));
        addToBot(new GainEnergyAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(3);
        }
    }
}