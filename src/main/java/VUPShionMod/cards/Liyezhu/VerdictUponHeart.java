package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.powers.Liyezhu.SinPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class VerdictUponHeart extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(VerdictUponHeart.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/VerdictUponHeart.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public VerdictUponHeart() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 6;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++)
            addToBot(new DuelSinAction());

        int count = 0;

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (monster != null && !monster.isDeadOrEscaped()) {
                    AbstractPower power = monster.getPower(SinPower.POWER_ID);
                    if (power != null)
                        count += power.amount;
                }
            }
        }

        if(p.hasPower(SinPower.POWER_ID))
            count += p.getPower(SinPower.POWER_ID).amount;

        if(count>0){
            addToBot(new ApplyPowerAction(p,p,new PsychicPower(p,count)));
            addToBot(new HealAction(p,p,count));
        }



    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            upgradeBaseCost(0);
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}