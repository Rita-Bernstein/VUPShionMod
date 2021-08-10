package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.patches.CardTagsEnum;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeBomb extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("TimeBomb");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu04.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public TimeBomb() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 20;
        this.isMultiDamage = true;
        this.tags.add(CardTagsEnum.LOADED);
        this.exhaust = true;
        GraveField.grave.set(this,true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(8);
        }
    }
}
