package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.character.Shion;
import VUPShionMod.powers.Shion.HyperdimensionalLinksPower;
import VUPShionMod.skins.SkinManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class SantaCroce extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(SantaCroce.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz08.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SantaCroce() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
        this.baseBlock = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHyperdimensionalLinksAction(this.magicNumber));
//        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.magicNumber), this.magicNumber));
        addToBot(new GainBlockAction(p, this.block));
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }


    @Override
    public void applyPowers() {
        this.baseBlock = this.magicNumber * this.secondaryM;
        if (AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID))
            this.baseBlock += AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount * this.secondaryM;
        super.applyPowers();
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeSecondM(1);
        }
    }


}
