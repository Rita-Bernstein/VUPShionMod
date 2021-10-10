package VUPShionMod.effects;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FinFunnelSelectedEffect extends AbstractGameEffect {
    private Texture img;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public boolean selfPos = false;
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
        if (selfPos) {
            this.cX = AbstractPlayerPatches.AddFields.activatedFinFunnel.get(AbstractDungeon.player).cX;
            this.cY = AbstractPlayerPatches.AddFields.activatedFinFunnel.get(AbstractDungeon.player).cY;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        if (AbstractDungeon.rs != AbstractDungeon.RenderScene.EVENT && !AbstractDungeon.isScreenUp)
            sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F,
            24.0f,24.0f,
                    48.0f,48.0f,
                    Settings.scale,Settings.scale,
                    0.0f,0,0,
                    48,48,
                    false,false
            );


    }

    @Override
    public void dispose() {
        this.img.dispose();
    }
}
