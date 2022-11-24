package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Shion.DamageAndGainBlockAction;
import VUPShionMod.actions.Shion.MoveFinFunnelSelectedEffectAction;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Shion.*;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Shion.FinFunnelBeamEffect;
import VUPShionMod.vfx.Shion.FinFunnelSmallLaserEffect;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
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

    public int index = -1;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public float muzzle_X = 0.0F;
    public float muzzle_Y = 0.0F;
    public float minamiPosX = 0.0F;
    public float minamiPosY = 0.0F;
    public Hitbox hb;
    protected float fontScale;
    public Color renderColor = Settings.CREAM_COLOR.cpy();


    protected Bone body;
    protected Bone muzzle;

    public int level;
    public int levelForCombat;
    public int effect;

    protected TextureAtlas atlas;
    protected Skeleton skeleton;
    public AnimationState state;
    protected AnimationStateData stateData;

    protected TintEffect tint = new TintEffect();

    public AbstractFinFunnel(String id) {
        this.hb = new Hitbox(192.0F * Settings.scale, 96.0F * Settings.scale);
        this.fontScale = 0.7F;

        updateMinamiPos();
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

    protected void initAnimation(int index){
        this.state.setAnimation(0, "weapon" + (index + 1) + "_come_in", false);
        this.state.addAnimation(0, "weapon" + (index + 1) + "_idle", true, 0.0f);
    }


    public void updateDescription() {
        if (this.levelForCombat != this.level) {
            this.description = String.format(CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(this.id)).DESCRIPTION[1],
                    getLevel(), this.level, getFinalDamage(), getFinalEffect());
        } else {
            this.description = String.format(CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(this.id)).DESCRIPTION[0],
                    getLevel(), getFinalDamage(), getFinalEffect());
        }
    }

    public void atTurnStart() {
    }

    public void preBattlePrep() {
    }

    public void upgradeLevel(int amount) {
        this.level += amount;
        this.levelForCombat += amount;

        updateDescription();
    }

    public void loseLevel(int amount) {
        this.level -= amount;
        if (this.level < 0)
            this.level = 0;

        this.levelForCombat -= amount;

        if (this.levelForCombat < 0)
            this.levelForCombat = 0;

        updateDescription();
    }

    public void loseTempLevel(int amount) {
        this.levelForCombat -= amount;
        if (this.levelForCombat < 0)
            this.levelForCombat = 0;

        updateDescription();
    }

    public void setLevel(int amount) {
        this.level = amount;
    }

    public int getLevel() {
        return this.levelForCombat;
    }

    public void onPursuitEnemy(AbstractCreature target) {
        onPursuitEnemy(target, 1);
    }

    public void onPursuitEnemy(AbstractCreature target, int loop) {
    }


    public void activeFire(AbstractCreature target, DamageInfo info) {
        this.activeFire(target, info, true, 1);
    }

    public void activeFire(AbstractCreature target, DamageInfo info, boolean triggerPassive) {
        this.activeFire(target, info, triggerPassive, 1);
    }

    public void activeFire(AbstractCreature target, DamageInfo info, int loopTimes) {
        this.activeFire(target, info, true, loopTimes);
    }


    public void activeFire(AbstractCreature target, DamageInfo info, boolean triggerPassive, int loopTimes) {
        if (AbstractDungeon.player.hasPower(AttackOrderSpecialPower.POWER_ID)
                || (AbstractDungeon.player.hasPower(WideAreaLockingPower.POWER_ID) && this.id.equals(PursuitFinFunnel.ID))) {
            if (AbstractDungeon.player.hasPower(DefensiveOrderPower.POWER_ID)) {
                int block = 0;
                int[] damageInfo = DamageInfo.createDamageMatrix(info.base, false);
                for (int i = 0; i < damageInfo.length; i++)
                    block += damageInfo[i];

                addToBot(new GainShieldAction(AbstractDungeon.player, block * loopTimes));
            } else {
                addToBot(new VFXAction(new FinFunnelBeamEffect(this), 0.4f));
                for (int i = 0; i < loopTimes; i++)
                    addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(info.base,
                            false), info.type, AbstractGameAction.AttackEffect.FIRE));
            }
            if (triggerPassive) {
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        if (!mo.isDeadOrEscaped()) {
                            powerToApply(mo);
                        }
                    }
            }


        } else {
            if (AbstractDungeon.player.hasPower(DefensiveOrderPower.POWER_ID)) {
                addToBot(new GainShieldAction(AbstractDungeon.player, info.output));
            } else {
                addToBot(new VFXAction(new FinFunnelSmallLaserEffect(this, target), 0.3F));
                addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
                for (int i = 0; i < loopTimes; i++) {
                    addToBot(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
                }
            }

            if (triggerPassive) {
                powerToApply(target);
            }
        }
    }


    public void activeFire(AbstractCreature target, int multiDamage, boolean triggerPassive, int loopTimes) {
        if (AbstractDungeon.player.hasPower(DefensiveOrderPower.POWER_ID)) {
            int block = 0;
            int[] damageInfo = DamageInfo.createDamageMatrix(multiDamage, false);
            for (int i = 0; i < damageInfo.length; i++)
                block += damageInfo[i];

            addToBot(new GainShieldAction(AbstractDungeon.player, block * loopTimes));
        } else {
            addToBot(new VFXAction(new FinFunnelBeamEffect(this), 0.4f));
            for (int i = 0; i < loopTimes; i++)
                addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(multiDamage, false),
                        DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        }

        if (triggerPassive) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        powerToApply(mo);
                    }
                }
        }
    }


    public void fire(AbstractCreature target, int damage, DamageInfo.DamageType type, int loopTimes) {
        if (target.isDeadOrEscaped()) return;
        if (AbstractDungeon.player.hasPower(AttackOrderBetaPower.POWER_ID)) {
            addToBot(new VFXAction(AbstractDungeon.player, new FinFunnelBeamEffect(this, AbstractDungeon.player.flipHorizontal), 0.4F));
            for (int i = 0; i < loopTimes; i++)
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), type, AbstractGameAction.AttackEffect.FIRE));
            if (getLevel() > 0) {
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


            if (getLevel() > 0) {
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
        this.minamiPosX = AbstractDungeon.player.drawX + AbstractDungeon.player.animX;
        this.minamiPosY = AbstractDungeon.player.drawY + AbstractDungeon.player.animY;

        updateMinamiPos();

        if (AbstractDungeon.player.flipHorizontal)
            this.cX = this.skeleton.getX() + body.getWorldX() - 48.0f * Settings.scale;
        else
            this.cX = this.skeleton.getX() + body.getWorldX() + 48.0f * Settings.scale;
        this.cY = this.skeleton.getY() + body.getWorldY();


        hb.move(cX, cY);
        this.muzzle_X = this.skeleton.getX() + muzzle.getWorldX();
        this.muzzle_Y = this.skeleton.getY() + muzzle.getWorldY();

    }


    public void render(SpriteBatch sb) {
        if (this.atlas != null) {
            sb.setColor(Color.WHITE);
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            this.skeleton.setPosition(this.minamiPosX, this.minamiPosY);
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
        if (!AbstractDungeon.player.isDeadOrEscaped()) {
            if (AbstractDungeon.player.flipHorizontal) {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(getLevel()),
                        this.cX + 24.0F * Settings.scale, this.cY - 12.0F * Settings.scale,
                        this.level == this.levelForCombat ? this.renderColor : new Color(0.2F, 1.0F, 1.0F, 1.0F), this.fontScale);
            } else {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(getLevel()),
                        this.cX - 24.0F * Settings.scale, this.cY - 12.0F * Settings.scale,
                        this.level == this.levelForCombat ? this.renderColor : new Color(0.2F, 1.0F, 1.0F, 1.0F), this.fontScale);
            }
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

    public void powerToApply(AbstractCreature target, float amountScale, boolean top) {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof AbstractShionPower) {
                ((AbstractShionPower) power).onTriggerFinFunnel(this, target);
            }
        }
    }

    public void powerToApply(AbstractCreature target) {
        powerToApply(target, 1.0f, false);
    }

    public int getFinalDamage() {
        return (getLevel() - 1) / 2 + 1;
    }


    public static int calculateTotalFinFunnelLevel() {
        int ret = 0;
        if (AbstractDungeon.player != null) {
            if (!FinFunnelManager.getFinFunnelList().isEmpty()) {
                List<AbstractFinFunnel> funnelList = FinFunnelManager.getFinFunnelList();
                for (AbstractFinFunnel funnel : funnelList) {
                    ret += funnel.getLevel();
                }

                if (AbstractDungeon.player.hasPower(LoseFinFunnelUpgradePower.POWER_ID))
                    ret = 0;
            }
        }

        return ret;
    }

    public void resetLevelCombat() {
        this.levelForCombat = this.level;
        this.updateDescription();
    }


    public void updateMinamiPos() {
        if (AbstractDungeon.player != null) {
            float flip = AbstractDungeon.player.flipHorizontal ? -1.0f : 1.0f;
            switch (this.index) {
                case 0:
                    this.minamiPosX += -flip * 300.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    this.minamiPosY += 668.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    break;
                case 1:
                    this.minamiPosX += flip * 300.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    this.minamiPosY += 498.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    break;
                case 2:
                    this.minamiPosX += -flip * 140.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    this.minamiPosY += 416.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    break;
                case 3:
                    this.minamiPosX += flip * 200.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    this.minamiPosY += 320.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    break;
                case 4:
                    this.minamiPosX += -flip * 70.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    this.minamiPosY += 860.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    break;
                case 5:
                    this.minamiPosX += flip * 220.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    this.minamiPosY += 749.0f * Settings.scale / SkinManager.getSkin(0).renderScale;
                    break;
            }
        }
    }



}
