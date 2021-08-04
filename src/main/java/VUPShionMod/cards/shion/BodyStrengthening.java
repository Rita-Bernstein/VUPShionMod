package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractShionCard;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class BodyStrengthening extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("BodyStrengthening");
    public static final String IMG =  VUPShionMod.assetPath("img/cards/shion/zy22.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public BodyStrengthening() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.baseMagicNumber = this.magicNumber = 3;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = MathUtils.random(3);
        switch (count){
            case 0:
                addToBot(new SFXAction("SHION_1"));
                break;
            case 1:
                addToBot(new SFXAction("SHION_2"));
                break;
            case 2:
                addToBot(new SFXAction("SHION_6"));
                break;
        }

        CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
        CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
        AbstractDungeon.effectsQueue.add(new LightningEffect(m.hb.cX, m.hb.cY));
        addToBot(new FeedAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.baseMagicNumber));
        for(int j = 0; j < 10; ++j) {
            this.addToBot(new VFXAction(new FlyingOrbEffect(m.hb.cX, m.hb.cY)));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += VUPShionMod.calculateTotalFinFunnelLevel();
        super.calculateCardDamage(mo);
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += VUPShionMod.calculateTotalFinFunnelLevel();
        super.applyPowers();
        this.magicNumber = this.baseMagicNumber;
        if (this.upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
}
