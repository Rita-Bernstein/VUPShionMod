package VUPShionMod.vfx.Common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.function.Consumer;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

public class AbstractSpineEffect extends AbstractGameEffect {
    private TextureAtlas Atlas = null;
    private com.esotericsoftware.spine.Skeleton Skeleton;
    private AnimationState State;
    private AnimationStateData StateData;
    private SkeletonData Data;


    private Consumer<AnimationState> stateConsumer;


    public AbstractSpineEffect(boolean renderBehind, String path, float x, float y, float scale, float speed, float duration, Consumer<AnimationState> stateConsumer) {
        this.renderBehind = renderBehind;
        this.duration = duration;
        if (stateConsumer == null) {
            this.isDone = true;
        } else {
            this.stateConsumer = stateConsumer;
            loadanimation(path, scale, speed);

            this.stateConsumer.accept(this.State);
            Skeleton.setPosition(x, y);
        }

    }

    private void loadanimation(String path, float scale, float speed) {
        Atlas = new TextureAtlas(Gdx.files.internal(path + ".atlas"));
        SkeletonJson json = new SkeletonJson(Atlas);
        json.setScale(Settings.scale / scale);
        Data = json.readSkeletonData(Gdx.files.internal(path + ".json"));
        Skeleton = new Skeleton(Data);
        Skeleton.setColor(Color.WHITE);
        StateData = new AnimationStateData(Data);
        State = new AnimationState(StateData);
        StateData.setDefaultMix(0.2F);

        State.setTimeScale(1.0f * speed);
    }


    public void update() {
        if (this.duration > 0) {
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;

            }
        }

        if(this.isDone){
            dispose();
        }
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
        this.Atlas.dispose();
        this.Atlas = null;
    }
}


