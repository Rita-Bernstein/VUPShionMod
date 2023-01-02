package VUPShionMod.ui;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainMaxHPAction;
import VUPShionMod.actions.Common.LoseMaxHPAction;
import VUPShionMod.events.MentalBreakdown;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.relics.Event.AbyssalCrux;
import VUPShionMod.util.SaveHelper;
import basemod.CustomEventRoom;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class SansMeter {
    private final Texture bg;
    private final Texture meter;
    private final Texture light;

    public int amount = 100;
    public int amount_MAX = 100;
    private final Hitbox hb;

    private float cX = 0.0f;
    private float cY = 0.0f;
    public float current_x = 0.0f;
    public float current_y = 0.0f;
    private final float scale = 0.23f;
    private float lightTimer = 0.0f;

    private final Color color = Color.WHITE.cpy();

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("SansMeter"));
    public static final String[] TEXT = uiStrings.TEXT;
    public String name = TEXT[0];
    public String description = TEXT[1];

    public SansMeter() {
        this.bg = ImageMaster.loadImage("VUPShionMod/img/ui/SanMeter/BG.png");
        this.meter = ImageMaster.loadImage("VUPShionMod/img/ui/SanMeter/Meter.png");
        this.light = ImageMaster.loadImage("VUPShionMod/img/ui/SanMeter/MeterTop.png");

        this.hb = new Hitbox(792.0f * this.scale * Settings.scale, 1158.0f * this.scale * Settings.scale);

        this.amount = SansMeterSave.sansMeterSaveAmount;
        updateDescription();
    }

    public void updatePos(EnergyPanel _instance) {
        this.cX = _instance.current_x;
        this.cY = _instance.current_y + 100.0f * Settings.scale;
        this.hb.move(this.cX, this.cY + 579.0f * this.scale * Settings.scale);
    }

    public void updateDescription() {
        this.description = TEXT[1] + TEXT[2] + TEXT[3] + TEXT[4];
    }

    public void update() {
        this.hb.update();
    }


    public void render(SpriteBatch sb) {
        sb.setColor(this.color);

        sb.draw(this.bg,
                this.cX + this.current_x - 396.0f,
                this.cY + this.current_y,
                396.0F, 0.0f,
                792.0F, 1158.0F,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                792, 1158,
                false, false);

        sb.draw(this.meter,
                this.cX + this.current_x - 396.0f,
                this.cY + this.current_y - 0.0f + 89.0f * Settings.scale * this.scale,
                396.0F, 0.0f,
                792.0F, 914 * this.amount * 0.01f,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, (int) Math.floor((914 * (100 - this.amount)) * 0.01f),
                792, (int) Math.floor((914 * this.amount) * 0.01f),
                false, false);


        this.lightTimer += Gdx.graphics.getDeltaTime() * 4.0f;
        this.color.a = ((float) Math.cos(this.lightTimer) + 1.0f) * 0.2f + 0.6f;
        sb.setColor(this.color);

        sb.draw(this.light,
                this.cX + this.current_x - 396.0f,
                this.cY + this.current_y - 64.0f + (89.0f + 914.0f * this.amount * 0.01f) * Settings.scale * this.scale,
                396.0F, 64.0f,
                792.0F, 128.0f,
                this.amount < 30 ? this.scale * Settings.scale * (this.amount * 0.03f + 0.1f) : this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                792, 128,
                false, false);


        this.color.a = 1.0f;
        sb.setColor(this.color);

        String energyMsg = this.amount + "";
        AbstractDungeon.player.getEnergyNumFont().getData().setScale(8.0f * this.scale);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), energyMsg,
                this.cX + this.current_x,
                this.cY + this.current_y + 670.0f * Settings.scale * this.scale,
                this.color);

        this.hb.render(sb);

        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(this.hb.cX + this.hb.width * 0.5f,
                    0.75f * Settings.HEIGHT, name, description);
        }

    }


    public void onFatal(AbstractMonster m) {
        if ((m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower(MinionPower.POWER_ID)) {
            if (this.amount <= 30)
                addToBot(new LoseMaxHPAction(AbstractDungeon.player, AbstractDungeon.player, 4));
            else if (this.amount <= 50)
                addToBot(new LoseMaxHPAction(AbstractDungeon.player, AbstractDungeon.player, 1));

            loseSan(3);

        }
    }

    public int changeSinApply(AbstractPower power) {
        if (power.owner.isPlayer)
            if (this.amount <= 30) {
                return power.amount + 1;
            }
        return power.amount;
    }


    public void loseSan(int amount) {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof AbstractShionRelic)
                amount = ((AbstractShionRelic) relic).onLoseSan(amount);
        }

        if (amount <= 0) return;

        this.amount -= amount;
        if (this.amount < 0)
            this.amount = 0;

        if (SaveHelper.isTrainingMod && this.amount <= 0)
            this.amount = 1;

        SansMeterSave.sansMeterSaveAmount = this.amount;
    }

    public void addSan(int amount) {
        if (AbstractDungeon.player.hasRelic(AbyssalCrux.ID)) return;
        this.amount += amount;
        if (this.amount > this.amount_MAX)
            this.amount = this.amount_MAX;

        SansMeterSave.sansMeterSaveAmount = this.amount;
    }

    public void atStartOfTurn() {
    }

    public void atStartOfCombat() {
        this.amount = SansMeterSave.sansMeterSaveAmount;

        if (this.amount >= this.amount_MAX)
            addToBot(new GainMaxHPAction(AbstractDungeon.player, 10));

        if (this.amount < this.amount_MAX && this.amount > 50)
            addToBot(new GainMaxHPAction(AbstractDungeon.player, 1));

        if (this.amount <= 50)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, 3, false)));

        if (this.amount <= 30)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 3, false)));
    }

    public void atEndOfTurn() {

    }

    public void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }


    public static SansMeter getSans() {
        return EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel);
    }

    public void onVictory() {
        if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))
            AbstractDungeon.effectsQueue.add(new AbstractGameEffect() {
                @Override
                public void update() {
                    if (!SansMental.sansMental && SansMeter.this.amount <= 0) {
                        if ((AbstractDungeon.getCurrRoom()) != null) {

                            (AbstractDungeon.getCurrRoom()).isBattleOver = false;

                            if ((AbstractDungeon.getCurrRoom()).monsters != null) {
                                if ((AbstractDungeon.getCurrRoom()).monsters.monsters != null) {
                                    (AbstractDungeon.getCurrRoom()).monsters.monsters.clear();
                                }
                            }

                            if ((AbstractDungeon.getCurrRoom()).rewards != null)
                                (AbstractDungeon.getCurrRoom()).rewards.clear();
                        }

                        RoomEventDialog.optionList.clear();

                        AbstractDungeon.eventList.add(0, MentalBreakdown.ID);

                        MapRoomNode cur = AbstractDungeon.currMapNode;
                        MapRoomNode node = new MapRoomNode(cur.x, cur.y);
                        node.room = new CustomEventRoom();

                        ArrayList<MapEdge> curEdges = cur.getEdges();
                        for (MapEdge edge : curEdges) {
                            node.addEdge(edge);
                        }


                        AbstractDungeon.player.releaseCard();
                        AbstractDungeon.overlayMenu.hideCombatPanels();
                        AbstractDungeon.previousScreen = null;
                        AbstractDungeon.dynamicBanner.hide();
                        AbstractDungeon.dungeonMapScreen.closeInstantly();
                        AbstractDungeon.closeCurrentScreen();
                        AbstractDungeon.topPanel.unhoverHitboxes();
                        AbstractDungeon.fadeIn();
                        AbstractDungeon.dungeonMapScreen.dismissable = true;
                        AbstractDungeon.nextRoom = node;
                        AbstractDungeon.setCurrMapNode(node);
                        AbstractDungeon.getCurrRoom().onPlayerEntry();
                        AbstractDungeon.scene.nextRoom(node.room);
                        AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;

                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
                        GenericEventDialog.show();
                        CardCrawlGame.fadeIn(1.5F);
                    }

                    isDone = true;
                }

                @Override
                public void render(SpriteBatch spriteBatch) {

                }

                @Override
                public void dispose() {

                }
            });

    }

}
