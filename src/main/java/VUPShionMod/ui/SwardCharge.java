package VUPShionMod.ui;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyCorGladiiAction;
import VUPShionMod.actions.Wangchuan.SwardChargeMaxAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;


public class SwardCharge implements Disposable {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(SwardCharge.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;
    public String name = TEXT[0];
    public String description = TEXT[1];


    private int count = 0;
    private int upgradeCount = 0;
    private int magiamCount = 0;


    private Color color = Color.WHITE.cpy();

    public float cX = 0.0f;
    public float cY = 0.0f;
    public float scale = 0.55f;

    private Hitbox hb;

    private Texture body;
    private ArrayList<SwardChargeItem> comboImg = new ArrayList<>();
    private ArrayList<SwardChargeItem> upgradeImg = new ArrayList<>();
    public boolean comboTrigger = false;
    public boolean upgradeShine = false;


    private ArrayList<String> cardsPlayed = new ArrayList<>();

    public SwardCharge() {
        this.body = ImageMaster.loadImage("VUPShionMod/img/ui/SwardCharge/Body.png");

        if (comboImg.isEmpty()) {
            this.comboImg.add(new SwardChargeItem("VUPShionMod/img/ui/SwardCharge/Combo1.png", this.scale));
            this.comboImg.add(new SwardChargeItem("VUPShionMod/img/ui/SwardCharge/Combo2.png", this.scale));
            this.comboImg.add(new SwardChargeItem("VUPShionMod/img/ui/SwardCharge/Combo3.png", this.scale));
            this.comboImg.add(new SwardChargeItem("VUPShionMod/img/ui/SwardCharge/Combo4.png", this.scale));
        }

        if (upgradeImg.isEmpty()) {
            this.upgradeImg.add(new SwardChargeItem("VUPShionMod/img/ui/SwardCharge/Upgrade1.png", this.scale));
            this.upgradeImg.add(new SwardChargeItem("VUPShionMod/img/ui/SwardCharge/Upgrade2.png", this.scale));
            this.upgradeImg.add(new SwardChargeItem("VUPShionMod/img/ui/SwardCharge/Upgrade3.png", this.scale));
        }


        this.hb = new Hitbox(380.0f * Settings.scale * this.scale, 380.0f * Settings.scale * this.scale);

        updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(TEXT[1] + TEXT[2], this.count, this.upgradeCount, this.magiamCount);
    }

    public void updatePos(EnergyPanel _instance) {
        this.cX = _instance.current_x - 100.0f * Settings.scale;
        this.cY = _instance.current_y + 100.0f * Settings.scale;
        this.hb.move(this.cX + 150.0f * this.scale * Settings.scale, this.cY + 150.0f * this.scale * Settings.scale);

        for (SwardChargeItem item : this.upgradeImg) {
            item.updatePos(this.cX, this.cY);
        }

        for (SwardChargeItem item : this.comboImg) {
            item.updatePos(this.cX, this.cY);
        }
    }

    public void update() {
        this.upgradeShine = true;
        if (!this.comboImg.isEmpty()) {
            for (SwardChargeItem item : this.upgradeImg) {
                if (item.lightTimer < 1.0f)
                    this.upgradeShine = false;
            }

            for (SwardChargeItem item : this.upgradeImg) {
                if (this.upgradeShine)
                    item.shine();
            }
        }

        this.hb.update();
    }

    public void render(SpriteBatch sb) {

        sb.setColor(this.color);

        if (this.body != null) {
            sb.draw(this.body, this.cX, this.cY,
                    0.0f, 0.0f, this.body.getWidth(), this.body.getHeight(),
                    this.scale * Settings.scale, this.scale * Settings.scale,
                    0.0f,
                    0, 0, this.body.getWidth(), this.body.getHeight(), false, false);
        }

        if (!this.upgradeImg.isEmpty()) {
            for (SwardChargeItem item : this.upgradeImg) {
                item.render(sb);
            }
        }


        if (!this.comboImg.isEmpty()) {
            for (SwardChargeItem item : this.comboImg) {
                item.render(sb);
            }
        }


        sb.setColor(this.color);

        this.hb.render(sb);

        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(this.hb.cX + this.hb.width * 0.5f,
                    0.75f * Settings.HEIGHT, name, description);
        }

    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CardTagsEnum.NoSwardCharge) || !EnergyPanelPatches.PatchEnergyPanelField.canUseSwardCharge.get(AbstractDungeon.overlayMenu.energyPanel)) {
            return;
        }

        if(this.comboTrigger)return;

        if (card.type == AbstractCard.CardType.ATTACK) {
            if (!this.cardsPlayed.contains(card.cardID)) {
                this.cardsPlayed.add(card.cardID);
                this.count = this.cardsPlayed.size();

                for (int i = 0; i < this.count; i++) {
                    this.comboImg.get(i).show();
                }

            }
        }

        if(this.count ==3){
            addToBot(new ApplyCorGladiiAction(1));
        }

        if (this.count >= 4 && !this.comboTrigger) {
            this.comboTrigger = true;
            addToBot(new SwardChargeMaxAction(this.upgradeCount));
        }

        updateDescription();

    }


    public void onApplyMagiamObruor(int amount) {
        if (!EnergyPanelPatches.PatchEnergyPanelField.canUseSwardCharge.get(AbstractDungeon.overlayMenu.energyPanel))
            return;


        magiamCount += amount;
        this.upgradeCount += magiamCount / 3;
        this.magiamCount %= 3;
        if (this.upgradeCount > 3) {
            this.upgradeCount = 3;
        }


        for (int i = 0; i < this.upgradeCount; i++)
            this.upgradeImg.get(i).show();


        updateDescription();
    }

    public static SwardCharge getSwardCharge() {
        return EnergyPanelPatches.PatchEnergyPanelField.swardCharge.get(AbstractDungeon.overlayMenu.energyPanel);
    }

    public void resetCount() {
        this.count = 0;
        this.cardsPlayed.clear();
        this.comboTrigger = false;
        updateDescription();

        for (SwardChargeItem item : this.comboImg) {
            item.hide();
        }
    }

    public void resetUpgrade() {
        this.upgradeCount = 0;
        this.magiamCount = 0;
        updateDescription();

        this.upgradeShine = false;
        for (SwardChargeItem item : this.upgradeImg) {
            item.hide();
        }
    }


    @Override
    public void dispose() {
        if (this.body != null) {
            this.body.dispose();
            this.body = null;
        }

        if (this.upgradeImg != null && !this.upgradeImg.isEmpty()) {
            for (SwardChargeItem item : this.upgradeImg) {
                item.dispose();
            }
        }


        if (this.comboImg != null && !this.comboImg.isEmpty()) {
            for (SwardChargeItem item : this.comboImg) {
                item.dispose();
            }
        }
    }

    public void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }


}
