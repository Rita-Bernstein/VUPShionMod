package VUPShionMod.cutscenes;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class BetterCutscenePanel extends CutscenePanel {
    private Texture img;
    private Color color;
    private float timeScale;

    public BetterCutscenePanel(String imgUrl, String sfx, float timeScale) {
        super(imgUrl, sfx);
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.timeScale = timeScale;
        this.img = ImageMaster.loadImage(imgUrl);
    }

    public BetterCutscenePanel(String imgUrl, float timeScale) {
        this(imgUrl, null, timeScale);
    }

    public BetterCutscenePanel(String imgUrl) {
        this(imgUrl, null, 1.0f);
    }

    @Override
    public void update() {
        if (this.fadeOut) {
            this.color.a -= Gdx.graphics.getDeltaTime();
            if (this.color.a < 0.0F) {
                this.color.a = 0.0F;
                this.finished = true;
            }

            return;
        }
        if (this.activated && !this.finished) {
            String sfx = ReflectionHacks.getPrivate(this, CutscenePanel.class, "sfx");
            if (sfx != null) {
                this.color.a += Gdx.graphics.getDeltaTime() * 10.0F * timeScale;
            } else {
                this.color.a += Gdx.graphics.getDeltaTime() * timeScale;
            }

            if (this.color.a > 1.0F) {
                this.color.a = 1.0F;
                this.finished = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.color);
            if (Settings.isSixteenByTen) {
                sb.draw(this.img, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
            } else {
                sb.draw(this.img, 0.0F, -50.0F * Settings.scale, (float)Settings.WIDTH, (float)Settings.HEIGHT + 110.0F * Settings.scale);
            }
        }
    }
}
