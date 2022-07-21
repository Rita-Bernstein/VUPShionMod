package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlockThis extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(BlockThis.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc43.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public BlockThis() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 14;
        this.exhaust = true;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(4);
        }
    }
}
