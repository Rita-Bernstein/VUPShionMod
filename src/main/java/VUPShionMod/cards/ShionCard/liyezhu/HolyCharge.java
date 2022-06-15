package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HolyCharge extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("HolyCharge");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz05.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public HolyCharge() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 15;
        this.magicNumber = this.baseMagicNumber = 3;
        this.selfRetain = true;
    }

    @Override
    public void onRetained() {
        addToBot(new ReduceCostAction(this));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHyperdimensionalLinksAction(this.magicNumber));
//        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.magicNumber),this.magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                upgradeMagicNumber(1);
                isDone = true;
            }
        });

        addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 005 Impact Radial", m.hb.cX, m.hb.cY,
                125.0f, 125.0f, 2.0f * Settings.scale, 2, false)));

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            upgradeDamage(6);
        }
    }
}
