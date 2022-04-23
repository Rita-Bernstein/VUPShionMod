package VUPShionMod.prayers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPrayerPatches;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
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
        this.img = ImageMaster.loadImage("VUPShionMod/img/prayer/Prayer.png");
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
        this.hb.move(x, y);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        sb.draw(this.img, this.x - 24.0f, this.y - 24.0f,
                24.0F, 24.0F, 48.0F, 48.0F,
                1.0f * Settings.scale, 1.0f * Settings.scale,
                0.0f, 0, 0, 48, 48, false, false);

        FontHelper.renderFontCentered(sb, FontHelper.powerAmountFont, Integer.toString(this.turns), this.x, this.y, Color.WHITE.cpy(), 1.8f);
        this.hb.render(sb);
        this.hasThisTurn = false;
    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
