package VUPShionMod.vfx.Monster.Boss;

import VUPShionMod.monsters.Rita.AbstractMonsterIntent;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;

public class MonsterDivinityParticleEffect extends DivinityParticleEffect {
    private TextureAtlas.AtlasRegion img;

    public MonsterDivinityParticleEffect(AbstractCreature owner){
        super();
        this.img = ImageMaster.EYE_ANIM_0;
        float x = owner.hb.cX + MathUtils.random(-owner.hb.width / 2.0F - 50.0F * Settings.scale, owner.hb.width / 2.0F + 50.0F * Settings.scale);
        float y = owner.hb.cY + MathUtils.random(-owner.hb.height / 2.0F + 10.0F * Settings.scale, owner.hb.height / 2.0F - 20.0F * Settings.scale);


        if (x > owner.hb.cX) {
            this.rotation = -this.rotation;
        }

        x -= (float)this.img.packedWidth / 2.0F;
        y -= (float)this.img.packedHeight / 2.0F;

        ReflectionHacks.setPrivate(this,DivinityParticleEffect.class,"x",x);
        ReflectionHacks.setPrivate(this,DivinityParticleEffect.class,"y",y);
    }
}
