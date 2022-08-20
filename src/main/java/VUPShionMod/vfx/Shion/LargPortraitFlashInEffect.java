package VUPShionMod.vfx.Shion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.Set;

public class LargPortraitFlashInEffect extends AbstractGameEffect {
    private Texture img;
    private Color color = Color.WHITE.cpy();
    private float offSet_x;
    private float offSet_y;

    private boolean flipX = false;

    public LargPortraitFlashInEffect(String name) {
        this(name, false);
    }

    public LargPortraitFlashInEffect(String name, boolean flipX) {
        this.img = ImageMaster.loadImage("VUPShionMod/img/vfx/LargPortraitFlashInEffect/" + name + ".png");
        this.duration = 2.25f;
        this.offSet_x = Settings.WIDTH;
        this.offSet_y = 0.0f * Settings.scale;
        this.scale = 1.0f;
        this.flipX = flipX;
    }

    @Override
    public void update() {
        if (this.img == null) {
            this.isDone = true;
            return;
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration > 2.0F) {
            this.offSet_x = Settings.WIDTH * (this.duration - 2.0f) * 4;
        } else {
            this.offSet_x = (-200.0f * (2.0f - this.duration) * 0.5f) * Settings.scale;
        }

        if (this.duration < 0.5f) {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - this.duration * 2);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            dispose();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
//        sb.setBlendFunction(770, 771);

        if (this.img != null) {

            if (this.flipX)
                sb.draw(this.img, Settings.WIDTH / 2.0f + 300.0f * Settings.scale - this.img.getWidth() / 2.0f - offSet_x,
                        Settings.HEIGHT / 2.0f - this.img.getHeight() / 2.0f + offSet_y,
                        this.img.getWidth() / 2.0f, this.img.getHeight() / 2.0f, this.img.getWidth(), this.img.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);

            else
                sb.draw(this.img, Settings.WIDTH / 2.0f - 300.0f * Settings.scale - this.img.getWidth() / 2.0f + offSet_x,
                        Settings.HEIGHT / 2.0f - this.img.getHeight() / 2.0f + offSet_y,
                        this.img.getWidth() / 2.0f, this.img.getHeight() / 2.0f, this.img.getWidth(), this.img.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);


        }
    }

    @Override
    public void dispose() {
        if (this.img != null) {
            this.img.dispose();
            this.img = null;
        }
    }
}
