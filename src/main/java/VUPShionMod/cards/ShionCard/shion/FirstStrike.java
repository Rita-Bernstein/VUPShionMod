package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ChangeCostAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FirstStrike extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(FirstStrike.class.getSimpleName());
    public static final String IMG =  VUPShionMod.assetPath("img/cards/ShionCard/shion/zy21.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public FirstStrike() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 14;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(CardTags.STRIKE);
        ExhaustiveVariable.setBaseValue(this,2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        if (m != null)
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Sparks 041 Shot Right", m.hb.cX, m.hb.cY,
                212.0f, 255.0f, 1.0f * Settings.scale, 2,false)));

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ChangeCostAction(this.uuid, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new FirstStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }
}