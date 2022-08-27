package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.monsters.HardModeBoss.Shion.AbstractShionBoss;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.AbstractBossFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Shion.FinFunnelMinionEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PursuitPower extends AbstractShionPower implements HealthBarRenderPower {
    public static final String POWER_ID = VUPShionMod.makeID(PursuitPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractCreature source;

    public PursuitPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/PursuitPower128.png"), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/PursuitPower48.png"), 0, 0, 48, 48);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.owner.isPlayer)
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
        else
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        if (!this.owner.isPlayer) {
            flash();
            if (!FinFunnelManager.getFinFunnelList().isEmpty()) {
                for (AbstractFinFunnel funnel : FinFunnelManager.getFinFunnelList()) {
                    if (!this.owner.isDeadOrEscaped()) {
                        funnel.onPursuitEnemy(this.owner);
                    }
                }
            } else {
                addToBot(new VFXAction(new FinFunnelMinionEffect(this.owner, SkinManager.getSkinCharacter(0).reskinCount, 0, false)));
                addToBot(new DamageAction(this.owner,
                        new DamageInfo(null, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
            }
        }

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (this.owner.isPlayer && isPlayer) {
            flash();
            if (this.source != null && this.source instanceof AbstractShionBoss && !this.source.isDeadOrEscaped()) {
                AbstractShionBoss boss = (AbstractShionBoss) this.source;

                for (AbstractBossFinFunnel finFunnel : boss.bossFinFunnels) {
                    finFunnel.onPursuitEnemy(this.owner);
                }
            } else {
                addToBot(new VFXAction(new FinFunnelMinionEffect(this.owner, SkinManager.getSkinCharacter(0).reskinCount, 7, false)));
                addToBot(new DamageAction(this.owner,
                        new DamageInfo(null, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
            }
        }
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return new Color(1.0F, 0.5F, 0.0F, 0.0F);
    }
}
