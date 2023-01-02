package VUPShionMod.character;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.Codex.*;
import VUPShionMod.cards.EisluRen.ShieldCharge;
import VUPShionMod.cards.EisluRen.WindArrow;
import VUPShionMod.cards.Liyezhu.SoothingScripture;
import VUPShionMod.modules.EnergyOrbWangChuan;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.patches.FontHelperPatches;
import VUPShionMod.powers.Shion.DelayAvatarPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.stances.JudgeStance;
import VUPShionMod.stances.PrayerStance;
import VUPShionMod.stances.*;
import VUPShionMod.stances.SpiritStance;
import VUPShionMod.vfx.victory.LiyezhuVictoryEffect;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

import static VUPShionMod.VUPShionMod.EisluRen_Color;

public class EisluRen extends CustomPlayer {
    public static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID(EisluRen.class.getSimpleName()));
    public static final int ENERGY_PER_TURN = 3;
    public static final int START_HP = 30;
    public static final int START_GOLD = 1;

    public static final String[] orbTextures = {
            "VUPShionMod/img/ui/topPanel/Shion/layer1.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer2.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer3.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer4.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer5.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer6.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer7.png",
            "VUPShionMod/img/ui/topPanel/Shion/levelBack.png",
            "VUPShionMod/img/ui/topPanel/Shion/levelBack.png",
            "VUPShionMod/img/ui/topPanel/Shion/level1.png",
            "VUPShionMod/img/ui/topPanel/Shion/level2.png",
            "VUPShionMod/img/ui/topPanel/Shion/level3.png",
            "VUPShionMod/img/ui/topPanel/Shion/level4.png",
            "VUPShionMod/img/ui/topPanel/Shion/level5.png",
            "VUPShionMod/img/ui/topPanel/Shion/levelMax.png"
    };

    private static String currentIdle = "Idle";
    public float stanceSwitchAnimTimer = 0.0F;
    private final ArrayList<String> stanceSwitchQueue = new ArrayList<>();


    public EisluRen(String name, PlayerClass setClass) {
        super(name, setClass, new EnergyOrbWangChuan(orbTextures, "VUPShionMod/img/ui/topPanel/Shion/energyVFX.png"), null, null);
        this.drawX += 5.0F * Settings.scale;
        this.drawY += 0.0F * Settings.scale;

        this.dialogX = this.drawX + 20.0F * Settings.scale;
        this.dialogY = this.drawY + 270.0F * Settings.scale;

        initializeClass(null,
                SkinManager.getSkin(3).SHOULDER1,
                SkinManager.getSkin(3).SHOULDER2,
                SkinManager.getSkin(3).CORPSE,
                getLoadout(), 0.0F, -5.0F, 260.0F, 380.0F, new EnergyManager(ENERGY_PER_TURN));

        reloadAnimation();


        CharacterSelectScreenPatches.AddFields.characterPriority.get(this).setCharacterPriority(4);
    }

    public void reloadAnimation() {
        this.loadAnimation(
                SkinManager.getSkin(3).atlasURL,
                SkinManager.getSkin(3).jsonURL,
                SkinManager.getSkin(3).renderScale);


        if (SkinManager.getSkinCharacter(3).reskinCount == 0) {
            this.state.setAnimation(0, "idle", true);
        }

    }


    public String getPortraitImageName() {
        return null;
    }

    public ArrayList<String> getStartingRelics() {
        SkinManager.getSkinCharacter(3).InitializeReskinCount();
        return SkinManager.getSkin(3).getStartingRelic();
    }

    public ArrayList<String> getStartingDeck() {
        SkinManager.getSkinCharacter(3).InitializeReskinCount();
        return SkinManager.getSkin(3).getStartingDeck();
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                getLocalizedCharacterName(),
                charStrings.TEXT[0],
                START_HP,
                START_HP,
                0,
                START_GOLD,
                5,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false);
    }


    @Override
    public String getTitle(PlayerClass playerClass) {
        return SkinManager.getSkin(3).getCharacterTiTleName();
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        ArrayList<AbstractCard> pool = super.getCardPool(tmpPool);
        if (ModHelper.isModEnabled("Red Cards")) {
            CardLibrary.addRedCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Green Cards")) {
            CardLibrary.addGreenCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Blue Cards")) {
            CardLibrary.addBlueCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Purple Cards")) {
            CardLibrary.addPurpleCards(tmpPool);
        }


        tmpPool.add(new TerraRapida());
        tmpPool.add(new TerraNimia());
        tmpPool.add(new AquaConstans());
        tmpPool.add(new AquaRapida());
        tmpPool.add(new VentusRapidus());
        tmpPool.add(new VentusNimius());
        tmpPool.add(new LignumNimium());
        tmpPool.add(new LignumConstans());


        return pool;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.EisluRen_LIME;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new WindArrow();
    }

    @Override
    public Color getCardTrailColor() {
        return EisluRen_Color.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return START_HP / 10;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelperPatches.energyNumFontShion;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

//        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_MAGIC_BEAM_SHORT";
    }

    @Override
    public String getLocalizedCharacterName() {
        return charStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new EisluRen(this.name, AbstractPlayerEnum.EisluRen);
    }

    @Override
    public String getSpireHeartText() {
        return CardCrawlGame.languagePack.getEventString(VUPShionMod.makeID("SpireHeart_EisluRen")).DESCRIPTIONS[0];
    }


    @Override
    public Color getSlashAttackColor() {
        return Color.GOLD;
    }


    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public Color getCardRenderColor() {
        return Settings.GOLD_COLOR;
    }


    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        };
    }


    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            this.state.setAnimation(0, "hurt", false).setTimeScale(2.0f);
            this.state.addAnimation(0, "idle", true, 0.0f);
        }

        super.damage(info);
    }

    @Override
    public void update() {
        super.update();
        tickStanceVisualTimer();
    }

    @Override
    public void onStanceChange(String id) {
        if (id.equals(SpiralBladeStance.STANCE_ID)) {
            stanceSwitchQueue.add("SpiralBladeStance");
            return;
        }

        if (id.equals(ThousandsOfBladeStance.STANCE_ID)) {
            stanceSwitchQueue.add("ThousandsOfBladeStance");
            return;
        }

        if (id.equals(LotusOfWarStance.STANCE_ID)) {
            stanceSwitchQueue.add("LotusOfWarStance");
            return;
        }

        if (id.equals(RuinGuardianStance.STANCE_ID)) {
            stanceSwitchQueue.add("RuinGuardianStance");
            return;
        }

        if (id.equals(LightArmorStance.STANCE_ID)) {
            stanceSwitchQueue.add("LightArmorStance");
            return;
        }

        stanceSwitchQueue.add("Idle");

    }


    private void tickStanceVisualTimer() {
        if (stanceSwitchQueue.size() > 0) {
            stanceSwitchAnimTimer = stanceSwitchAnimTimer - Gdx.graphics.getDeltaTime();
            if (stanceSwitchAnimTimer <= 0F) {
                switchStanceVisualGo(stanceSwitchQueue.get(0));
                stanceSwitchQueue.remove(0);
                if (stanceSwitchQueue.size() > 0) {
                    stanceSwitchAnimTimer = 0.6F;
                }
            }
        }
    }

    public void switchStanceVisualGo(String ID) {
        if (currentIdle.equals(ID)) return;
        closeWingAnimation(currentIdle);
        openWingAnimation(ID);
    }

    public void closeWingAnimation(String ID) {
        switch (ID) {
            case "Idle":
                this.state.setAnimation(1, "wings_normal_relieves", false).setTimeScale(2.0f);
                break;
            case "SpiralBladeStance":
                this.state.setAnimation(1, "wings_Spiral_knife_relieves", false).setTimeScale(2.0f);
                break;
            case "ThousandsOfBladeStance":
                this.state.setAnimation(1, "wings_Thousand_heavy_blade_relieves", false).setTimeScale(2.0f);
                break;
            case "LotusOfWarStance":
                this.state.setAnimation(1, "wings_Lotus_of_war_relieves", false).setTimeScale(2.0f);
                break;
            case "RuinGuardianStance":
                this.state.setAnimation(1, "wings_Ruins_guard_relieves", false).setTimeScale(2.0f);
                break;
            case "LightArmorStance":
                this.state.setAnimation(1, "wings_Light_armor_relieves", false).setTimeScale(2.0f);
                break;

        }
    }


    public void openWingAnimation(String ID) {
        switch (ID) {
            case "Idle":
                this.state.addAnimation(1, "wings_normal_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_normal_idle", true, 0.0f);
                currentIdle = "Idle";
                break;
            case "SpiralBladeStance":
                this.state.addAnimation(1, "wings_Spiral_knife_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Spiral_knife_idle", true, 0.0f);
                currentIdle = "SpiralBladeStance";
                break;
            case "ThousandsOfBladeStance":
                this.state.addAnimation(1, "wings_Thousand_heavy_blade_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Thousand_heavy_blade_idle", true, 0.0f);
                currentIdle = "ThousandsOfBladeStance";
                break;
            case "LotusOfWarStance":
                this.state.addAnimation(1, "wings_Lotus_of_war_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Lotus_of_war_idle", true, 0.0f);
                currentIdle = "LotusOfWarStance";
                break;
            case "RuinGuardianStance":
                this.state.addAnimation(1, "wings_Ruins_guard_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Ruins_guard_idle", true, 0.0f);
                currentIdle = "RuinGuardianStance";
                break;
            case "LightArmorStance":
                this.state.addAnimation(1, "wings_Light_armor_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Light_armor_idle", true, 0.0f);
                currentIdle = "LightArmorStance";
                break;

        }
    }

    public static boolean isInPrayer() {
        return AbstractDungeon.player.stance.ID.equals(PrayerStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(SpiritStance.STANCE_ID);
    }

    public static boolean isInJudge() {
        return AbstractDungeon.player.stance.ID.equals(JudgeStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(SpiritStance.STANCE_ID);
    }


    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/EisluRenCutScene.sff"));
        return panels;
    }

    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        boolean foundEyeVfx = false;
        for (AbstractGameEffect e : effects) {
            if (e instanceof LiyezhuVictoryEffect) {
                foundEyeVfx = true;
                break;
            }
        }

        if (!foundEyeVfx)
            effects.add(new LiyezhuVictoryEffect());
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        switchStanceVisualGo("Idle");
    }


    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
        this.state.setAnimation(1, "wings_main_in", false);
        openWingAnimation("Idle");
    }

    @Override
    public void onVictory() {
        super.onVictory();

        if (this.stance.ID.equals(SpiralBladeStance.STANCE_ID)) {
            closeWingAnimation("SpiralBladeStance");
        }

        if (this.stance.ID.equals(ThousandsOfBladeStance.STANCE_ID)) {
            closeWingAnimation("ThousandsOfBladeStance");
        }

        if (this.stance.ID.equals(LotusOfWarStance.STANCE_ID)) {
            closeWingAnimation("LotusOfWarStance");
        }

        if (this.stance.ID.equals(RuinGuardianStance.STANCE_ID)) {
            closeWingAnimation("RuinGuardianStance");
        }

        if (this.stance.ID.equals(LightArmorStance.STANCE_ID)) {
            closeWingAnimation("LightArmorStance");
        }

        if (this.stance.ID.equals(NeutralStance.STANCE_ID)) {
            closeWingAnimation("Idle");
        }

        this.state.addAnimation(1, "wings_main_out", false, 0.0f);

        currentIdle = "Idle";

        if (!this.stance.ID.equals("Neutral")) {
            this.stance = new NeutralStance();
        }

    }
}

