package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.actions.Unique.RemovePlayerBuffAction;
import VUPShionMod.actions.Unique.RemovePlayerDebuffAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class SacredChop extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(SacredChop.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/SacredChop.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public SacredChop() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExpungeVFXAction(m));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(p,p,new PsychicPower(p,this.magicNumber)));
        addToBot(new RemovePlayerDebuffAction());
        addToBot(new MakeTempCardInHandAction(new Miracle(),1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            upgradeBaseCost(0);
        }
    }
}