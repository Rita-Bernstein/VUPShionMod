package VUPShionMod.finfunnels;

import VUPShionMod.actions.MoveFinFunnelAction;
import VUPShionMod.actions.MoveFinFunnelSelectedEffectAction;
import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.EnergyPanelPatches;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.BobEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * 浮游炮抽象类
 * @author Temple9
 * @since 2021-07-22
 */
public abstract class AbstractFinFunnel {
    public String name;
    public String description;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public String ID;
    public Hitbox hb;
    protected Texture img;
    protected BobEffect bobEffect;
    protected float fontScale;

    /** 强化等级 */
    protected int level;

    public AbstractFinFunnel() {
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = ImageMaster.ORB_LIGHTNING; //TODO 先用电球的贴图顶着
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.7F;
        if (AbstractDungeon.player != null) {
            this.cX = AbstractDungeon.player.hb.cX;
            this.cY = AbstractDungeon.player.hb.cY;
        }
    }

    public AbstractFinFunnel setPosition(float cX, float cY, boolean moveEffect) {
        addToBot(new MoveFinFunnelAction(this, cX, cY, moveEffect));
        return this;
    }

    public abstract void updateDescription();

    /**
     * 玩家回合开始时调用此方法
     */
    public void atTurnStart() {

    }

    /**
     * 追击效果触发时
     * @param target 追击目标
     */
    public void onPursuitEnemy(AbstractCreature target) {

    }

    /**
     * 提升强化等级
     * @param amount 提升量
     */
    public void upgradeLevel(int amount) {
        this.level += amount;
    }

    /**
     * 获得强化等级
     * @return 强化等级
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * 这里写浮游炮的行动方式
     */
    public abstract void fire(AbstractCreature target, int damage, DamageInfo.DamageType type);

    /**
     * 更新
     */
    public void update() {
        this.bobEffect.update();
        this.hb.update();

        if (this.hb.hovered) {
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

    /**
     * 渲染
     * @param sb 纹理画布
     */
    public void render(SpriteBatch sb) {

    }

    /**
     * 渲染强化等级
     * @param sb 纹理画布
     */
    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.level), this.cX + 20.0F * Settings.scale, this.cY + this.bobEffect.y / 2.0F - 12.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, 1.0F), this.fontScale);
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    static {
        new FinFunnelSaver();
    }

    public static class FinFunnelSaver implements CustomSavable<List<Integer>> {

        FinFunnelSaver() {
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
            if (integerList != null) {
                List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player);
                int index = 0;
                for (Integer i :integerList) {
                    if (funnelList.size() > index) {
                        funnelList.get(index).level = i;
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
