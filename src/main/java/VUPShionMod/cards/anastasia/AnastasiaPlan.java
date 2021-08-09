package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class AnastasiaPlan extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("AnastasiaPlan");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia07.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AnastasiaPlan() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));

        if (!this.upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        initializeDescription();
    }


    public void applyPowers() {
        this.baseBlock = 0;
        if (AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID)){
            this.baseBlock = AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount;
        }

        if (this.upgraded) {
            this.baseBlock += 3;
        }
        super.applyPowers();

        if (!this.upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
