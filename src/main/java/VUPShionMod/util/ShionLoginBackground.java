package VUPShionMod.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.scenes.TitleBackground;

public class ShionLoginBackground extends TitleBackground implements Disposable {
    private Texture bg_account = ImageMaster.loadImage("VUPShionMod/img/ui/mainBg.png");
    private float display_Cx = Settings.WIDTH / 2.0f;
    private float display_Cy = Settings.HEIGHT / 2.0F;
    private float loginScale = 0.8f;

    public ShionLoginBackground() {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(bg_account, display_Cx - 1198.0F,
                display_Cy - 600.0F - 0.0f * Settings.scale,
                1198.0F, 600.0F,
                2397.0f, 1200.0f,
                Settings.renderScale * loginScale, Settings.renderScale * loginScale,
                0, 0, 0, 2397, 1200, false, false);
    }

    @Override
    public void dispose() {
        bg_account.dispose();
    }
}
