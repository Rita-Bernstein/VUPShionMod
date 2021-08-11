package VUPShionMod.vfx;

import VUPShionMod.actions.CustomWaitAction;
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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class AllFinFunnelSmallLaserEffect extends AbstractGameEffect {
    private ArrayList<AbstractFinFunnel> finFunnels;
    private ArrayList<AbstractMonster> targets;
    private ArrayList<FinFunnelSmallLaserData> dataList = new ArrayList<>();
    private static TextureAtlas.AtlasRegion img;
    private boolean posUpdated = false;

    public static class FinFunnelSmallLaserData {
        public float sX;
        public float sY;
        public float dX;
        public float dY;
        public float dst;
        public float rotation;
    }

    public AllFinFunnelSmallLaserEffect(ArrayList<AbstractFinFunnel> finFunnels, ArrayList<AbstractMonster> targets) {
        super();
        this.finFunnels = finFunnels;
        this.targets = targets;
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }

        this.color = Color.CYAN.cpy();
        this.duration = 0.8F;
        this.startingDuration = 0.5F;
    }

    @Override
    public void update() {
        if (duration == 0.8F)
            for (AbstractFinFunnel f : finFunnels)
                ((Shion) AbstractDungeon.player).playFinFunnelAnimation(f.id);


        if (this.duration < this.startingDuration) {
            if (!posUpdated) {

                for (int i = 0; i < finFunnels.size(); i++) {
                    FinFunnelSmallLaserData data = new FinFunnelSmallLaserData();

                    data.dX = finFunnels.get(i).muzzle_X;
                    data.dY = finFunnels.get(i).muzzle_Y;
                    data.sX = targets.get(i).hb.cX;
                    data.sY = targets.get(i).hb.cY;
                    data.dst = Vector2.dst(data.sX, data.sY, data.dX, data.dY) / Settings.scale;
                    data.rotation = MathUtils.atan2(data.dX - data.sX, data.dY - data.sY);
                    data.rotation *= 57.295776F;
                    data.rotation = -data.rotation + 90.0F;

                    dataList.add(data);

                    CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.5f);

                }
            }
            posUpdated = true;


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
        if (this.duration < this.startingDuration) {
            for (FinFunnelSmallLaserData data : dataList) {

                sb.setBlendFunction(770, 1);
                sb.setColor(this.color);
                sb.draw(img, data.sX, data.sY - img.packedHeight / 2.0F + 10.0F * Settings.scale,
                        0.0F, img.packedHeight / 2.0F, data.dst, 50.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, data.rotation);
                sb.setColor(new Color(0.3F, 0.3F, 1.0F, this.color.a));
                sb.draw(img, data.sX, data.sY - img.packedHeight / 2.0F,
                        0.0F, img.packedHeight / 2.0F, data.dst, MathUtils.random(50.0F, 90.0F), this.scale + MathUtils.random(-0.02F, 0.02F), this.scale, data.rotation);
                sb.setBlendFunction(770, 771);
            }
        }
    }

    @Override
    public void dispose() {
    }
}
