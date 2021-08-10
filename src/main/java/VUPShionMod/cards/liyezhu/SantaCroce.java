package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import VUPShionMod.powers.MarkOfThePaleBlueCrossPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class SantaCroce extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("SantaCroce");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz08.png");
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
        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.magicNumber), this.magicNumber));
        addToBot(new GainBlockAction(p, this.block));
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }


    @Override
    public void applyPowers() {
        this.baseBlock = this.magicNumber + this.secondaryM * 2;
        super.applyPowers();
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

}
