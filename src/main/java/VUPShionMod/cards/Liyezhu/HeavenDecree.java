package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HeavenDecree extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(HeavenDecree.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/HeavenDecree.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public HeavenDecree() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        if (m.getIntentDmg() > 0) {
            int damageAmount = m.getIntentBaseDmg();
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                        AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster(m, true, AbstractDungeon.cardRandomRng);
                        if (monster != null)
                            addToTop(new DamageAction(monster, new DamageInfo(p, damageAmount, damageTypeForTurn), AttackEffect.FIRE));
                    }
                    isDone = true;
                }
            });
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}