package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import basemod.helpers.CardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike_Shion extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("Strike_Shion");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy01.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public Strike_Shion() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.baseDamage = 6;
        loadJokeCardImage(VUPShionMod.assetPath("img/cards/joke/zy01.png"));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractFinFunnel funnel = AbstractPlayerPatches.AddFields.activatedFinFunnel.get(p);
        if (funnel != null) {
            funnel.activeFire(m, this.damage, this.damageTypeForTurn,false);
        } else {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }
}
