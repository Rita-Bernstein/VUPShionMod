package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BombardaMagica extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(BombardaMagica.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc30.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public BombardaMagica() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 5;
        this.selfRetain = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m != null)
            addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 013 Ray Up loop", m.hb.cX, m.hb.y +680.0f*Settings.scale,
                    50.0f, 90.0f, 8.0f * Settings.scale, 2, false)));

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE));

        if (this.timesUpgraded == 2)
            addToBot(new DrawCardAction(1));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }


    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
    }




    @Override
    public void upgrade() {
        if (timesUpgraded <= 1) {
            this.upgraded = true;
            this.name = EXTENDED_DESCRIPTION[timesUpgraded];
            this.initializeTitle();
            if (timesUpgraded < 1)
                this.rawDescription = EXTENDED_DESCRIPTION[2];
            else
                this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.timesUpgraded++;
        }

        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeDamage(2);
            }

            if (this.timesUpgraded == 2) {
                upgradeDamage(2);
            }
        }
    }
}
