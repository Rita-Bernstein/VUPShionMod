package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Shion.TurnTriggerFinFunnelAction;
import VUPShionMod.actions.Shion.TurnTriggerFinFunnelsAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MinamiReact extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(MinamiReact.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami02.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public MinamiReact() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 10;
        this.baseDamage = 10;
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TurnTriggerFinFunnelsAction(this.magicNumber, true));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (m.getIntentBaseDmg() >= 0) {
                    addToTop(new GainShieldAction(p, magicNumber));
                } else {
                    addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                }
                isDone = true;
            }
        });
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(1);
        }
    }

}
