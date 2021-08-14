package VUPShionMod.finfunnels;

import VUPShionMod.actions.CustomWaitAction;
import VUPShionMod.actions.MoveFinFunnelAction;
import VUPShionMod.actions.MoveFinFunnelSelectedEffectAction;
import VUPShionMod.character.Shion;
import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.EnergyPanelPatches;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
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
        this.level += amount;
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

        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            updateDescription();
            TipHelper.renderGenericTip(this.cX + 96.0F * Settings.scale, this.cY + 64.0F * Settings.scale, this.name, this.description);
            if (InputHelper.justReleasedClickLeft && AbstractPlayerPatches.AddFields.activatedFinFunnel.get(AbstractDungeon.player) != this) {
                if (EnergyPanelPatches.energyUsedThisTurn > 0) {
                    EnergyPanelPatches.energyUsedThisTurn--;
                    AbstractPlayerPatches.AddFields.activatedFinFunnel.set(AbstractDungeon.player, this);
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
        if (!AbstractDungeon.player.isDead)
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.level), this.cX + 20.0F * Settings.scale, this.cY - 12.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, 1.0F), this.fontScale);
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public static class FinFunnelSaver implements CustomSavable<List<Integer>> {

        public List<Integer> data;

        public FinFunnelSaver() {
            BaseMod.addSaveField("finFunnels", this);
        }

        @Override
        public List<Integer> onSave() {
            List<Integer> ret = new ArrayList<>();
            for (AbstractFinFunnel funnel : AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player)) {
                ret.add(funnel.getLevel());
            }
            return ret;
        }

        @Override
        public void onLoad(List<Integer> integerList) {
            this.data = integerList;
        }
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


    public int getFinalEffect() {
        return this.effect * (this.level + 2) / 3;
    }
}
