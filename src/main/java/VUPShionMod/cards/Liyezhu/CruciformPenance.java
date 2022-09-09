package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.CruciformPenanceAction;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import java.util.ArrayList;

public class CruciformPenance extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(CruciformPenance.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/CruciformPenance.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;

    public CruciformPenance() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 14;
        this.magicNumber = this.baseMagicNumber = 4;
        this.secondaryM = this.baseSecondaryM = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(p, p, new PsychicPower(p, this.magicNumber)));

        addToBot(new CruciformPenanceAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.secondaryM));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }
}