package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GravityImpact extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("GravityImpact");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy26.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public GravityImpact() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.baseDamage = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractFinFunnel funnel = AbstractPlayerPatches.AddFields.finFunnelManager.get(p).selectedFinFunnel;

        if (funnel != null) {
            funnel.activeFire(m, new DamageInfo(p, this.damage, this.damageTypeForTurn));
        } else {
                this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }

        addToBot(new TriggerFinFunnelAction(m, GravityFinFunnel.ID));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }
}
