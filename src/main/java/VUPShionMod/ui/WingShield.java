package VUPShionMod.ui;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainWingShieldChargeAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.patches.GameStatsPatch;
import VUPShionMod.relics.EisluRen.ShieldHRzy1;
import VUPShionMod.relics.EisluRen.ShieldHRzy2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;


public class WingShield implements Disposable {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(WingShield.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;
    public String name = TEXT[0];
    public String description = TEXT[1];

    private Texture body;
    private Texture center;

    private int count = 7;
    private int maxCount = 21;
    private int refundCharge = 0;
    private int damageReceived = 0;

    private int baseShieldDamageReduce = 2;
    private int shieldDamageReduce = 2;
    private int shieldDamageReduceCombat = 2;

    private Color color = Color.WHITE.cpy();

    public float cX = 0.0f;
    public float cY = 0.0f;
    public float scale = 0.33f;

    private Hitbox hb;

    private Texture[] iconTextures = new Texture[5];

    private ArrayList<WingShieldIcon> shieldIcons = new ArrayList<>();

    public WingShield() {
        this.body = ImageMaster.loadImage("VUPShionMod/img/ui/WingShield/Body.png");
        this.center = ImageMaster.loadImage("VUPShionMod/img/ui/WingShield/Center.png");

        for (int i = 0; i < 5; i++) {
            iconTextures[i] = ImageMaster.loadImage("VUPShionMod/img/ui/WingShield/Icon" + i + ".png");
        }

        if (shieldIcons.isEmpty()) {
            for (int i = 0; i < 7; i++) {
                shieldIcons.add(new WingShieldIcon(i));
            }
        }

        this.count = WingShieldSave.wingShieldSaveAmount;
        this.refundCharge = WingShieldRefundSave.wingShieldRefundSaveAmount;
        this.damageReceived = WingShieldDamageSave.wingShieldDamageSaveAmount;

        this.hb = new Hitbox(600.0f * Settings.scale * this.scale, 600.0f * Settings.scale * this.scale);

        updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(TEXT[1], this.damageReceived, getDamageReduce(), this.refundCharge, this.count, this.maxCount);
    }

    public void updatePos(EnergyPanel _instance) {
        this.cX = _instance.current_x;
        this.cY = _instance.current_y + 180.0f * Settings.scale;
        this.hb.move(this.cX, this.cY);

        for (WingShieldIcon icon : this.shieldIcons) {
            icon.update(this.cX, this.cY, this.scale);
        }
    }

    public void update() {
        this.hb.update();
    }

    private void updateShieldIcon() {
        for (WingShieldIcon icon : this.shieldIcons) {
            icon.reset();
            icon.setCount(this.count / 7);
        }

        if (this.count % 7 > 0) {
            for (int i = 0; i < this.count % 7; i++) {
                this.shieldIcons.get(i).setCount(this.shieldIcons.get(i).getCount() + 1);
            }
        }

        updateRefund();
    }

    public void updateRefund() {
        for (WingShieldIcon icon : this.shieldIcons) {
            icon.setRefundCharge(0);
        }

        this.shieldIcons.get(this.count % 7).setRefundCharge(this.refundCharge);
    }

    public void render(SpriteBatch sb) {
        this.color.a = 1.0f;
        sb.setColor(this.color);

        if (this.body != null) {
            sb.draw(this.body, this.cX - this.body.getWidth() / 2.0f, this.cY - this.body.getHeight() / 2.0f,
                    this.body.getWidth() / 2.0f, this.body.getHeight() / 2.0f, this.body.getWidth(), this.body.getHeight(),
                    this.scale * Settings.scale, this.scale * Settings.scale,
                    0.0f,
                    0, 0, this.body.getWidth(), this.body.getHeight(), false, false);
        }

        if (!this.shieldIcons.isEmpty() && this.iconTextures.length > 0) {
            for (WingShieldIcon icon : this.shieldIcons) {
                icon.render(sb, this.iconTextures);
            }
        }

        this.color.a = 1.0f;
        sb.setColor(this.color);
        if (this.center != null) {
            sb.draw(this.center, this.cX - this.center.getWidth() / 2.0f, this.cY - this.center.getHeight() / 2.0f,
                    this.center.getWidth() / 2.0f, this.center.getHeight() / 2.0f, this.center.getWidth(), this.center.getHeight(),
                    this.scale * Settings.scale, this.scale * Settings.scale,
                    0.0f,
                    0, 0, this.center.getWidth(), this.center.getHeight(), false, false);
        }


        this.color.a = 1.0f;
        sb.setColor(this.color);

        this.hb.render(sb);

        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(this.hb.cX + this.hb.width * 0.5f,
                    0.75f * Settings.HEIGHT, name, description);
        }

    }

    public void addCharge() {
        int pre = this.count % 7;
        this.count++;
        if (this.count > this.maxCount) {
            this.count = maxCount;
        } else {
            this.shieldIcons.get(pre).setCount(this.shieldIcons.get(pre).getCount() + 1);
            this.shieldIcons.get(pre).show();
        }

        WingShieldSave.wingShieldSaveAmount = this.count;
        updateDescription();
    }


    public void loseCharge() {
        this.count--;
        if (this.count < 0) {
            this.count = 0;
        } else {
            this.shieldIcons.get(this.count % 7).setCount(this.shieldIcons.get(this.count % 7).getCount() - 1);
            this.shieldIcons.get(this.count % 7).hide();
        }

        WingShieldSave.wingShieldSaveAmount = this.count;
        updateDescription();
    }

