package VUPShionMod.cards.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuickAttack extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("QuickAttack");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy08.png");

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public QuickAttack() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 3;
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.tags.add(CardTagsEnum.LOADED);
        this.exhaust = true;
        this.color = CardColor.COLORLESS;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            AbstractFinFunnel funnel = AbstractPlayerPatches.AddFields.activatedFinFunnel.get(p);
            if (funnel != null) {
                funnel.activeFire(m, this.damage, this.damageTypeForTurn);
            } else {
                this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
    }
}
