package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.DamageAndGainBlockAction;
import VUPShionMod.actions.Shion.MoveFinFunnelSelectedEffectAction;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.Shion.*;
import VUPShionMod.relics.Shion.DimensionSplitterAria;
import VUPShionMod.vfx.FinFunnelBeamEffect;
import VUPShionMod.vfx.FinFunnelSmallLaserEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.TintEffect;

import java.util.List;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

public abstract class AbstractFinFunnel {
    public String id;
    public String name;
    public String description;

    public boolean canUse = false;

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

    protected TextureAtlas atlas;
    protected Skeleton skeleton;
    public AnimationState state;
    protected AnimationStateData stateData;

    protected TintEffect tint = new TintEffect();

    public AbstractFinFunnel(String id) {
        this.hb = new Hitbox(192.0F * Settings.scale, 96.0F * Settings.scale);
        this.fontScale = 0.7F;

        if (AbstractDungeon.player != null) {
            this.cX = AbstractDungeon.player.hb.cX;
            this.cY = AbstractDungeon.player.hb.cY;
        }
        this.id = id;
        this.name = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(id)).NAME;
    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);

        json.setScale(Settings.scale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }


    public abstract void updateDescription();

    public void atTurnStart() {
    }

    public void preBattlePrep() {
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


    public void activeFire(AbstractCreature target, int damage, DamageInfo.DamageType type, boolean triggerPassive, int loopTimes) {
        if (AbstractDungeon.player.hasPower(AttackOrderSpecialPower.POWER_ID)) {
            addToBot(new VFXAction(new FinFunnelBeamEffect(this), 0.4f));
            for (int i = 0; i < loopTimes; i++)
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), type, AbstractGameAction.AttackEffect.FIRE));

            if (triggerPassive) {
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        if (!mo.isDeadOrEscaped()) {
                            powerToApply(mo);
                        }
                    }
            }

        } else {
            addToBot(new VFXAction(new FinFunnelSmallLaserEffect(this, target), 0.3F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            for (int i = 0; i < loopTimes; i++) {
                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, type), AbstractGameAction.AttackEffect.FIRE));
            }

            if (triggerPassive) {
                powerToApply(target);
            }
        }
    }


    public void fire(AbstractCreature target, int damage, DamageInfo.DamageType type, int loopTimes) {
        if (target.isDeadOrEscaped()) return;
        if (AbstractDungeon.player.hasPower(AttackOrderBetaPower.POWER_ID)) {
            addToBot(new VFXAction(AbstractDungeon.player, new FinFunnelBeamEffect(this, AbstractDungeon.player.flipHorizontal), 0.4F));
            for (int i = 0; i < loopTimes; i++)
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), type, AbstractGameAction.AttackEffect.FIRE));
            if (this.level > 0) {
                for (int i = 0; i < loopTimes; i++)
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        powerToApply(mo);
                    }
            }
        } else {
            addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));

            if (AbstractDungeon.player.hasPower(AttackOrderAlphaPower.POWER_ID))
                for (int i = 0; i < loopTimes; i++)
                    addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage * 5, type)));
            else if (AbstractDungeon.player.hasPower(AttackOrderDeltaPower.POWER_ID))
                for (int i = 0; i < loopTimes; i++)
                    addToBot(new DamageAndGainBlockAction(target, new DamageInfo(AbstractDungeon.player, damage, type), 1.0f));
            else
                for (int i = 0; i < loopTimes; i++)
                    addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, type)));


            if (this.level > 0) {
                for (int i = 0; i < loopTimes; i++)
                    powerToApply(target);
            }
        }
    }

    public void update() {
        this.hb.update();

        if (InputHelper.justClickedLeft && this.hb.hovered) {
            this.hb.clickStarted = true;
        }

        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            updateDescription();
            TipHelper.renderGenericTip(this.cX + 96.0F * Settings.scale, this.cY + 64.0F * Settings.scale, this.name, this.description);
            if (this.hb.clicked && AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).selectedFinFunnel != this) {
                this.hb.clicked = false;
                if (EnergyPanelPatches.energyUsedThisTurn > 0) {
                    EnergyPanelPatches.energyUsedThisTurn--;
                    addToBot(new MoveFinFunnelSelectedEffectAction(this));
                }
            }
        }

        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }

    public void updatePosition(Skeleton skeleton) {
    }

    public void render(SpriteBatch sb) {
        if (this.atlas != null) {
            sb.setColor(Color.WHITE);
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            this.skeleton.setPosition(AbstractDungeon.player.drawX + AbstractDungeon.player.animX, AbstractDungeon.player.drawY + AbstractDungeon.player.animY);
            this.skeleton.setColor(this.tint.color);
            this.skeleton.setFlip(AbstractDungeon.player.flipHorizontal, AbstractDungeon.player.flipVertical);

            sb.end();
            CardCrawlGame.psb.begin();
            sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
        }

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


    public abstract void playFinFunnelAnimation(String id);


    public abstract int getFinalEffect();

    public abstract void powerToApply(AbstractCreature target);

    public int getFinalDamage() {
        return (this.level - 1) / 2 + 1;
    }


    public static int calculateTotalFinFunnelLevel() {
        int ret = 0;
        if (AbstractDungeon.player != null) {
            if (!AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).finFunnelList.isEmpty()) {
                List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).finFunnelList;
                for (AbstractFinFunnel funnel : funnelList) {
                    ret += funnel.getLevel();
                }
                AbstractRelic relic = AbstractDungeon.player.getRelic(DimensionSplitterAria.ID);
                if (relic != null) {
                    ret += relic.counter;
                }

                if (AbstractDungeon.player.hasPower(LoseFinFunnelUpgradePower.POWER_ID))
                    ret = 0;
            }
        }

        return ret;
    }

}
