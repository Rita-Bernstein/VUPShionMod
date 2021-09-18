package VUPShionMod.effects;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FinFunnelSelectedEffect extends AbstractGameEffect {
    private Texture img;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public static FinFunnelSelectedEffect instance;

    public FinFunnelSelectedEffect() {
        this.img = new Texture(VUPShionMod.assetPath("img/effects/finFunnelSelectedEffect.png"));
        if (instance != null) {
            instance.isDone = true;
        }
        instance = this;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        if(AbstractDungeon.rs != AbstractDungeon.RenderScene.EVENT)
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F);
    }

    @Override
    public void dispose() {
        this.img.dispose();
    }
}
