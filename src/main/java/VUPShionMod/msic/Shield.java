package VUPShionMod.msic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.combat.BlockImpactLineEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedNumberEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;

public class Shield implements Disposable {
    private Texture blockImg = ImageMaster.loadImage("VUPShionMod/img/ui/Shield.png");
    private int currentShield = 0;

    protected static final float BLOCK_ICON_X = -14.0F * Settings.scale;
    protected static final float BLOCK_ICON_Y = -14.0F * Settings.scale;
    private Color blockColor = Color.WHITE.cpy();
    private Color blockTextColor = new Color(0.9F, 0.9F, 0.9F, 1.0F);
    private float renderScale = 0.8f;

    public Shield() {
    }

    public void update() {
    }

    public void render(SpriteBatch sb, float x, float y, float blockOffset, float blockScale) {
        sb.setColor(this.blockColor);

        if (this.currentShield > 0) {
            if (AbstractDungeon.player.currentBlock > 0) {
                sb.draw(blockImg,
                        x + BLOCK_ICON_X - 50.0F, y + BLOCK_ICON_Y - 50.0F + blockOffset + 50.0f * Settings.scale * renderScale,
                        50.0F, 50.0F,
                        100.0F, 100.0F,
                        renderScale * Settings.scale, renderScale * Settings.scale,
                        0, 0, 0,
                        100, 100, false, false);

                FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(this.currentShield),
                        x + BLOCK_ICON_X, y - 16.0F * Settings.scale + 50.0f * Settings.scale * renderScale, this.blockTextColor, 1.0f);
            } else {
                sb.draw(blockImg,
                        x + BLOCK_ICON_X - 50.0F, y + BLOCK_ICON_Y - 50.0F + blockOffset,
                        50.0F, 50.0F,
                        100.0F, 100.0F,
                        renderScale * Settings.scale, renderScale * Settings.scale,
                        0, 0, 0,
                        100, 100, false, false);

                FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(this.currentShield),
                        x + BLOCK_ICON_X, y - 16.0F * Settings.scale, this.blockTextColor, 1.0f);
            }

        }
    }


    public void addBlock(int blockAmount) {
        float tmp = blockAmount;
        this.currentShield += MathUtils.floor(tmp);

        if (this.currentShield > 999) {
            this.currentShield = 999;
        }
    }

    public int decrementBlock(DamageInfo info, int damageAmount, AbstractCreature creature) {
        if (info.type != DamageInfo.DamageType.HP_LOSS && this.currentShield > 0) {
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);


            if (damageAmount > this.currentShield) {
                damageAmount -= this.currentShield;
                if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(creature.hb.cX, creature.hb.cY + creature.hb.height / 2.0F,
                            Integer.toString(this.currentShield)));
                }
                loseBlock();
                brokeBlock();

            } else if (damageAmount == this.currentShield) {
                damageAmount = 0;
                loseBlock();
                brokeBlock();
                AbstractDungeon.effectList.add(new BlockedWordEffect(creature, creature.hb.cX, creature.hb.cY,
                        CardCrawlGame.languagePack.getUIString("AbstractCreature").TEXT[1]));
            } else {

                CardCrawlGame.sound.play("BLOCK_ATTACK");
                loseBlock(damageAmount);
                for (int i = 0; i < 18; i++) {
                    AbstractDungeon.effectList.add(new BlockImpactLineEffect(creature.hb.cX, creature.hb.cY));
                }
                if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(creature.hb.cX, creature.hb.cY + creature.hb.height / 2.0F,
                            Integer.toString(damageAmount)));
                }
                damageAmount = 0;
            }
        }
        return damageAmount;
    }

    private void loseBlock(int amount) {
        this.currentShield -= amount;
        if (this.currentShield < 0) {
            this.currentShield = 0;
        }
    }

    public void loseBlock() {
        loseBlock(this.currentShield);
    }

    private void brokeBlock() {
        CardCrawlGame.sound.play("BLOCK_BREAK");
    }

    @Override
    public void dispose() {
        blockImg.dispose();
    }
}
