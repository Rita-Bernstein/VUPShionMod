package VUPShionMod.vfx.Monster.Boss;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

public class MonsterStanceAuraEffect extends StanceAuraEffect {
    private TextureAtlas.AtlasRegion img;

    public MonsterStanceAuraEffect(AbstractCreature owner, String stanceId) {
        super(stanceId);
        this.img = ImageMaster.EXHAUST_L;

        float x = owner.hb.cX + MathUtils.random(-owner.hb.width / 16.0F, owner.hb.width / 16.0F);
        float y = owner.hb.cY + MathUtils.random(-owner.hb.height / 16.0F, owner.hb.height / 12.0F);
        x -= (float) this.img.packedWidth / 2.0F;
        y -= (float) this.img.packedHeight / 2.0F;

        ReflectionHacks.setPrivate(this, StanceAuraEffect.class, "x", x);
        ReflectionHacks.setPrivate(this, StanceAuraEffect.class, "y", y);
    }
}