    public void addRefundCount(int amount) {
        this.refundCharge += amount;
        if (this.refundCharge >= 3) {
            if (this.refundCharge >= 6)
                addToBot(new GainWingShieldChargeAction((this.refundCharge / 3) - 1, this.refundCharge / 3));
            this.refundCharge %= 3;
            addChargeQuick(1);
        } else
            this.shieldIcons.get(this.count % 7).setRefundCharge(this.refundCharge);

        WingShieldRefundSave.wingShieldRefundSaveAmount = this.refundCharge;
        updateDescription();
    }

    public void addChargeQuick(int amount) {
        this.count += amount;
        if (this.count > this.maxCount) {
            this.count = maxCount;
        }

        WingShieldSave.wingShieldSaveAmount = this.count;
        updateDescription();
        updateShieldIcon();
    }

    public void loseChargeQuick(int amount) {
        this.count -= amount;
        if (this.count < 0)
            this.count = 0;

        WingShieldSave.wingShieldSaveAmount = this.count;
        updateDescription();
        updateShieldIcon();
    }

    public void atStartOfCombat() {
//        this.count = WingShieldSave.wingShieldSaveAmount;
//        this.refundCharge = WingShieldRefundSave.wingShieldRefundSaveAmount;
//        this.damageReceived = WingShieldDamageSave.wingShieldDamageSaveAmount;

        this.count = 7;
        this.refundCharge = 0;
        this.damageReceived = 0;

        WingShieldSave.wingShieldSaveAmount = 7;
        WingShieldRefundSave.wingShieldRefundSaveAmount = 0;
        WingShieldDamageSave.wingShieldDamageSaveAmount = 0;

        this.shieldDamageReduceCombat = this.baseShieldDamageReduce;
        this.shieldDamageReduce = this.baseShieldDamageReduce;


        if (!canUseWingShield()) {
            reset();
        }
        updateDescription();
        updateShieldIcon();
    }


    public void atStartOfTurn() {
        this.shieldDamageReduce = this.shieldDamageReduceCombat;
        updateDescription();
    }

    public static WingShield getWingShield() {
        return EnergyPanelPatches.PatchEnergyPanelField.wingShield.get(AbstractDungeon.overlayMenu.energyPanel);
    }

    public int getCount() {
        return this.count;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public void reset() {
        this.count = 0;
        this.refundCharge = 0;

        WingShieldSave.wingShieldSaveAmount = 0;
        WingShieldRefundSave.wingShieldRefundSaveAmount = 0;
        WingShieldDamageSave.wingShieldDamageSaveAmount = 0;

        updateShieldIcon();
    }


    public void upgradeMax() {
        this.maxCount = 28;
    }


    @Override
    public void dispose() {
        if (this.body != null) {
            this.body.dispose();
            this.body = null;
        }
        if (this.center != null) {
            this.center.dispose();
            this.center = null;
        }

        for (Texture texture : iconTextures) {
            texture.dispose();
            texture = null;
        }
    }

    public void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }


    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            if (damageAmount > 0 && canUseWingShield() && this.count > 0) {
                if ((damageReceived + damageAmount) / getDamageReduce() > 0) {
                    int loseCharge = (damageReceived + damageAmount) / getDamageReduce();
                    if (loseCharge > 7) loseCharge = 7;

                    if (loseCharge < this.count) {
                        addToTop(new LoseWingShieldAction(loseCharge));
                        if (loseCharge >= 7)
                            this.damageReceived = 0;
                        else
                            this.damageReceived = (damageReceived + damageAmount) % getDamageReduce();

                        GameStatsPatch.wingShieldDamageReduceThisCombat += damageAmount;
                        WingShieldDamageSave.wingShieldDamageSaveAmount = this.damageReceived;
                        updateDescription();
                        return 0;
                    } else {
                        addToTop(new LoseWingShieldAction(this.count));
                        GameStatsPatch.wingShieldDamageReduceThisCombat += getDamageReduce() * this.count - this.damageReceived;
                        WingShieldDamageSave.wingShieldDamageSaveAmount = 0;

                        int tmp = this.damageReceived;
                        if(tmp > getDamageReduce()) tmp = getDamageReduce();
                        this.damageReceived = 0;

                        updateDescription();
                        return damageAmount - (getDamageReduce() - 1) * this.count - (getDamageReduce() - tmp);
                    }

                } else {
                    damageReceived += damageAmount;
                    GameStatsPatch.wingShieldDamageReduceThisCombat += damageAmount;
                    WingShieldDamageSave.wingShieldDamageSaveAmount = this.damageReceived;
                    updateDescription();
                    return 0;
                }
            }
        }
        return damageAmount;
    }

    public void increaseDamageReduce(int amount) {
        this.shieldDamageReduce += amount;
        updateDescription();
    }

    public void increaseDamageReduceCombat(int amount) {
        this.shieldDamageReduceCombat += amount;

        if (this.shieldDamageReduce < this.shieldDamageReduceCombat) {
            this.shieldDamageReduce = this.shieldDamageReduceCombat;
        }
        updateDescription();
    }

    public int getDamageReduce() {
        return shieldDamageReduce;
    }


    public static boolean canUseWingShield() {
        if (!EnergyPanelPatches.PatchEnergyPanelField.canUseWingShield.get(AbstractDungeon.overlayMenu.energyPanel))
            return false;

        if (AbstractDungeon.player != null) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r.relicId.equals(ShieldHRzy1.ID) || r.relicId.equals(ShieldHRzy2.ID))
                    return true;
            }
        }

        return false;
    }
}
