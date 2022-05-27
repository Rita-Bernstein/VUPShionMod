package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.powers.Shion.HyperdimensionalLinksPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlueBlade extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("BlueBlade");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz04.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public BlueBlade() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 2;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = 6;
        int amount = 0;
        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID))
            amount = p.getPower(HyperdimensionalLinksPower.POWER_ID).amount * this.magicNumber;
        this.baseDamage += amount;

        calculateCardDamage(m);

        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new SFXAction("BLUNT_FAST"));
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 016 Impact Explosion Radial", monster.hb.cX, monster.hb.cY + 0.0f * Settings.scale,
                        125.0f, 125.0f, 2.0f * Settings.scale, 2, false)));
            }
        }
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
//        addToBot(new LoseHyperdimensionalLinksAction(true));
    }

    @Override
    public void applyPowers() {
        this.baseDamage = 6;
        int amount = 0;
        if (AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID))
            amount = AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount * this.magicNumber;
        this.baseDamage += amount;
        super.applyPowers();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }
}
