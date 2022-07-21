package VUPShionMod.vfx.Shion;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.TintEffect;


import static com.megacrit.cardcrawl.core.AbstractCreature.sr;


public class FinFunnelMinionEffect extends AbstractGameEffect {
    protected TextureAtlas atlas;
    protected Skeleton skeleton;
    public AnimationState state;
    protected AnimationStateData stateData;
    protected TintEffect tint = new TintEffect();

    private AbstractCreature target;
    private AbstractFinFunnel finFunnel;
    private int index;

    private boolean isFinFunnelIn = false;
    private boolean isFinFunnelOut = false;

    private float cX = 0.0f;
    private float cY = 0.0f;
    private float dX = 0.0f;
    private float dY = 0.0f;
    private float scale = 0.8f;

    private float fireTimer = 1.0f;
    private float delayTimer = 0.0f;

    private boolean isAoe = false;


    public FinFunnelMinionEffect(AbstractFinFunnel finFunnel, AbstractCreature target, int index, boolean isAoe) {
        this.index = index + 1;
        this.finFunnel = finFunnel;

        this.duration = 2.0f + (7 - index) * 0.15f + index * 0.15f;
        this.fireTimer += index * 0.15f;
        this.delayTimer = index * 0.15f;

        this.isAoe = isAoe;
        this.target = target;

        switch (this.index) {
            case 1:
                this.cX = 0.37f * Settings.WIDTH;
                this.cY = 0.81f * Settings.HEIGHT;
                break;
            case 2:
                this.cX = 0.51f * Settings.WIDTH;
                this.cY = 0.76f * Settings.HEIGHT;
                this.scale = 0.625f;
                break;
            case 3:
                this.cX = 0.73f * Settings.WIDTH;
                this.cY = 0.5f * Settings.HEIGHT;
                this.scale = 0.625f;
                break;
            case 4:
                this.cX = 0.91f * Settings.WIDTH;
                this.cY = 0.25f * Settings.HEIGHT;
                break;
            case 5:
                this.cX = 0.37f * Settings.WIDTH;
                this.cY = 0.25f * Settings.HEIGHT;
                break;
            case 6:
                this.cX = 0.4f * Settings.WIDTH;
                this.cY = 0.44f * Settings.HEIGHT;
                this.scale = 0.625f;
                break;
            case 7:
                this.cX = 0.68f * Settings.WIDTH;
                this.cY = 0.74f * Settings.HEIGHT;
                this.scale = 0.625f;
                break;
            default:
                this.cX = 0.77f * Settings.WIDTH;
                this.cY = 0.82f * Settings.HEIGHT;
        }


        if (!isAoe)
            if (target != null && !target.isDeadOrEscaped()) {
                this.dX = target.hb.cX;
                this.dY = target.hb.cY;
            }


        if (CharacterSelectScreenPatches.skinManager.skinCharacters.get(0).reskinCount == 0) {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon5.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon5.json", 0.8f * this.scale);
        } else {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Blue/YOFU5.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Blue/YOFU5.json", 0.8f * this.scale);
        }


    }



    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || this.target == null || this.finFunnel == null) {
            isDone = true;
            dispose();
            return;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        this.delayTimer -= Gdx.graphics.getDeltaTime();

        if (this.fireTimer > 0.0f) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer <= 0.0f) {
                Bone muzzle = this.skeleton.findBone("weapon5_muzzle");

                if (!isAoe && this.target != null) {
                    CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", MathUtils.random(0.4F, 0.6F));

                    AbstractDungeon.effectsQueue.add(new FinFunnelMinionLaserEffect(
                            this.skeleton.getX() + muzzle.getWorldX(), this.skeleton.getY() + muzzle.getWorldY(),
                            this.target.hb.cX, this.target.hb.cY, this.scale));
                }

                if (this.isAoe) {
                    CardCrawlGame.sound.play("ATTACK_DEFECT_BEAM");
                    AbstractDungeon.effectsQueue.add(new FinFunnelMinionBeamEffect(this.skeleton, false, this.scale));
                }

            }

        }

        if (!isFinFunnelIn && this.delayTimer <= 0.0f) {
            this.isFinFunnelIn = true;
            this.skeleton.setPosition(this.cX, this.cY);
            Bone root = this.skeleton.findBone("root");
            if (this.isAoe)
                root.setRotation(-((MathUtils.atan2(this.cX - Settings.WIDTH / 2.0f, this.cY - Settings.HEIGHT / 2.0f) * 57.295776F) + 90.0f));
            else
                root.setRotation(-((MathUtils.atan2(this.cX - this.dX, this.cY - this.dY) * 57.295776F) + 90.0f));

            this.state.setAnimation(1, "weapon5_idle_lightring", false);
            this.state.setAnimation(0, "weapon5_come_in", false);
            this.state.addAnimation(0, "weapon5_attack", false, 0.0f);
            this.state.addAnimation(0, "weapon5_idle", false, 0.0f);
        }

        if (this.duration <= 0.3f && !this.isFinFunnelOut) {
            this.isFinFunnelOut = true;
            this.state.setAnimation(0, "weapon5_leave_out", false).setTimeScale(2.0f);
        }

        if (this.duration <= 0.0f && !this.isDone) {
            this.isDone = true;
            dispose();
        }
    }

    public void render(SpriteBatch sb) {
        if (this.atlas != null) {
            sb.setColor(Color.WHITE);
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            this.skeleton.setColor(this.tint.color);

            sb.end();
            CardCrawlGame.psb.begin();
            sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
        }
    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);

        json.setScale(Settings.scale * scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }

    @Override
    public void dispose() {
        if (this.atlas != null) {
            this.atlas.dispose();
            this.atlas = null;
        }
    }
}
