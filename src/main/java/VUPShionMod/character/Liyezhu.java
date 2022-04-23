package VUPShionMod.character;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.Liyezhu.*;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.modules.EnergyOrbWangChuan;
import VUPShionMod.patches.*;
import VUPShionMod.stances.JudgeStance;
import VUPShionMod.stances.PrayerStance;
import VUPShionMod.stances.SpiritStance;
import VUPShionMod.vfx.LiyezhuVictoryEffect;
import VUPShionMod.vfx.ShionVictoryEffect;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

import static VUPShionMod.VUPShionMod.Liyezhu_Color;
import static VUPShionMod.VUPShionMod.WangChuan_Color;

public class Liyezhu extends CustomPlayer {
    public static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID("Liyezhu"));
    public static final int ENERGY_PER_TURN = 3;
    public static final int START_HP = 100;
    public static final int START_GOLD = 0;

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
    private ArrayList<String> stanceSwitchQueue = new ArrayList<>();

    public Liyezhu(String name, PlayerClass setClass) {
        super(name, setClass, new EnergyOrbWangChuan(orbTextures, "VUPShionMod/img/ui/topPanel/Shion/energyVFX.png"), (String) null, null);
        this.drawX += 5.0F * Settings.scale;
        this.drawY += 7.0F * Settings.scale;

        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;

        initializeClass(null,
                CharacterSelectScreenPatches.characters[2].skins[CharacterSelectScreenPatches.characters[2].reskinCount].SHOULDER1,
                CharacterSelectScreenPatches.characters[2].skins[CharacterSelectScreenPatches.characters[2].reskinCount].SHOULDER2,
                CharacterSelectScreenPatches.characters[2].skins[CharacterSelectScreenPatches.characters[2].reskinCount].CORPSE,
                getLoadout(), 0.0F, -5.0F, 260.0F, 380.0F, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(VUPShionMod.assetPath("characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.atlas"),
                VUPShionMod.assetPath("characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.json"), 3.0f);

        reloadAnimation();
    }

    public void reloadAnimation() {
        this.loadAnimation(
                CharacterSelectScreenPatches.characters[2].skins[CharacterSelectScreenPatches.characters[2].reskinCount].atlasURL,
                CharacterSelectScreenPatches.characters[2].skins[CharacterSelectScreenPatches.characters[2].reskinCount].jsonURL,
                CharacterSelectScreenPatches.characters[2].skins[CharacterSelectScreenPatches.characters[2].reskinCount].renderscale);


        if (CharacterSelectScreenPatches.characters[1].reskinCount == 0) {
            this.state.setAnimation(0, "idle_normal", true);
            this.state.setAnimation(1, "idle_wings", true);
            this.state.setAnimation(2, "idle_xiaobingpian", true);
            this.state.setAnimation(3, "change_xiaobingpian_off", false);
        }

    }


    public String getPortraitImageName() {
        return null;
    }

    public ArrayList<String> getStartingRelics() {
        CharacterSelectScreenPatches.characters[2].InitializeReskinCount();
        return CharacterSelectScreenPatches.characters[2].skins[CharacterSelectScreenPatches.characters[2].reskinCount].getStartingRelic();
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(HolyLight.ID);
        retVal.add(HolyLight.ID);
        retVal.add(HolyLight.ID);
        retVal.add(HolyLight.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(SoothingScripture.ID);
        retVal.add(TranquilPrayer.ID);
        retVal.add(JudgementOfSins.ID);


        return retVal;
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
        return charStrings.NAMES[1];
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {

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

        return super.getCardPool(tmpPool);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.Liyezhu_LIME;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Sheathe();
    }

    @Override
    public Color getCardTrailColor() {
        return Liyezhu_Color.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 10;
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
        return new Liyezhu(this.name, AbstractPlayerEnum.Liyezhu);
    }

    @Override
    public String getSpireHeartText() {
        return CardCrawlGame.languagePack.getEventString(VUPShionMod.makeID("SpireHeart_Liyezhu")).DESCRIPTIONS[0];
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
            this.state.setAnimation(0, "hurt", false).setTimeScale(3.0f);
            this.state.addAnimation(0, "idle_normal", true, 0.0f);
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
        System.out.println("改变姿态"+ id);
        switch (id) {
            case PrayerStance.STANCE_ID:
                stanceSwitchQueue.add("Prayer");
                break;

            case JudgeStance.STANCE_ID:
                stanceSwitchQueue.add("Judge");
                break;

            case SpiritStance.STANCE_ID:
                stanceSwitchQueue.add("Spirit");
                break;
            default:
                stanceSwitchQueue.add("Idle");
        }
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
        System.out.println("改变姿态动画===当前id--"+ currentIdle);
        System.out.println("改变姿态动画===目标id--"+ ID);

        if(currentIdle.equals("Idle")){
            switch (ID) {
                case "Prayer": {
                    this.state.setAnimation(5,"change_shengguan_on",false);
                    this.state.addAnimation(5,"idle_shengguan",true,0.0f);
                    currentIdle = "Prayer";
                    break;
                }
                case "Judge": {
                    this.state.setAnimation(4,"change_bajian",false);
                    this.state.setAnimation(3,"change_xiaobingpian_on",false);
                    currentIdle = "Judge";
                    break;
                }
                case "Spirit": {
                    this.state.setAnimation(5,"change_shengguan_blue_on",false);
                    this.state.addAnimation(5,"idle_shengguan_blue",true,0.0f);
                    this.state.setAnimation(4,"change_bajian",false);
                    this.state.setAnimation(3,"change_xiaobingpian_on",false);
                    currentIdle = "Spirit";
                    break;
                }
            }

            return;
        }


        if(currentIdle.equals("Prayer")){
            switch (ID) {
                case "Judge": {
                    this.state.setAnimation(5,"change_shengguan_off",false);
                    this.state.setAnimation(4,"change_bajian",false);
                    this.state.setAnimation(3,"change_xiaobingpian_on",false);
                    currentIdle = "Judge";
                    break;
                }
                case "Spirit": {
                    this.state.setAnimation(5,"change_shengguan_WCB",false);
                    this.state.addAnimation(5,"idle_shengguan_blue",true,0.0f);
                    this.state.setAnimation(4,"change_bajian",false);
                    this.state.setAnimation(3,"change_xiaobingpian_on",false);
                    currentIdle = "Spirit";
                    break;
                }
                default:
                    this.state.setAnimation(5,"change_shengguan_off",false);
                    currentIdle = "Idle";
                    break;
            }

            return;
        }


        if(currentIdle.equals("Judge")){
            switch (ID) {
                case "Prayer": {
                    this.state.setAnimation(5,"change_shengguan_on",false);
                    this.state.addAnimation(5,"idle_shengguan",true,0.0f);
                    this.state.setAnimation(4,"change_qidao",false);
                    this.state.setAnimation(3,"change_xiaobingpian_off",false);
                    currentIdle = "Prayer";
                    break;
                }
                case "Spirit": {
                    this.state.setAnimation(5,"change_shengguan_blue_on",false);
                    this.state.addAnimation(5,"idle_shengguan_blue",true,0.0f);
                    currentIdle = "Spirit";
                    break;
                }
                default:
                    this.state.setAnimation(4,"change_qidao",false);
                    this.state.setAnimation(3,"change_xiaobingpian_off",false);
                    currentIdle = "Idle";
                    break;
            }
            return;
        }

        if(currentIdle.equals("Spirit")){
            switch (ID) {
                case "Prayer": {
                    this.state.setAnimation(5,"change_shengguan_BCW",false);
                    this.state.setAnimation(4,"change_qidao",false);
                    this.state.setAnimation(3,"change_xiaobingpian_off",false);
                    currentIdle = "Prayer";
                    break;
                }
                case "Judge": {
                    this.state.setAnimation(5,"change_shengguan_blue_off",false);
                    currentIdle = "Judge";
                    break;
                }
                default:
                    this.state.setAnimation(5,"change_shengguan_blue_off",false);
                    this.state.setAnimation(4,"change_qidao",false);
                    this.state.setAnimation(3,"change_xiaobingpian_off",false);
                    currentIdle = "Idle";
                    break;
            }

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
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/WangChuanCutScene.png"));
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
}

