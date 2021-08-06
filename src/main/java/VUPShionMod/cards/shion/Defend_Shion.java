package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractShionCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend_Shion extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("Defend_Shion");
    public static final String IMG =  VUPShionMod.assetPath("img/cards/shion/zy02.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Defend_Shion() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTags.STARTER_DEFEND);
        this.baseBlock = 4;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void applyPowers() {
        int realBaseBlock = this.baseBlock;
        this.baseBlock += VUPShionMod.calculateTotalFinFunnelLevel();
        super.applyPowers();
        if (upgraded)
            this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0];
        else
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
        this.baseBlock = realBaseBlock;
        this.isBlockModified = this.block != this.baseBlock;
    }
}
