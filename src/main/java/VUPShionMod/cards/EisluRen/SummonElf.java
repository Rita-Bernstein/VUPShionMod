package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.SummonElfAction;
import VUPShionMod.cards.WangChuan.AbstractWCCard;
import VUPShionMod.minions.ElfMinion;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SummonElf extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(SummonElf.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/SummonElf.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public SummonElf() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        loadJokeCardImage("VUPShionMod/img/cards/EisluRen/beta/SummonElf.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
            addToBot(new RemoveDebuffsAction(p));
        addToBot(new SummonElfAction(new ElfMinion(0)));
    }


    @Override
    public void triggerAfterOtherCardPlayed(AbstractCard card) {
        if (card instanceof SynchroSummon || card instanceof LifeLinkCard)
            updateCost(-1);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(1);
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
