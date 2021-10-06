package VUPShionMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class GachaEffect extends AbstractGameEffect {
    private AbstractMonster m;
    private int result;
    private Texture rolling1 = ImageMaster.loadImage("VUPShionMod/img/ui/Rolling/Rolling1.png");
    private Texture rolling2 = ImageMaster.loadImage("VUPShionMod/img/ui/Rolling/Rolling2.png");
    private Texture rolling3 = ImageMaster.loadImage("VUPShionMod/img/ui/Rolling/Rolling3.png");
    private Texture rollingMask = ImageMaster.loadImage("VUPShionMod/img/ui/Rolling/RollingMask.png");
    private ArrayList<Texture> rollingItems = new ArrayList<>();

    private float cX = 0.0f;
    private float cY = 0.0f;
    private float renderScale = 0.5f;
    private float startX;
    private float startY;
    private float targetX;
    private float targetY;
    private Color glowColor = new Color(0.95f,0.23f,0.35f,0.0f);
    private int phase = 0;

    private float rollingTimer = 0.1f;
    private int rollingIndex = 0;


    public GachaEffect(AbstractMonster m, int result) {
        this.m = m;
        this.result = result;
        rollingItems.add(ImageMaster.loadImage("VUPShionMod/img/ui/Rolling/block.png"));
        rollingItems.add(ImageMaster.loadImage("VUPShionMod/img/ui/Rolling/regen.png"));
        rollingItems.add(ImageMaster.loadImage("VUPShionMod/img/ui/Rolling/artifact.png"));
        rollingItems.add(ImageMaster.loadImage("VUPShionMod/img/ui/Rolling/frail.png"));

        this.duration = this.startingDuration = 0.5f;

        this.cX = Settings.WIDTH / 2.0f + 300.0f * Settings.scale;
        this.cY = Settings.HEIGHT / 2.0f;
        this.startX = Settings.WIDTH / 2.0f + 300.0f * Settings.scale;
        this.startY = Settings.HEIGHT / 2.0f;
        this.targetX = m.hb.cX;
        this.targetY = m.hb.cY;

    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            this.phase++;
        }

        if (this.phase == 1) {

            this.cX = Interpolation.exp10Out.apply(this.targetX, this.startX, this.duration * 2.0f);
            this.cY = Interpolation.exp10Out.apply(this.targetY, this.startY, this.duration * 2.0f);
        }

        if (this.phase == 2) {
            this.rollingTimer -= Gdx.graphics.getDeltaTime();
            if (this.rollingTimer < 0.0f) {
                this.rollingTimer = 0.05f;
                this.rollingIndex++;
                if (rollingIndex > this.rollingItems.size() - 1) {
                    rollingIndex = 0;
                }
            }
        }
        if (this.phase == 3) {
            if (glowColor.a < 1.0f)
                glowColor.a = Interpolation.exp10Out.apply(1.0f, 0.4f, this.duration * 2.0f);
            else
                glowColor.a = 1.0f;
            this.rollingIndex = this.result;
        }

        if (this.phase >= 4) {
//            this.renderColor.a = Interpolation.exp10Out.apply(0.0f, 1.0f, this.duration * 2.0f);
            this.cX = Interpolation.exp10Out.apply(this.startX, this.targetX, this.duration * 2.0f);
            this.cY = Interpolation.exp10Out.apply(this.startY, this.targetY, this.duration * 2.0f);
        }


        this.duration -= Gdx.graphics.getDeltaTime();


        if (this.duration < 0.0f) {
            if (this.phase == 1 || this.phase == 2) {
                this.duration = 1.0f;
                this.startingDuration = 1.0f;
            } else {
                this.duration = 0.5f;
                this.startingDuration = 0.5f;
            }

            if (phase >= 4)
                this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 771);

        if (this.phase == 3) {
            sb.setColor(this.glowColor);
            sb.draw(this.rollingMask,
                    this.cX - 200.0F, this.cY - 200.0F,
                    200.0F, 200.0F,
                    400.0F, 400.0F,
                    this.renderScale * Settings.scale * 1.5f, this.renderScale * Settings.scale * 1.5f,
                    0.0f,
                    0, 0,
                    400, 400,
                    false, false);
        }

        sb.setColor(Color.WHITE);

        if (this.phase == 1) {
            sb.draw(this.rolling3,
                    this.cX - 200.0F, this.cY - 200.0F,
                    200.0F, 200.0F,
                    400.0F, 400.0F,
                    this.renderScale * Settings.scale, this.renderScale * Settings.scale,
                    0.0f,
                    0, 0,
                    400, 400,
                    false, false);
        } else {
            sb.draw(this.rolling1,
                    this.cX - 200.0F, this.cY - 200.0F,
                    200.0F, 200.0F,
                    400.0F, 400.0F,
                    this.renderScale * Settings.scale, this.renderScale * Settings.scale,
                    0.0f,
                    0, 0,
                    400, 400,
                    false, false);
        }

        if (this.phase >= 2) {
            sb.draw(this.rollingItems.get(this.rollingIndex),
                    this.cX - 64.0F, this.cY - 64.0F,
                    64.0F, 64.0F,
                    128.0F, 128.0F,
                    this.renderScale * Settings.scale, this.renderScale * Settings.scale,
                    0.0f,
                    0, 0,
                    128, 128,
                    false, false);
        }

        sb.draw(this.rolling2,
                this.cX - 200.0F, this.cY - 200.0F,
                200.0F, 200.0F,
                400.0F, 400.0F,
                this.renderScale * Settings.scale, this.renderScale * Settings.scale,
                0.0f,
                0, 0,
                400, 400,
                false, false);

    }

    @Override
    public void dispose() {
        this.rolling1.dispose();
        this.rolling2.dispose();
        for (Texture t : rollingItems)
            t.dispose();
    }
}
