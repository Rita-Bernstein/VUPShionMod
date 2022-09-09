package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.AddSansAction;
import VUPShionMod.actions.Liyezhu.ApplySinAction;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.actions.Unique.RemovePlayerDebuffAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LimpidHeart extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(LimpidHeart.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/LimpidHeart.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 2;

    public LimpidHeart() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, this.secondaryM));

        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new ApplySinAction(mo, this.magicNumber));
        }

        addToBot(new RemovePlayerDebuffAction());
        if (AbstractDungeon.player.hasPower(PsychicPower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(PsychicPower.POWER_ID).amount > 1) {
                addToBot(new AddSansAction(1));
                addToBot(new ReducePowerAction(p, p, PsychicPower.POWER_ID, 1));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(1);
        }
    }
}