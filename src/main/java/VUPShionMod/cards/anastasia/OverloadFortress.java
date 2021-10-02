package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Predicate;

public class OverloadFortress extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("OverloadFortress");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia12.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public OverloadFortress() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 5;
        this.baseBlock = 0;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractFinFunnel finFunnel : AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player)) {
                    if (finFunnel.id.equals(GravityFinFunnel.ID))
                        finFunnel.loseLevel(magicNumber);
                }
                this.isDone = true;
            }
        });
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        for (AbstractFinFunnel finFunnel : AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player)) {
            if (finFunnel.id.equals(GravityFinFunnel.ID))
                this.baseBlock = finFunnel.getLevel() * this.secondaryM;
        }

        super.applyPowers();

        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractFinFunnel finFunnel : AbstractPlayerPatches.AddFields.finFunnelList.get(p)) {
            if (finFunnel.id.equals(GravityFinFunnel.ID))
                if (finFunnel.getLevel() < this.magicNumber) {
                    return false;
                }
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
        }
    }


}
