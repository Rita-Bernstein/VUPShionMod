package VUPShionMod.vfx.Common;

import VUPShionMod.util.SaveHelper;
import VUPShionMod.vfx.Shion.LargPortraitFlashInEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.PetalEffect;

public class PortraitWindyPetalEffect extends AbstractGameEffect {
    private float timer = 0.1F;
    private float timer2 = 0.05F;
    private String name = "";
    private boolean justStart = true;

    public PortraitWindyPetalEffect() {
        this.duration = 2.5f;
    }

    public PortraitWindyPetalEffect(String name) {
        this.duration = 2.5f;
        this.name = name;
    }


    @Override
    public void update() {
        if (!SaveHelper.safeCampfire) {
            isDone = true;
            return;
        }
        if (this.justStart) {
            this.justStart = false;
            if (!this.name.equals("")) {
                AbstractDungeon.topLevelEffects.add(new LargPortraitFlashInEffect(this.name));
            }
        }


        this.duration -= Gdx.graphics.getDeltaTime();
        this.timer -= Gdx.graphics.getDeltaTime();
        this.timer2 -= Gdx.graphics.getDeltaTime();

        if (this.timer < 0.0F && this.duration > 0.5f) {
            this.timer += 0.1F;
            for (int i = 0; i < 2; i++) {
                AbstractGameEffect effect = new PetalEffect();
                effect.duration = this.duration - 0.5f;
                AbstractDungeon.effectsQueue.add(effect);
            }
        }

        if (this.timer2 < 0.0F && this.duration > 0.5f) {
            this.timer2 += 0.05F;
            AbstractDungeon.effectsQueue.add(new PortraitWindyParticleEffect(new Color(0.9F, 0.9F, 1.0F, 1.0F), true));
        }

        if (this.duration < 0.0F)
            this.isDone = true;

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {
    }
}
