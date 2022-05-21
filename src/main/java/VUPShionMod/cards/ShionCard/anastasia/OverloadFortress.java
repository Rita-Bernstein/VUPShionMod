package VUPShionMod.cards.ShionCard.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractShionAnastasiaCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OverloadFortress extends AbstractShionAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("OverloadFortress");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/anastasia/anastasia12.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public OverloadFortress() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 7;
        this.baseBlock = 0;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.secondaryM; i++)
            addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractFinFunnel finFunnel = AbstractPlayerPatches.AddFields.finFunnelManager.get(p).getFinFunnel(GravityFinFunnel.ID);
                if(finFunnel !=null)
                    finFunnel.loseLevel(magicNumber);

                this.isDone = true;
            }
        });

    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        AbstractFinFunnel finFunnel = AbstractPlayerPatches.AddFields.finFunnelManager.get(p).getFinFunnel(GravityFinFunnel.ID);
        if(finFunnel != null)
            if(finFunnel.getLevel() >= this.magicNumber)
                return super.canUse(p,m);

            return  false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondM(5);
        }
    }


}
