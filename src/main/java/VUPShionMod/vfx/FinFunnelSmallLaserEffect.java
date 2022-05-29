package VUPShionMod.vfx;

import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FinFunnelSmallLaserEffect extends AbstractGameEffect {
    private AbstractFinFunnel finFunnel;
    private AbstractCreature target;
    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private float dst;
    private static TextureAtlas.AtlasRegion img;
    private boolean posUpdated = false;


    public FinFunnelSmallLaserEffect(AbstractFinFunnel finFunnel, AbstractCreature target) {
        super();
        this.finFunnel = finFunnel;
        this.target = target;

        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }

        this.dX = finFunnel.muzzle_X;
        this.dY = finFunnel.muzzle_Y;
        this.dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
        this.color = Color.CYAN.cpy();
        this.duration = 0.8F;
        this.startingDuration = 0.5F;
        this.rotation = MathUtils.atan2(dX - sX, dY - sY);
        this.rotation *= 57.295776F;
        this.rotation = -this.rotation + 90.0F;
    }

    @Override
    public void update() {
        if(finFunnel == null || this.target == null){
            this.isDone = true;
            return;
        }

        if (duration == 0.8F) {
            finFunnel.playFinFunnelAnimation(finFunnel.id);
        }
        if (this.duration < this.startingDuration) {
            if (!posUpdated) {
                this.dX = finFunnel.muzzle_X;
                this.dY = finFunnel.muzzle_Y;
                this.sX = target.hb.cX;
                this.sY = target.hb.cY;
                this.dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
                this.rotation = MathUtils.atan2(dX - sX, dY - sY);
                this.rotation *= 57.295776F;
                this.rotation = -this.rotation + 90.0F;

                this.posUpdated = true;

                CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.5f);

            }

            if (this.duration > this.startingDuration / 2.0F) {
                this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 4.0F);
            } else {
                this.color.a = Interpolation.bounceIn.apply(0.0F, 1.0F, this.duration * 4.0F);
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.duration < this.startingDuration && posUpdated) {
            sb.setBlendFunction(770, 1);
            sb.setColor(this.color);
            sb.draw(img, this.sX, this.sY - img.packedHeight / 2.0F + 10.0F * Settings.scale, 0.0F, img.packedHeight / 2.0F, this.dst, 50.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, this.rotation);
            sb.setColor(new Color(0.3F, 0.3F, 1.0F, this.color.a));
            sb.draw(img, this.sX, this.sY - img.packedHeight / 2.0F, 0.0F, img.packedHeight / 2.0F, this.dst, MathUtils.random(50.0F, 90.0F), this.scale + MathUtils.random(-0.02F, 0.02F), this.scale, this.rotation);
            sb.setBlendFunction(770, 771);
        }
    }

    @Override
    public void dispose() {

    }
}
