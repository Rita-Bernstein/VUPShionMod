package VUPShionMod.finfunnels;

import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.actions.Shion.MoveFinFunnelAction;
import VUPShionMod.actions.Shion.MoveFinFunnelSelectedEffectAction;
import VUPShionMod.character.Shion;
import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.LoseFinFunnelUpgradePower;
import VUPShionMod.powers.TempFinFunnelUpgradePower;
import VUPShionMod.relics.DimensionSplitterAria;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.List;

public abstract class AbstractFinFunnel {
    public String id;
    public String name;
    public String description;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public float muzzle_X = 0.0F;
    public float muzzle_Y = 0.0F;
    public Hitbox hb;
    protected float fontScale;
    protected Bone body;
    protected Bone muzzle;

    public int level;
    public int effect;

    public AbstractFinFunnel(String id) {
        this.hb = new Hitbox(192.0F * Settings.scale, 96.0F * Settings.scale);
        this.fontScale = 0.7F;
        if (AbstractDungeon.player != null) {
            this.cX = AbstractDungeon.player.hb.cX;
            this.cY = AbstractDungeon.player.hb.cY;
        }
        this.id = id;
        this.name = CardCrawlGame.languagePack.getOrbString(id).NAME;
    }

    public AbstractFinFunnel setPosition(float cX, float cY) {
        addToBot(new MoveFinFunnelAction(this, cX, cY));
        return this;
    }

    public abstract void updateDescription();

    public void atTurnStart() {
    }

    public void upgradeLevel(int amount) {
    }

    public void loseLevel(int amount) {
    }

    public void setLevel(int amount) {
        this.level = amount;
    }

    public int getLevel() {
        return this.level;
    }

    public void onPursuitEnemy(AbstractCreature target) {
        onPursuitEnemy(target, 1);
    }

    public void onPursuitEnemy(AbstractCreature target, int loop) {
    }

    public abstract void fire(AbstractCreature target, int damage, DamageInfo.DamageType type, int loopTimes);

    public void fire(AbstractCreature target, int damage, DamageInfo.DamageType type) {
        this.fire(target, damage, type, 1);
    }

    public void activeFire(AbstractCreature target, int damage, DamageInfo.DamageType type) {
        this.activeFire(target, damage, type, true, 1);
    }

    public void activeFire(AbstractCreature target, int damage, DamageInfo.DamageType type, boolean triggerPassive) {
        this.activeFire(target, damage, type, triggerPassive, 1);
    }

    public void activeFire(AbstractCreature target, int damage, DamageInfo.DamageType type, int loopTimes) {
        this.activeFire(target, damage, type, true, loopTimes);
    }

    public abstract void activeFire(AbstractCreature target, int damage, DamageInfo.DamageType type, boolean triggerPassive, int loopTimes);


    public void update() {
        this.hb.update();

        if (InputHelper.justClickedLeft && this.hb.hovered) {
            this.hb.clickStarted = true;
        }

        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            updateDescription();
            TipHelper.renderGenericTip(this.cX + 96.0F * Settings.scale, this.cY + 64.0F * Settings.scale, this.name, this.description);
            if (this.hb.clicked && AbstractPlayerPatches.AddFields.activatedFinFunnel.get(AbstractDungeon.player) != this) {
                this.hb.clicked = false;
                if (EnergyPanelPatches.energyUsedThisTurn > 0) {
                    EnergyPanelPatches.energyUsedThisTurn--;
                    addToBot(new MoveFinFunnelSelectedEffectAction(FinFunnelSelectedEffect.instance, this));
                }
            }
        }

        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }

    public void updatePosition(Skeleton skeleton) {
    }

    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        this.renderText(sb);
        this.hb.render(sb);
    }


    protected void renderText(SpriteBatch sb) {
        if (!AbstractDungeon.player.isDeadOrEscaped())
            if (AbstractDungeon.player.flipHorizontal) {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.level),
                        this.cX + 24.0F * Settings.scale, this.cY - 12.0F * Settings.scale,
                        new Color(0.2F, 1.0F, 1.0F, 1.0F), this.fontScale);
            } else {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.level),
                        this.cX - 24.0F * Settings.scale, this.cY - 12.0F * Settings.scale,
                        new Color(0.2F, 1.0F, 1.0F, 1.0F), this.fontScale);
            }
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }


    public void playFinFunnelAnimation(String id) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player instanceof Shion) {
                    ((Shion) AbstractDungeon.player).playFinFunnelAnimation(id);
                }
                isDone = true;
            }
        });
        addToBot(new CustomWaitAction(0.25f));
    }


    public abstract int getFinalEffect();

    public int getFinalDamage() {
        return (this.level - 1) / 2 + 1;
    }


    public static int calculateTotalFinFunnelLevel() {
        int ret = 0;
        if (AbstractDungeon.player != null) {
            List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player);
            for (AbstractFinFunnel funnel : funnelList) {
                ret += funnel.getLevel();
            }
            AbstractRelic relic = AbstractDungeon.player.getRelic(DimensionSplitterAria.ID);
            if (relic != null) {
                ret += relic.counter;
            }

            AbstractPower power = AbstractDungeon.player.getPower(TempFinFunnelUpgradePower.POWER_ID);
            if (power != null) {
                ret += power.amount;
            }

            if (AbstractDungeon.player.hasPower(LoseFinFunnelUpgradePower.POWER_ID))
                ret = 0;
        }

        return ret;
    }
}
