package VUPShionMod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class SwardChargeItem implements Disposable {
    private Texture img;
    private float cX;
    private float cY;
    private final float scale;

    private boolean hide = true;
    private boolean shine = false;

    private final Color color = Color.WHITE.cpy();

    public float lightTimer = 0.0f;


    public SwardChargeItem(String imgPath, float scale) {
        this.img = ImageMaster.loadImage(imgPath);
        this.scale = scale;
    }


    public void updatePos(float cX, float cY) {
        this.cX = cX;
        this.cY = cY;

        if (this.shine && this.lightTimer >= 0.6f) {
            this.lightTimer += Gdx.graphics.getDeltaTime() * 4.0f;
            this.color.a = ((float) Math.cos(this.lightTimer) + 1.0f) * 0.2f + 0.6f;
            return;
        }

        if (this.hide) {
            this.lightTimer -= Gdx.graphics.getDeltaTime() * 5.0f;
            if (this.lightTimer < 0.0f) this.lightTimer = 0.0f;
        } else {
            this.lightTimer += Gdx.graphics.getDeltaTime() * 5.0f;
            if (this.lightTimer > 1.0f) this.lightTimer = 1.0f;
        }
        this.color.a = this.lightTimer;

    }

    public void hide() {
        this.hide = true;
        this.shine = false;
    }

    public void show() {
        this.hide = false;
        this.shine = false;
    }

    public void shine() {
        if (!this.shine)
            this.lightTimer = 1.0f;
        this.hide = false;
        this.shine = true;

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.cX, this.cY,
                0.0f, 0.0f, this.img.getWidth(), this.img.getHeight(),
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
    }

    @Override
    public void dispose() {
        if (this.img != null) {
            this.img.dispose();
            this.img = null;
        }
    }
}
