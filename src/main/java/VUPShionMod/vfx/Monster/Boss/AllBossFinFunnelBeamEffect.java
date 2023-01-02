package VUPShionMod.vfx.Monster.Boss;

import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.AbstractBossFinFunnel;
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

import java.util.ArrayList;

public class AllBossFinFunnelBeamEffect extends AbstractGameEffect {
    private final ArrayList<AbstractBossFinFunnel> finFunnels;
    private final ArrayList<FinFunnelBeamData> dataList = new ArrayList<>();
    private boolean isFlipped = false;
    private static TextureAtlas.AtlasRegion img;
    private boolean posUpdated = false;


    public static class FinFunnelBeamData {
        public float sX;
        public float sY;
        public float dX;
        public float dY;
        public float dst;
        public float rotation;
    }

    public AllBossFinFunnelBeamEffect(ArrayList<AbstractBossFinFunnel> finFunnels, boolean isFlipped) {
        super();
        this.finFunnels = finFunnels;

        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }
        this.isFlipped = isFlipped;

        this.color = Color.CYAN.cpy();
        this.duration = 0.9F;
        this.startingDuration = 0.5F;
    }

    public AllBossFinFunnelBeamEffect(ArrayList<AbstractBossFinFunnel> finFunnels) {
        this(finFunnels, false);
    }

    @Override
    public void update() {
        if (this.finFunnels.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (duration == 0.9F) {
            for (AbstractBossFinFunnel f : finFunnels)
                f.playFinFunnelAnimation(f.id);
        }

        if (this.duration < this.startingDuration) {
            if (!posUpdated) {
                posUpdated = true;
                for (int i = 0; i < finFunnels.size(); i++) {
                    FinFunnelBeamData data = new FinFunnelBeamData();
                    data.sX = finFunnels.get(i).muzzle_X;
                    data.sY = finFunnels.get(i).muzzle_Y;
                    dataList.add(data);
                }

                CardCrawlGame.sound.play("ATTACK_DEFECT_BEAM");
            }


            for (FinFunnelBeamData data : dataList) {
                if (this.isFlipped) {
                    data.dX = Settings.WIDTH / 2.0F * Interpolation.pow3Out.apply(this.duration);
                    data.dY = AbstractDungeon.floorY + 10.0F * Settings.scale;
                } else {
                    data.dX = Settings.WIDTH + (-Settings.WIDTH) / 2.0F * Interpolation.pow3Out.apply(this.duration);
                    data.dY = AbstractDungeon.floorY + 30.0F * Settings.scale;
                }

                data.dst = Vector2.dst(data.sX, data.sY, data.dX, data.dY) / Settings.scale;
                data.rotation = MathUtils.atan2(data.dX - data.sX, data.dY - data.sY);
                data.rotation *= 57.295776F;
                data.rotation = -data.rotation + 90.0F;
            }

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
            for (FinFunnelBeamData data : dataList) {
                sb.setBlendFunction(770, 1);
                sb.setColor(this.color);
                sb.draw(img, data.sX, data.sY - img.packedHeight / 2.0F + 10.0F * Settings.scale, 0.0F,
                        img.packedHeight / 2.0F, data.dst, 50.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, data.rotation);
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
