package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.SummonElfAction;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.ElfMinion;
import VUPShionMod.minions.MinionGroup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

public class ElfEnhance extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(ElfEnhance.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ElfEnhance.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public ElfEnhance() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 30;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RemoveDebuffsAction(p));
        addToBot(new SummonElfAction(new ElfMinion(1)));
        if (this.upgraded)
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (!MinionGroup.areMinionsBasicallyDead()) {
                        AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
                        if(minion !=null)
                        addToTop(new ApplyPowerAction(minion, p, new BufferPower(minion, 1)));
                    }
                    isDone = true;
                }
            });

    }

    @Override
    public void triggerAfterOtherCardPlayed(AbstractCard card) {
        if(card instanceof SynchroSummon || card instanceof LifeLinkCard)
            updateCost(-1);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeBaseCost(2);
        }
    }
}
