package VUPShionMod.modules;

import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.GravityFinFunnelUpgradePower;
import VUPShionMod.powers.InvestigationFinFunnelUpgradePower;
import VUPShionMod.powers.PursuitFinFunnelUpgradePower;
import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnergyOrbWangChuan extends CustomEnergyOrb {
    private static final float ORB_IMG_SCALE = 1.15F * Settings.scale;
    private float angle4;
    private float angle3;
    private float angle2;
    private float angle1;

    private Color glowColor = Color.WHITE.cpy();
    private float duration = 1.0f;
    private float timer = 0.0f;

    public EnergyOrbWangChuan(String[] orbTexturePaths, String orbVfxPath) {
        super(orbTexturePaths, orbVfxPath, null);
    }

    @Override
    public void updateOrb(int orbCount) {
        this.timer += Gdx.graphics.getDeltaTime();
        this.duration = (float) Math.sin(timer) / 2.0f + 1.0f;
        glowColor.a = Interpolation.fade.apply(0.6F, 0.2F, this.duration);


        if (orbCount == 0) {
            this.angle4 += Gdx.graphics.getDeltaTime() * 5.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * -5.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * 8.0F;
            this.angle1 += Gdx.graphics.getDeltaTime() * -8.0F;
        } else {
            this.angle4 += Gdx.graphics.getDeltaTime() * 20.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * -20.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * 40.0F;
            this.angle1 += Gdx.graphics.getDeltaTime() * -40.0F;
        }
    }


    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        if (enabled) {
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);

            sb.draw(this.energyLayers[0], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 442, 442, false, false);

            sb.draw(this.energyLayers[1], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 442, 442, false, false);

            sb.setBlendFunction(770, 1);
            sb.draw(this.energyLayers[2], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 442, 442, false, false);
            sb.setBlendFunction(770, 771);


            sb.draw(this.energyLayers[3], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 442, 442, false, false);
            sb.draw(this.energyLayers[4], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 442, 442, false, false);

            sb.draw(this.energyLayers[5], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 442, 442, false, false);

            sb.setColor(glowColor);
            sb.setBlendFunction(770, 1);
            sb.draw(this.energyLayers[6], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 442, 442, false, false);
            sb.setBlendFunction(770, 771);


        } else {

            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);

            sb.draw(this.energyLayers[0], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 442, 442, false, false);
            sb.draw(this.energyLayers[3], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 442, 442, false, false);
            sb.draw(this.energyLayers[4], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 442, 442, false, false);
            sb.draw(this.energyLayers[5], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 442, 442, false, false);

        }


        sb.setColor(EnergyPanelPatches.levelColor);
        System.out.println(EnergyPanelPatches.levelColor.a);
        sb.draw(this.noEnergyLayers[6], current_x - 221.0F, current_y - 221.0F, 221.0F, 221.0F, 442.0F, 442.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 442, 442, false, false);
    }

}
