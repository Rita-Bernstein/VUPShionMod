package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerFinFunnelAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class GravityLoading extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("GravityLoading");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami13.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public GravityLoading() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 008 Impact Radial", p.hb.cX, p.hb.cY,
                125.0f, 125.0f, 3.0f * Settings.scale, 2,false)));

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!mo.isDeadOrEscaped()) {
                    addToBot(new TriggerFinFunnelAction(mo, GravityFinFunnel.ID));
                    addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber));
                }
            }
    }

    public AbstractCard makeCopy() {
        return new GravityLoading();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
