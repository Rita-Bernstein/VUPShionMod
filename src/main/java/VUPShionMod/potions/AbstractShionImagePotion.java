package VUPShionMod.potions;


import VUPShionMod.cards.WangChuan.Reflect;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class AbstractShionImagePotion extends AbstractShionPotion {
    protected Texture potionImg;
    protected Texture potionOutlineImg;
    public Color potionImageColor = Color.WHITE.cpy();

    public AbstractShionImagePotion(String id, String potionImg, PotionRarity rarity) {
        super(id, rarity, PotionSize.S);
        this.potionImg = ImageMaster.loadImage("VUPShionMod/img/potions/" + potionImg);
        this.potionOutlineImg = ImageMaster.loadImage("VUPShionMod/img/potions/outline/" + potionImg);
    }


    @Override
    public void render(SpriteBatch sb) {
        if (this.potionImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(this.potionImageColor);
            sb.draw(this.potionImg, this.posX - 32.0F, this.posY - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale,
                    angle, 0, 0, 64, 64, false, false);
        }

        ArrayList<FlashPotionEffect> effects = ReflectionHacks.getPrivate(this, AbstractPotion.class, "effect");
        for (FlashPotionEffect e : effects) {
            e.render(sb, this.posX, this.posY);
        }

        if (this.hb != null) {
            this.hb.render(sb);
        }
    }


    @Override
    public void renderLightOutline(SpriteBatch sb) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale,
                    angle, 0, 0, 64, 64, false, false);
        }
    }

    @Override
    public void renderOutline(SpriteBatch sb) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(Settings.HALF_TRANSPARENT_BLACK_COLOR);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale,
                    angle, 0, 0, 64, 64, false, false);
        }

    }

    @Override
    public void renderOutline(SpriteBatch sb, Color c) {
        if (this.potionOutlineImg != null) {
            float angle = ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle");
            sb.setColor(c);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale,
                    angle, 0, 0, 64, 64, false, false);
        }

    }

    @Override
    public void renderShiny(SpriteBatch sb) {
    }

    @Override
    public void labRender(SpriteBatch sb) {
        try {
            Method updateFlash = AbstractPotion.class.getDeclaredMethod("updateFlash");
            updateFlash.setAccessible(true);

            Method updateEffect = AbstractPotion.class.getDeclaredMethod("updateEffect");
            updateEffect.setAccessible(true);

            updateFlash.invoke(this);
            updateEffect.invoke(this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (this.hb.hovered) {
            TipHelper.queuePowerTips(150.0F * Settings.scale, 800.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }

        renderOutline(sb, this.labOutlineColor);
        render(sb);
    }

    @Override
    public void shopRender(SpriteBatch sb) {
        try {
            Method updateFlash = AbstractPotion.class.getDeclaredMethod("updateFlash");
            updateFlash.setAccessible(true);

            Method updateEffect = AbstractPotion.class.getDeclaredMethod("updateEffect");
            updateEffect.setAccessible(true);

            updateFlash.invoke(this);
            updateEffect.invoke(this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (this.hb.hovered) {
            TipHelper.queuePowerTips(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY + 50.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }

        renderOutline(sb);
        render(sb);
    }
}
