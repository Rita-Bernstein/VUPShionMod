package VUPShionMod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShionBossBackgroundEffect extends AbstractGameEffect {
    protected TextureAtlas atlas = null;
    protected Skeleton skeleton;
    public AnimationState state;
    protected AnimationStateData stateData;

    public static ShionBossBackgroundEffect instance;

    public float drawX = 0.0f;
    public float drawY = 0.0f;

    public ShionBossBackgroundEffect() {
        if (instance != null) {
            instance.isDone = true;
        }
        instance = this;

        this.renderBehind = true;

        loadAnimation("VUPShionMod/img/monsters/PlagaAMundo/ShionBackground.atlas",
                "VUPShionMod/img/monsters/PlagaAMundo/ShionBackground.json", 1.0f);

        this.state.setAnimation(0, "Background_idle1", true);
    }

    public void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);

        json.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }

    public void setAnimation(int trackIndex, String animationName,boolean loop) {
        this.state.setAnimation(trackIndex, animationName, loop);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch sb) {
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.updateWorldTransform();
        this.skeleton.setPosition(this.drawX, this.drawY);
        this.skeleton.setColor(Color.WHITE);

        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }
}
