package VUPShionMod.helpers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerAllFinFunnelAction;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ChargeHelper implements Disposable {
    private Texture mainImg15 = ImageMaster.loadImage("VUPShionMod/img/ui/ChargeUI/ChargeUI15.png");
    private Texture mainImg10 = ImageMaster.loadImage("VUPShionMod/img/ui/ChargeUI/ChargeUI10.png");
    private Texture iconImg15 = ImageMaster.loadImage("VUPShionMod/img/ui/ChargeUI/ChargeIcon15.png");
    private Texture iconImg10 = ImageMaster.loadImage("VUPShionMod/img/ui/ChargeUI/ChargeIcon10.png");
    private int count = 0;
    private AbstractPlayer p = AbstractDungeon.player;
    public boolean active = false;
    private float drawX = 0.0f;
    private float drawY = 0.0f;
    private float drawScale = 0.8f;
    private Hitbox hb = new Hitbox(800 * Settings.scale, 240 * Settings.scale);

    public String name;
    public String description;

    private int damage = 200;

    public ChargeHelper() {
        name = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("ChargeHelper")).TEXT[0];
        description = String.format(CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("ChargeHelper")).TEXT[1], damage);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(770, 771);

        if (!AbstractDungeon.player.isDead)
            if (AbstractDungeon.ascensionLevel >= 19) {
                render15UI(sb);
            } else {
                render10UI(sb);
            }

        this.hb.render(sb);
    }

    private void render15UI(SpriteBatch sb) {
        sb.draw(this.mainImg15,
                this.drawX - 500.0f, this.drawY - 165.0f,
                500.0f, 165.0f,
                1000.0f, 329.0f,
                drawScale * Settings.scale,
                drawScale * Settings.scale,
                0, 0, 0, 1000, 329, false, false
        );

        for (int i = 0; i < this.count; i++)
            sb.draw(this.iconImg15,
                    this.drawX - 25.0f - 314.0f * Settings.scale + i * 44.25f * Settings.scale, this.drawY - 15.0f,
                    25.0f, 15.0f,
                    50.0f, 31.0f,
                    drawScale * Settings.scale,
                    drawScale * Settings.scale,
                    0, 0, 0, 50, 31, false, false
            );

    }

    private void render10UI(SpriteBatch sb) {
        sb.draw(this.mainImg10,
                this.drawX - 500.0f, this.drawY - 165.0f,
                500.0f, 165.0f,
                1000.0f, 329.0f,
                drawScale * Settings.scale,
                drawScale * Settings.scale,
                0, 0, 0, 1000, 329, false, false
        );

        for (int i = 0; i < this.count; i++)
            sb.draw(this.iconImg10,
                    this.drawX - 25.0f - 200.0f * Settings.scale + i * 43.7f * Settings.scale, this.drawY - 14.0f,
                    25.0f, 14.0f,
                    50.0f, 27.0f,
                    drawScale * Settings.scale,
                    drawScale * Settings.scale,
                    0, 0, 0, 50, 27, false, false
            );
    }

    public void update() {
        this.drawX = Settings.WIDTH / 2.0f;
        this.drawY = Settings.HEIGHT - 192.0f * Settings.scale;

        this.hb.update();
        this.hb.move(this.drawX, this.drawY);
        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(this.drawX + 96.0F * Settings.scale, this.drawY + 64.0F * Settings.scale, this.name, this.description);
        }
    }

    public void addCount(int amount) {
        this.count += amount;
        checkIfTrigger();
    }

    private void checkIfTrigger() {
        if (AbstractDungeon.ascensionLevel >= 19) {
            if (this.count >= 15) {
                this.count = 0;
                triggerEffect();
            }
        } else {
            if (this.count >= 10) {
                this.count = 0;
                triggerEffect();
            }
        }

    }

    private void triggerEffect() {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_IRON_2", -0.5F, true));
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AbstractAtlasGameEffect(
                        "Fire 071 Ray Shot Up MIX", monster.hb.cX, monster.hb.y + 550.f * Settings.scale,
                        130.0f, 213.0f, 3.0f * Settings.scale, 3, false)));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        AbstractDungeon.actionManager.addToBottom(new TriggerAllFinFunnelAction(true));
    }

    @Override
    public void dispose() {
        this.mainImg15.dispose();
        this.mainImg10.dispose();
        this.iconImg15.dispose();
        this.iconImg10.dispose();
    }


}
