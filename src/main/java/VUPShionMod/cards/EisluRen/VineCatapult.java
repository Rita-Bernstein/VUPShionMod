package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.cards.WangChuan.AbstractWCCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.function.Supplier;

public class VineCatapult extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(VineCatapult.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/VineCatapult.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public VineCatapult() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            addToBot(new ApplyPowerAction(m, p, new ConstrictedPower(m, p, this.magicNumber)));
        } else {
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (monster != null && !monster.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(monster, p, new ConstrictedPower(monster, p, this.magicNumber)));
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
