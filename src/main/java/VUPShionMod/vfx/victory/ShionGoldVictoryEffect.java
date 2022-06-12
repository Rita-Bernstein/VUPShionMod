package VUPShionMod.vfx.victory;

import VUPShionMod.util.SaveHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

public class ShionGoldVictoryEffect extends AbstractGameEffect {

    private float speed;

    private static TextureAtlas Atlas = null;
    private static com.esotericsoftware.spine.Skeleton Skeleton;
    private static AnimationState State;
    private static AnimationStateData StateData;
    private static SkeletonData Data;

    public ShionGoldVictoryEffect(float speed) {
        this.speed = speed;
        this.duration = 3.0f;

        loadanimation(this.speed);
    }

    public ShionGoldVictoryEffect() {
        this(1.0f);
    }

    private static void loadanimation(float timeScale) {
        Atlas = new TextureAtlas(Gdx.files.internal("VUPShionMod/characters/Shion/portrait_spine/Shion.atlas"));
        SkeletonJson json = new SkeletonJson(Atlas);
        json.setScale(Settings.scale * 1.0f);
        Data = json.readSkeletonData(Gdx.files.internal("VUPShionMod/characters/Shion/portrait_spine/Shion.json"));


        Skeleton = new Skeleton(Data);
        Skeleton.setColor(Color.WHITE);
        StateData = new AnimationStateData(Data);
        State = new AnimationState(StateData);
        StateData.setDefaultMix(0.2F);

        State.setTimeScale(1.0f * timeScale);
        Skeleton.setPosition(0.0f * Settings.scale, -40.0f * Settings.scale);

        State.setAnimation(0, "idle", true);
    }


    public void update() {
//        this.duration -= Gdx.graphics.getDeltaTime();
//        if (this.duration < 0.0F) {
//            this.isDone = true;
//        }
    }

    public void render(SpriteBatch sb) {
        State.update(Gdx.graphics.getDeltaTime());
        State.apply(Skeleton);
        Skeleton.updateWorldTransform();
        Skeleton.setColor(Color.WHITE);
        Skeleton.setFlip(false, false);


        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, Skeleton);
        CardCrawlGame.psb.end();
        sb.begin();

    }

    public void dispose() {
        Atlas.dispose();
    }
}
