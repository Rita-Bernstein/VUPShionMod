package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.LoseMaxHPAction;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;

public class FullBloom extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("FullBloom");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc29.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public FullBloom(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.timesUpgraded = upgrades;
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 3;
    }

    public FullBloom() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded <= 1)
            addToBot(new LoseMaxHPAction(p, p, this.secondaryM));
        else
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.player.increaseMaxHp(secondaryM, false);
                    isDone = true;
                }
            });


        addToBot(new ApplyPowerAction(p, p, new BerserkPower(p, this.magicNumber)));
    }

    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded <= 1;
    }

    @Override
    public void upgrade() {
        if (this.timesUpgraded <= 1) {
            this.upgraded = true;
            this.timesUpgraded++;
            if (this.timesUpgraded == 1) {
                this.name = this.name + "+";
                this.rawDescription = UPGRADE_DESCRIPTION;
                initializeTitle();
                initializeDescription();
                this.isInnate = true;

                upgradeMagicNumber(1);
                upgradeSecondM(-1);
            }

            if (this.timesUpgraded == 2) {
                this.name = EXTENDED_DESCRIPTION[0];
                this.rawDescription = EXTENDED_DESCRIPTION[1];
                initializeTitle();
                initializeDescription();
                upgradeSecondM(-1);
            }


        }
    }
}
