package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.AttackOrderGammaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class AttackOrderGamma extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("AttackOrderGamma");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia03.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AttackOrderGamma() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.isInnate = true;
        this.isEthereal = true;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new AttackOrderGammaPower(p)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }


}
