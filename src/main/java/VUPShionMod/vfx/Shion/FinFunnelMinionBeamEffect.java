package VUPShionMod.vfx.Shion;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FinFunnelMinionBeamEffect extends AbstractGameEffect {
    private final Skeleton skeleton;
    private final Bone muzzle;
    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private float dst;
    private boolean isFlipped = false;
    private static TextureAtlas.AtlasRegion img;
    private boolean posUpdated = false;

    private final float scaleY;


    public FinFunnelMinionBeamEffect(Skeleton skeleton, boolean isFlipped, float scaleY) {
        super();
        this.skeleton = skeleton;

        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }

        this.muzzle = this.skeleton.findBone("weapon5_muzzle");

        this.isFlipped = isFlipped;

        if (isFlipped) {
            this.sX = this.skeleton.getX() + muzzle.getWorldX() - 32.0F * Settings.scale;
            this.sY = this.skeleton.getY() + muzzle.getWorldY() + 20.0F * Settings.scale;
        } else {
            this.sX = this.skeleton.getX() + muzzle.getWorldX() + 40.0F * Settings.scale;
            this.sY = this.skeleton.getY() + muzzle.getWorldY() + 50.0F * Settings.scale;
        }

        this.color = Color.SCARLET.cpy();
        this.duration = 0.5F;
        this.scaleY = scaleY;
    }


    @Override
    public void update() {
        if (this.skeleton == null) {
            this.isDone = true;
            return;
        }


        if (!posUpdated) {
            this.sX = this.skeleton.getX() + muzzle.getWorldX();
            this.sY = this.skeleton.getY() + muzzle.getWorldY();
            this.posUpdated = true;
        }

        if (this.isFlipped) {
            this.dX = Settings.WIDTH / 2.0F * Interpolation.pow3Out.apply(this.duration);
            this.dY = AbstractDungeon.floorY + 10.0F * Settings.scale;
        } else {
            this.dX = Settings.WIDTH - Settings.WIDTH / 2.0F * Interpolation.pow3Out.apply(this.duration);
            this.dY = AbstractDungeon.floorY + 30.0F * Settings.scale;
        }

        this.dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
        this.rotation = MathUtils.atan2(this.dX - this.sX, this.dY - this.sY);
        this.rotation *= 57.295776F;
        this.rotation = -this.rotation + 90.0F;
        this.skeleton.findBone("root").setRotation(this.rotation);

        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 4.0F);
        } else {
            this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.duration * 4.0F);
        }


        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(img, this.sX, this.sY - img.packedHeight / 2.0F + 10.0F * Settings.scale * this.scaleY,
                0.0F, img.packedHeight / 2.0F,
                this.dst, 50.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale * this.scaleY, this.rotation);
        sb.setColor(new Color(0.3F, 0.3F, 1.0F, this.color.a));
        sb.draw(img, this.sX, this.sY - img.packedHeight / 2.0F,
                0.0F, img.packedHeight / 2.0F,
                this.dst, MathUtils.random(50.0F, 90.0F), this.scale + MathUtils.random(-0.02F, 0.02F), this.scale * this.scaleY, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose() {

    }
}
