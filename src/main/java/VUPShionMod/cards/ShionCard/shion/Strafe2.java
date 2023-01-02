package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainFinFunnelChargeAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strafe2 extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(Strafe2.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy15.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public Strafe2() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.baseDamage = 9;
        this.magicNumber = this.baseMagicNumber = 3;
        this.isMultiDamage = true;
        loadJokeCardImage(VUPShionMod.assetPath("img/cards/ShionCard/joke/zy15.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractFinFunnel funnel = AbstractPlayerPatches.AddFields.finFunnelManager.get(p).selectedFinFunnel;
        if (funnel != null) {
            funnel.activeFire(m, this.baseDamage, true, 1);
        } else {
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage,
                    this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        }

        addToBot(new GainFinFunnelChargeAction(this.magicNumber));

    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
