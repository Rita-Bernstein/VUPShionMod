package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class LuxRapida extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID(LuxRapida.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/guang.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public LuxRapida(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
        this.isEthereal = true;
    }

    public LuxRapida() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (!monster.isDeadOrEscaped()) {
                    addToBot(new StunMonsterAction(monster, p, this.magicNumber));
                }
            }
        }
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeBaseCost(1);
            }

            if (this.timesUpgraded == 2) {
                upgradeMagicNumber(1);
            }

        }
    }
}
