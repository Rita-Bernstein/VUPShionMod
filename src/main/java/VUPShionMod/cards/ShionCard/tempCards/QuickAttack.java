package VUPShionMod.cards.ShionCard.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TurnTriggerFinFunnelsAction;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@NoPools
public class QuickAttack extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID(QuickAttack.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy08.png");

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public QuickAttack() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 1;
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.tags.add(CardTagsEnum.LOADED);
        this.exhaust = true;

        this.magicNumber = this.baseMagicNumber = 2;

        vupCardSetBanner(CardRarity.UNCOMMON,TYPE);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new TurnTriggerFinFunnelsAction(this.magicNumber,true));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
