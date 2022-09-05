package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainMaxHPAction;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.powers.EisluRen.SpiritCloisterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LifeLinkCard extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(LifeLinkCard.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/LifeLinkCard.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public LifeLinkCard() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 12;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new SpiritCloisterPower(p)));
        if(MinionGroup.hasElfMinions()){
            addToBot(new GainMaxHPAction(MinionGroup.getElfMinion(),this.magicNumber));
        }

        addToBot(new RemoveDebuffsAction(p));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(1);
        }
    }
}
