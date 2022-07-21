package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.LoseCorGladiiAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Guard extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(Guard.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc15.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Guard() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 22;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new LoseCorGladiiAction(this.secondaryM));
        addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, this.magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount >= this.secondaryM)
                return super.canUse(p, m);
        }

        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
