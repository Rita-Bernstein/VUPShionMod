package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import VUPShionMod.powers.BadgeOfTimePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class AnastasiaPlan extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("AnastasiaPlan");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia07.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AnastasiaPlan() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(BadgeOfTimePower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, p.getPower(BadgeOfTimePower.POWER_ID).amount)));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, p.getPower(BadgeOfTimePower.POWER_ID).amount)));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, p.getPower(BadgeOfTimePower.POWER_ID).amount)));
            addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, p.getPower(BadgeOfTimePower.POWER_ID).amount)));
        }

        if (p.hasPower(BadgeOfThePaleBlueCrossPower.POWER_ID)) {
            if (upgraded)
                addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount * this.magicNumber)));
            else
                addToBot(new GainBlockAction(p, p, p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount * this.magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


}
