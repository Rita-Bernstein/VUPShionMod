package VUPShionMod.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardTypeHelper {

    public static class RenderTypeColor {
        public static Color typeCommonColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
        public static Color typeUncommonColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        public static Color typeRareColor = new Color(1.0f, 0.96f, 0.83f, 1.0f);
    }

    public static void renderRotatedText(SpriteBatch sb, BitmapFont font, String msg, float x, float y, float offsetX, float offsetY, float angle, boolean roundY, Color c) {
        if (roundY) {
            y = Math.round(y) + 0.25F;
        }

        if ((font.getData()).scaleX == 1.0F) {
            x = MathUtils.round(x);
            y = MathUtils.round(y);
            offsetX = MathUtils.round(offsetX);
            offsetY = MathUtils.round(offsetY);
        }

        Matrix4 mx4 = new Matrix4();
        Vector2 rotatedTextTmp = new Vector2(0.0F, 0.0F);
        GlyphLayout layout = new GlyphLayout();
        mx4.setToRotation(0.0F, 0.0F, 1.0F, angle);
        Matrix4 rotatedTextMatrix = new Matrix4();

        rotatedTextTmp.x = offsetX;
        rotatedTextTmp.y = offsetY;
        rotatedTextTmp.rotate(angle);
        mx4.trn(x + rotatedTextTmp.x, y + rotatedTextTmp.y, 0.0F);
        sb.end();
        sb.setTransformMatrix(mx4);
        sb.begin();


        font.setColor(c);
        layout.setText(font, msg);
        font.draw(sb, msg, 0, layout.height / 2.0F);

        sb.end();
        sb.setTransformMatrix(rotatedTextMatrix);
        sb.begin();
    }

    public static void renderRotatedText(SpriteBatch sb, BitmapFont font, String msg, float x, float y, float offsetX, float offsetY, float angle, boolean roundY, Color c, AbstractCard.CardRarity rarity) {


        if (roundY) {
            y = Math.round(y) + 0.25F;
        }

        if ((font.getData()).scaleX == 1.0F) {
            x = MathUtils.round(x);
            y = MathUtils.round(y);
            offsetX = MathUtils.round(offsetX);
            offsetY = MathUtils.round(offsetY);
        }

        Matrix4 mx4 = new Matrix4();
        Vector2 rotatedTextTmp = new Vector2(0.0F, 0.0F);
        GlyphLayout layout = new GlyphLayout();
        mx4.setToRotation(0.0F, 0.0F, 1.0F, angle);
        Matrix4 rotatedTextMatrix = new Matrix4();

        rotatedTextTmp.x = offsetX;
        rotatedTextTmp.y = offsetY;
        rotatedTextTmp.rotate(angle);
        mx4.trn(x + rotatedTextTmp.x, y + rotatedTextTmp.y, 0.0F);
        sb.end();
        sb.setTransformMatrix(mx4);
        sb.begin();

        switch (rarity) {
            case RARE:
                RenderTypeColor.typeRareColor.a = c.a;
                font.setColor(RenderTypeColor.typeRareColor);
                break;
            case UNCOMMON:
                RenderTypeColor.typeUncommonColor.a = c.a;
                font.setColor(RenderTypeColor.typeUncommonColor);
                break;
            default:
                RenderTypeColor.typeCommonColor.a = c.a;
                font.setColor(RenderTypeColor.typeCommonColor);
                break;
        }


        layout.setText(font, msg);
        font.draw(sb, msg, -layout.width / 2.0F, layout.height / 2.0F);

        sb.end();
        sb.setTransformMatrix(rotatedTextMatrix);
        sb.begin();
    }

}
