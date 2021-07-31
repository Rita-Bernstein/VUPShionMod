package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Goodbye extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("Goodbye");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "img/cards/shion/zy18.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public Goodbye() {
        super(ID, NAME, VUPShionMod.assetPath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColorEnum.VUP_Shion_LIME, RARITY, TARGET);
        this.baseDamage = 5;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for(int i = 0; i < p.hand.size(); i++) {
                    this.addToTop(new DamageAction(m, new DamageInfo(p, Goodbye.this.damage, Goodbye.this.damageTypeForTurn), AttackEffect.FIRE));
                }

                for(int i = 0; i < p.hand.size(); i++) {
                    if (Settings.FAST_MODE) {
                        this.addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
                    } else {
                        this.addToTop(new ExhaustAction(1, true, true));
                    }
                }
                isDone = true;
            }
        });
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
