package VUPShionMod.vfx.Shion;

import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FinFunnelBeamEffect extends AbstractGameEffect {
    private AbstractFinFunnel finFunnel;
    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private float dst;
    private boolean isFlipped = false;
    private static TextureAtlas.AtlasRegion img;
    private boolean posUpdated = false;

    private float scaleY = 1.0f;

    public FinFunnelBeamEffect(AbstractFinFunnel finFunnel, boolean isFlipped) {
        super();
        this.finFunnel = finFunnel;

        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }

        if (isFlipped) {
            this.sX = this.finFunnel.muzzle_X - 32.0F * Settings.scale;
            this.sY = this.finFunnel.muzzle_Y + 20.0F * Settings.scale;
        } else {
            this.sX = this.finFunnel.muzzle_X + 40.0F * Settings.scale;
            this.sY = this.finFunnel.muzzle_Y + 50.0F * Settings.scale;
        }

        this.color = Color.CYAN.cpy();
        this.duration = 0.9F;
        this.startingDuration = 0.5F;
    }

    public FinFunnelBeamEffect(AbstractFinFunnel finFunnel, boolean isFlipped,float scaleY) {
        this(finFunnel,isFlipped);
        this.scaleY = scaleY;
    }


    public FinFunnelBeamEffect(AbstractFinFunnel finFunnel) {
        this(finFunnel, false);
    }

    @Override
    public void update() {
        if(this.finFunnel ==null){
            this.isDone = true;
            return;
        }

        if (duration == 0.9F) {
            finFunnel.playFinFunnelAnimation(finFunnel.id);
        }



        if (this.duration < this.startingDuration) {
            if (!posUpdated) {
                this.sX = finFunnel.muzzle_X;
                this.sY = finFunnel.muzzle_Y;
                this.posUpdated = true;

                CardCrawlGame.sound.play("ATTACK_DEFECT_BEAM");
            }

            if (this.isFlipped) {
                this.dX = (float) Settings.WIDTH / 2.0F * Interpolation.pow3Out.apply(this.duration);
                this.dY = AbstractDungeon.floorY + 10.0F * Settings.scale;
            } else {
                this.dX = (float) Settings.WIDTH + (float) (-Settings.WIDTH) / 2.0F * Interpolation.pow3Out.apply(this.duration);
                this.dY = AbstractDungeon.floorY + 30.0F * Settings.scale;
            }

            this.dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
            this.rotation = MathUtils.atan2(this.dX - this.sX, this.dY - this.sY);
            this.rotation *= 57.295776F;
            this.rotation = -this.rotation + 90.0F;
            if (this.duration > this.startingDuration / 2.0F) {
                this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 4.0F);
            } else {
                this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.duration * 4.0F);
            }

        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.duration < this.startingDuration) {
            sb.setBlendFunction(770, 1);
            sb.setColor(this.color);
            sb.draw(img, this.sX, this.sY - (float) img.packedHeight / 2.0F + 10.0F * Settings.scale, 0.0F, (float) img.packedHeight / 2.0F, this.dst, 50.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, this.rotation);
            sb.setColor(new Color(0.3F, 0.3F, 1.0F, this.color.a));
            sb.draw(img, this.sX, this.sY - (float) img.packedHeight / 2.0F, 0.0F, (float) img.packedHeight / 2.0F, this.dst, MathUtils.random(50.0F, 90.0F), this.scale + MathUtils.random(-0.02F, 0.02F), this.scale, this.rotation);
            sb.setBlendFunction(770, 771);
        }

    }

    @Override
    public void dispose() {

    }
}
