package VUPShionMod.vfx.Shion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FinFunnelMinionLaserEffect extends AbstractGameEffect {
    private final float sX;
    private final float sY;
    private final float dX;

    private final float dY;
    private final float dst;
    private static TextureAtlas.AtlasRegion img;

    private float scaleY = 1.0f;

    public FinFunnelMinionLaserEffect(float sX, float sY, float dX, float dY, float scaleY) {
        this(sX, sY, dX, dY);
        this.scaleY = scaleY;
    }

    public FinFunnelMinionLaserEffect(float sX, float sY, float dX, float dY) {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }
        this.sX = sX;
        this.sY = sY;
        this.dX = dX;
        this.dY = dY;

        this.dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
        this.color = Color.CYAN.cpy();
        this.duration = 0.4F;
        this.startingDuration = 0.4F;

        this.rotation = MathUtils.atan2(dX - sX, dY - sY);
        this.rotation *= 57.295776F;
        this.rotation = -this.rotation + 90.0F;
    }


    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 0.2F) * 5.0F);
        } else {
            this.color.a = Interpolation.bounceIn.apply(0.0F, 1.0F, this.duration * 5.0F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(img, this.sX, this.sY - img.packedHeight / 2.0F + 10.0F * Settings.scale * this.scaleY, 0.0F,
                img.packedHeight / 2.0F, this.dst, 50.0F,
                this.scale + MathUtils.random(-0.01F, 0.01F), this.scale * this.scaleY, this.rotation);

        sb.setColor(new Color(0.3F, 0.3F, 1.0F, this.color.a));
        sb.draw(img, this.sX, this.sY - img.packedHeight / 2.0F, 0.0F, img.packedHeight / 2.0F, this.dst,
                MathUtils.random(50.0F, 90.0F),
                this.scale + MathUtils.random(-0.02F, 0.02F), this.scale * this.scaleY, this.rotation);


        sb.setBlendFunction(770, 771);
    }


    public void dispose() {
    }
}