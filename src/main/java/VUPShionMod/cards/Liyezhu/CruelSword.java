package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.ApplySinAction;
import VUPShionMod.actions.Liyezhu.LoseSansAction;
import VUPShionMod.powers.Liyezhu.SinPower;
import VUPShionMod.ui.SansMeter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class CruelSword extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(CruelSword.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/CruelSword.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public CruelSword() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 12;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseSansAction(this.secondaryM));
        addToBot(new LoseHPAction(p, p, 5));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int count =0;
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card.type == CardType.CURSE || card.type == CardType.STATUS) {
                        count++;
                    }
                }

                for (int i = 0; i < count; i++){
                    addToTop(new ApplySinAction(m,magicNumber));
                    addToTop(new ApplyPowerAction(m,p,new WeakPower(m,magicNumber,false)));
                }

                for (int i = 0; i < count; i++) {
                    addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                }


                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card.type == CardType.CURSE || card.type == CardType.STATUS) {
                        addToTop(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
                    }
                }
                isDone = true;
            }
        });

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (SansMeter.getSans().amount < this.secondaryM) {
            cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:SansMeter").TEXT[5];
            return false;
        }

        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
        }
    }
}