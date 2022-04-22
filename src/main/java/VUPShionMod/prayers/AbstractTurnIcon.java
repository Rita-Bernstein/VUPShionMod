package VUPShionMod.prayers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPrayerPatches;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class AbstractTurnIcon implements Disposable {
    public Texture img;
    public Hitbox hb = new Hitbox(48.0f * Settings.scale, 48.0f * Settings.scale);

    private static final OrbStrings prayerStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID("AbstractTurnIcon"));
    public static final String NAME = prayerStrings.NAME;
    public static final String[] DESCRIPTIONS = prayerStrings.DESCRIPTION;
    public String name;
    public String description;
    public int turns = 1;

    public float x;
    public float y;

    public boolean hasThisTurn = false;

    public AbstractTurnIcon(int turns) {
        this.turns = turns;
        this.img = ImageMaster.loadImage("VUPShionMod/img/prayer/Prayer" + this.turns + ".png");
        this.name = NAME;
        this.description = String.format(DESCRIPTIONS[0], this.turns);
    }

    public void update() {
        this.hb.update();

        if (this.turns > 1)
            this.description = String.format(DESCRIPTIONS[1], this.turns);
        else
            this.description = String.format(DESCRIPTIONS[0], this.turns);

        if (!AbstractPrayerPatches.prayers.isEmpty())
            for (AbstractPrayer prayer : AbstractPrayerPatches.prayers) {
                if (prayer.turns == this.turns) {
                    this.description += " NL " + prayer.description;
                }
            }


        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.x + 96.0F * Settings.scale, this.y + 64.0F * Settings.scale, this.name, this.description);
        }

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hb.move(x + 24.0f * Settings.scale, y + 24.0f * Settings.scale);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.x, this.y,
                36.0F, 16.0F, 36.0F, 36.0F,
                1.0f * Settings.scale, 1.0f * Settings.scale,
                0.0f, 0, 0, 36, 36, false, false);

        this.hb.render(sb);
        this.hasThisTurn = false;
    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
