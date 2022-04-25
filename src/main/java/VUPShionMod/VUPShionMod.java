package VUPShionMod;

import VUPShionMod.cards.Liyezhu.*;
import VUPShionMod.cards.ShionCard.*;
import VUPShionMod.cards.ShionCard.anastasia.*;
import VUPShionMod.cards.ShionCard.kuroisu.*;
import VUPShionMod.cards.ShionCard.liyezhu.*;
import VUPShionMod.cards.ShionCard.minami.*;
import VUPShionMod.cards.ShionCard.optionCards.*;
import VUPShionMod.cards.ShionCard.shion.*;
import VUPShionMod.cards.ShionCard.tempCards.*;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.cards.Codex.*;
import VUPShionMod.character.Liyezhu;
import VUPShionMod.character.Shion;
import VUPShionMod.character.WangChuan;
import VUPShionMod.events.*;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.helpers.SecondaryMagicVariable;
import VUPShionMod.monsters.PlagaAMundo;
import VUPShionMod.patches.*;
import VUPShionMod.powers.LoseFinFunnelUpgradePower;
import VUPShionMod.powers.TempFinFunnelUpgradePower;
import VUPShionMod.relics.*;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.util.SansMeterSave;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomCard;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static VUPShionMod.patches.CharacterSelectScreenPatches.characters;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpireInitializer
public class VUPShionMod implements
        PostInitializeSubscriber,
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        AddAudioSubscriber,
        EditKeywordsSubscriber,
        EditStringsSubscriber,
        PostDungeonInitializeSubscriber,
        StartActSubscriber {

    public static final String MODNAME = "VUPShionMod";
    public static final String AUTHOR = "Rita";
    public static final String DESCRIPTION = "";
    public static final Color Shion_Color = new Color(0.418F, 0.230F, 0.566F, 1.0F);
    public static final Color WangChuan_Color = new Color(0.203F, 0.176F, 0.168F, 1.0F);
    public static final Color Liyezhu_Color = new Color(0.250F, 0.286F, 0.541F, 1.0F);
    public static final Logger logger = LogManager.getLogger(VUPShionMod.class.getSimpleName());
    public static String MOD_ID = "VUPShionMod";
    public static Properties VUPShionDefaults = new Properties();
    public static ArrayList<AbstractGameEffect> effectsQueue = new ArrayList<>();
//    public static AbstractFinFunnel.FinFunnelSaver finFunnelSaver;

    public static int gravityFinFunnelLevel = 1;
    public static int investigationFinFunnelLevel = 1;
    public static int pursuitFinFunnelLevel = 1;
    public static int activeFinFunnel = 1;

    public static List<CustomCard> an_Cards = new ArrayList<>();
    public static List<CustomCard> ku_Cards = new ArrayList<>();
    public static List<CustomCard> li_Cards = new ArrayList<>();
    public static List<CustomCard> mi_Cards = new ArrayList<>();
    public static List<CustomCard> shi_Cards = new ArrayList<>();
    public static List<CustomCard> chuan_Cards = new ArrayList<>();
    public static List<CustomCard> codex_Cards = new ArrayList<>();

    public static boolean useSimpleOrb = false;
    public static boolean notReplaceTitle = false;
    public static boolean safeCampfire = false;

    public static ModLabeledToggleButton useSimpleOrbSwitch;
    public static ModLabeledToggleButton notReplaceTitleSwitch;
    public static ModLabeledToggleButton safeCampfireSwitch;

    public static Color transparent = Color.WHITE.cpy();
    public static boolean fightSpecialBoss = false;
    public static boolean fightSpecialBossWithout = false;


    public VUPShionMod() {
        BaseMod.subscribe(this);
        transparent.a = 0.0f;
        BaseMod.addColor(CardColorEnum.VUP_Shion_LIME,
                Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color,
                assetPath("img/cardui/Shion/512/bg_attack_lime.png"),
                assetPath("img/cardui/Shion/512/bg_skill_lime.png"),
                assetPath("img/cardui/Shion/512/bg_power_lime.png"),
                assetPath("img/cardui/Shion/512/card_lime_orb_w.png"),
                assetPath("img/cardui/Shion/1024/bg_attack_lime.png"),
                assetPath("img/cardui/Shion/1024/bg_skill_lime.png"),
                assetPath("img/cardui/Shion/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/Shion/512/card_lime_small_orb.png"));

        BaseMod.addColor(CardColorEnum.WangChuan_LIME,
                WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color,
                assetPath("img/cardui/WangChuan/512/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_power_lime.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_orb.png"),
                assetPath("img/cardui/WangChuan/1024/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_small_orb.png"));

        BaseMod.addColor(CardColorEnum.Codex_LIME,
                WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color,
                assetPath("img/cardui/WangChuan/512/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_power_lime.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_orb.png"),
                assetPath("img/cardui/WangChuan/1024/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_small_orb.png"));

        BaseMod.addColor(CardColorEnum.Liyezhu_LIME,
                Liyezhu_Color, Liyezhu_Color, Liyezhu_Color, Liyezhu_Color, Liyezhu_Color, Liyezhu_Color, Liyezhu_Color,
                assetPath("img/cardui/WangChuan/512/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_power_lime.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_orb.png"),
                assetPath("img/cardui/WangChuan/1024/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_small_orb.png"));


    }


    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionDefaults);
            config.setBool("useSimpleOrb", useSimpleOrb);
            config.setBool("notReplaceTitle", notReplaceTitle);
            config.setBool("safeCampfire", safeCampfire);


            for (int i = 0; i <= characters.length - 1; i++) {
                config.setBool(CardCrawlGame.saveSlot + "ReskinUnlock" + i, characters[i].reskinUnlock);
                config.setInt(CardCrawlGame.saveSlot + "reskinCount" + i, characters[i].reskinCount);
            }

            System.out.println("==============reskin存入数据");

            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionDefaults);
            config.load();
            useSimpleOrb = config.getBool("useSimpleOrb");
            notReplaceTitle = config.getBool("notReplaceTitle");
            safeCampfire = config.getBool("safeCampfire");

            for (int i = 0; i <= characters.length - 1; i++) {
                characters[i].reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "ReskinUnlock" + i);
                characters[i].reskinCount = config.getInt(CardCrawlGame.saveSlot + "reskinCount" + i);

                if (characters[i].reskinCount > characters[i].skins.length - 1) {
                    characters[i].reskinCount = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            clearSettings();
        }
    }

    public static void clearSettings() {
        saveSettings();
    }

    public static void unlockAllReskin() {
        for (AbstractSkinCharacter c : characters) {
            c.reskinUnlock = false;
        }
        saveSettings();
    }

    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String assetPath(String path) {
        return MOD_ID + "/" + path;
    }

    public static String characterAssetPath(String className, String path) {
        return MOD_ID + "/" + className + "/" + path;
    }

    public static void saveFinFunnels() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "VUPShionMod_settings", VUPShionDefaults);
            config.setInt("gravityFinFunnelLevel", gravityFinFunnelLevel);
            config.setInt("investigationFinFunnelLevel", investigationFinFunnelLevel);
            config.setInt("pursuitFinFunnelLevel", pursuitFinFunnelLevel);
            config.setInt("activeFinFunnel", activeFinFunnel);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFinFunnels() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "VUPShionMod_settings", VUPShionDefaults);
            config.load();
            gravityFinFunnelLevel = config.getInt("gravityFinFunnelLevel");
            investigationFinFunnelLevel = config.getInt("investigationFinFunnelLevel");
            pursuitFinFunnelLevel = config.getInt("pursuitFinFunnelLevel");
            activeFinFunnel = config.getInt("activeFinFunnel");

        } catch (Exception e) {
            e.printStackTrace();
            clearFinFunnels();
        }
    }

    public static void clearFinFunnels() {
        saveFinFunnels();
    }

    public static int calculateTotalFinFunnelLevel() {
        int ret = 0;
        if (AbstractDungeon.player != null) {
            List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player);
            for (AbstractFinFunnel funnel : funnelList) {
                ret += funnel.getLevel();
            }
            AbstractRelic relic = AbstractDungeon.player.getRelic(DimensionSplitterAria.ID);
            if (relic != null) {
                ret += relic.counter;
            }

            AbstractPower power = AbstractDungeon.player.getPower(TempFinFunnelUpgradePower.POWER_ID);
            if (power != null) {
                ret += power.amount;
            }

            if (AbstractDungeon.player.hasPower(LoseFinFunnelUpgradePower.POWER_ID))
                ret = 0;
        }

        return ret;
    }

    public static void initialize() {
        new VUPShionMod();
    }

    @Override
    public void receivePostInitialize() {
        loadSettings();
//        unlockAllReskin();
        Texture badgeTexture = new Texture(assetPath("/img/badge.png"));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        useSimpleOrbSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("ModSettings")).TEXT[0], 400.0f, 720.0f - 0 * 50.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, useSimpleOrb, settingsPanel,
                (label) -> {
                }, (button) -> {
            useSimpleOrb = button.enabled;
            saveSettings();
        });

        notReplaceTitleSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("ModSettings")).TEXT[1], 400.0f, 720.0f - 1 * 50.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, notReplaceTitle, settingsPanel,
                (label) -> {
                }, (button) -> {
            notReplaceTitle = button.enabled;
            saveSettings();
        });

        safeCampfireSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("ModSettings")).TEXT[2], 400.0f, 720.0f - 2 * 50.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, safeCampfire, settingsPanel,
                (label) -> {
                }, (button) -> {
            safeCampfire = button.enabled;
            AbstractScenePatches.campfire_Wc = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/" + (VUPShionMod.safeCampfire ? "Campfire2.png" : "Campfire.png"));
            saveSettings();
        });


        settingsPanel.addUIElement(useSimpleOrbSwitch);
        settingsPanel.addUIElement(notReplaceTitleSwitch);
        settingsPanel.addUIElement(safeCampfireSwitch);

//        finFunnelSaver = new AbstractFinFunnel.FinFunnelSaver();

        ArrayList<AbstractPlayer.PlayerClass> list = new ArrayList<>();
        list.add(AbstractPlayerEnum.VUP_Shion);
        list.add(AbstractPlayerEnum.WangChuan);
        list.add(AbstractPlayerEnum.Liyezhu);

//        mod公共事件
        for(AbstractPlayer.PlayerClass playerClass : list){
            BaseMod.addEvent(new AddEventParams.Builder(CroissantEvent.ID, CroissantEvent.class) //Event ID//
                    //Event Character//
                    .playerClass(playerClass)
                    .spawnCondition(() -> !AbstractDungeon.id.equals(TheEnding.ID))
                    .create());


            BaseMod.addEvent(new AddEventParams.Builder(LostEquipment.ID, LostEquipment.class) //Event ID//
                    //Event Character//
                    .playerClass(playerClass)
                    .spawnCondition(() -> !AbstractDungeon.id.equals(TheEnding.ID))
                    .create());

            BaseMod.addEvent(new AddEventParams.Builder(DaysGoneBy.ID, DaysGoneBy.class) //Event ID//
                    .playerClass(playerClass)
                    .create());
        }


        BaseMod.addEvent(new AddEventParams.Builder(BreakAppointment.ID, BreakAppointment.class) //Event ID//
                //Event Character//
                .playerClass(AbstractPlayerEnum.VUP_Shion)
                .spawnCondition(() -> !AbstractDungeon.id.equals(TheEnding.ID))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Newborn.ID, Newborn.class) //Event ID//
                //Event Character//
                .spawnCondition(() -> AbstractDungeon.id.equals(TheEnding.ID))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Contact.ID, Contact.class) //Event ID//
                //Event Character//
                .spawnCondition(() -> AbstractDungeon.id.equals(TheEnding.ID))
                .create());


        BaseMod.addEvent(new AddEventParams.Builder(LakeAmidst.ID, LakeAmidst.class) //Event ID//
                .playerClass(AbstractPlayerEnum.WangChuan)
                .spawnCondition(() -> AbstractDungeon.id.equals(TheCity.ID))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(BoundaryOfChaos.ID, BoundaryOfChaos.class) //Event ID//
                .playerClass(AbstractPlayerEnum.WangChuan)
                .spawnCondition(() -> AbstractDungeon.id.equals(TheBeyond.ID)
                        && (BoundaryOfChaos.hasCodex() || BoundaryOfChaos.hasCodex1() || BoundaryOfChaos.hasCodex2()))
                .create());


        BaseMod.addMonster(PlagaAMundo.ID, () -> new PlagaAMundo());
    }

    @Override
    public void receiveAddAudio() {
        for (int i = 1; i <= 18; i++) {
            BaseMod.addAudio("SHION_" + i, assetPath("audio/sfx/shion" + i + ".ogg"));
        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        System.out.println("重开游戏");

        if(AbstractDungeon.floorNum == 0){
            SansMeterSave.sansMeterSaveAmount = 100;
        }
//        if (AbstractDungeon.player.hasRelic(DimensionSplitterAria.ID)) {
//            AbstractRelic relic = AbstractDungeon.player.getRelic(DimensionSplitterAria.ID);
//            relic.flash();
//            relic.counter++;
//            ((DimensionSplitterAria) relic).setDescriptionAfterLoading();
//        }

    }

    @Override
    public void receiveStartAct() {
        if (AbstractDungeon.floorNum == 0) {
            try {
                SpireConfig config = new SpireConfig("VUPShionMod", "VUPShionMod_settings", VUPShionDefaults);
                gravityFinFunnelLevel = 1;
                investigationFinFunnelLevel = 1;
                pursuitFinFunnelLevel = 1;
                activeFinFunnel = 1;
                config.setInt("gravityFinFunnelLevel", gravityFinFunnelLevel);
                config.setInt("investigationFinFunnelLevel", investigationFinFunnelLevel);
                config.setInt("pursuitFinFunnelLevel", pursuitFinFunnelLevel);
                config.setInt("activeFinFunnel", activeFinFunnel);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fightSpecialBoss = false;
            fightSpecialBossWithout = false;
        }

        if (AbstractDungeon.player.hasRelic(DimensionSplitterAria.ID)) {
            AbstractRelic relic = AbstractDungeon.player.getRelic(DimensionSplitterAria.ID);
            relic.flash();
            relic.counter++;
            ((DimensionSplitterAria) relic).setDescriptionAfterLoading();
        }
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("========================= 开始加载人物 =========================");

        logger.info(Shion.charStrings.NAMES[1]);
        BaseMod.addCharacter(new Shion(Shion.charStrings.NAMES[1], AbstractPlayerEnum.VUP_Shion),
                assetPath("characters/Shion/Button.png"),
                assetPath("characters/Shion/portrait.png"),
                AbstractPlayerEnum.VUP_Shion);
        BaseMod.addCharacter(new WangChuan(WangChuan.charStrings.NAMES[1], AbstractPlayerEnum.WangChuan),
                assetPath("characters/WangChuan/Button.png"),
                assetPath("characters/WangChuan/portrait.png"),
                AbstractPlayerEnum.WangChuan);

        BaseMod.addCharacter(new Liyezhu(Liyezhu.charStrings.NAMES[1], AbstractPlayerEnum.Liyezhu),
                assetPath("characters/Liyezhu/Button.png"),
                assetPath("characters/Liyezhu/portrait.png"),
                AbstractPlayerEnum.Liyezhu);

        BaseMod.addSaveField("SansMeterSave",new SansMeterSave());
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondaryMagicVariable());
        List<CustomCard> cards = new ArrayList<>();

//        紫音
        cards.add(new Strike_Shion());
        cards.add(new Defend_Shion());
        cards.add(new DefenseSystemCharging());
        cards.add(new DeploymentOfDefenseSystem());
        cards.add(new AttackSystemPreload());
        cards.add(new DefenseSystemPreload());
        cards.add(new AnalyseSystemPreload());
        cards.add(new QuickAttack());
        cards.add(new QuickDefend());
        cards.add(new QuickScreen());
        cards.add(new DimensionSplitting());
        cards.add(new AnastasiaCore());
        cards.add(new Goodbye());
        cards.add(new AttackInitiation());
        cards.add(new EnduranceInitiation());
        cards.add(new AttackPreparation());
        cards.add(new SpeedShot());
        cards.add(new Strafe());
        cards.add(new EnhancedWeapon());
        cards.add(new Boot());
        cards.add(new FirstStrike());
        cards.add(new SpeedSlash());
        cards.add(new Rob());
        cards.add(new Gravitonium());
        cards.add(new QuickTrigger());
        cards.add(new GravityImpact());


//        克洛伊斯
        cards.add(new TimeBacktracking());
        cards.add(new TimeSlack());
        cards.add(new TimeStop());
        cards.add(new TimeBomb());
        cards.add(new HourHand());
        cards.add(new MinuteHand());
        cards.add(new SecondHand());
        cards.add(new CrackOfTime());
        cards.add(new ReleaseFormShionKuroisu());
        cards.add(new TimeWarp());
        cards.add(new BlackHand());
        cards.add(new TimeOverload());
        cards.add(new Limit());
        cards.add(new OverspeedField());
        cards.add(new DelayAvatar());


//        南小棉
        cards.add(new FinFunnelActive());
        cards.add(new AttackWithDefense());
        cards.add(new LockIndication());
        cards.add(new TacticalLayout());
        cards.add(new FinFunnelSupport());
        cards.add(new SuperCharge());
        cards.add(new Anticoagulation());
        cards.add(new Lure());
        cards.add(new ReleaseFormShionMinami());
        cards.add(new AnestheticReagent());
        cards.add(new EnhancedSupport());
        cards.add(new BattlefieldHeritage());
        cards.add(new GravityLoading());
        cards.add(new FirePower());
        cards.add(new ArmedToTheTeeth());
        cards.add(new GravityCharging());


//      黎夜竹
        cards.add(new IntroductionSilence());
        cards.add(new SacredAdvice());
        cards.add(new DivineRedemption());
        cards.add(new BlueBlade());
        cards.add(new HolyCharge());
        cards.add(new StrengthPray());
        cards.add(new Pray());
        cards.add(new SantaCroce());
        cards.add(new ReleaseFormLiyezhu());
        cards.add(new Awaken());
        cards.add(new HolyCoffinSinkingSpirit());
        cards.add(new HolySlashDown());
        cards.add(new PainfulConfession());
        cards.add(new HolyCoffinRelease());
        cards.add(new BlueRose());

//        anastasia
        cards.add(new FinFunnelUpgrade());
        cards.add(new ShionAnastasiaPlan());
        cards.add(new AttackOrderAlpha());
        cards.add(new AttackOrderBeta());
        cards.add(new AttackOrderDelta());
        cards.add(new AttackOrderGamma());
        cards.add(new EnergyReserve());
        cards.add(new LockOn());
        cards.add(new Reboot());
        cards.add(new TeamWork());
        cards.add(new Read());
        cards.add(new OverloadFortress());


        cards.add(new InvestigationFinFunnelUpgrade());
        cards.add(new GravityFinFunnelUpgrade());
        cards.add(new PursuitFinFunnelUpgrade());

        cards.add(new FunnelMatrix());


//        忘川
        cards.add(new HiltBash());
        cards.add(new Slide());
        cards.add(new PreExecution());
        cards.add(new Sheathe());
        cards.add(new InTheBlink());
        cards.add(new Warp());

//        忘川-拔刀系
        cards.add(new GlandesMagicae());
        cards.add(new StrideSlash());
        cards.add(new OnrushingTip());
        cards.add(new VertexGladii());
        cards.add(new SeverWrist());
        cards.add(new SeverAnkle());
        cards.add(new SeverPetal());
        cards.add(new AethereScindo());
        cards.add(new GladiiInfiniti());
        cards.add(new Guard());
        cards.add(new LunaMergo());
        cards.add(new Reflect());
        cards.add(new Sharpen());
        cards.add(new FlawlessParry());
        cards.add(new Moonstrider());
        cards.add(new SubLuna());
        cards.add(new SeverCurrent());
        cards.add(new WeatherEye());
        cards.add(new Focus());
        cards.add(new OffensiveAdvance());
        cards.add(new EbbAndFlow());
        cards.add(new PetalsFall());
        cards.add(new Poise());
        cards.add(new FullBloom());

//        忘川-魔法系
        cards.add(new BombardaMagica());
        cards.add(new AfflictioBellumque());
        cards.add(new RosaSpinaque());
        cards.add(new MorsLibraque());
        cards.add(new HeliumLuxque());
        cards.add(new Skip());
        cards.add(new DistantiaAbsensque());
        cards.add(new VitaNaturaque());
        cards.add(new CorLapisque());
        cards.add(new AnimaSpiritusque());
        cards.add(new MensVirtusque());
        cards.add(new ElysiumSpesque());

//        忘川-支援
        cards.add(new SupportShion());
        cards.add(new BlockThis());
        cards.add(new Replenish());
        cards.add(new VampireForm());

//        忘川-魔能过载
        cards.add(new Alleviator());
        cards.add(new Recharger());
        cards.add(new Accelerator());
        cards.add(new Recoiler());
        cards.add(new Gravitater());
        cards.add(new Superloader());
        cards.add(new Antisequencer());
        cards.add(new GensBombardae());
        cards.add(new MagicProjection());
        cards.add(new NihilImmensum());
        cards.add(new ArtificiumMundi());
        cards.add(new OculusMortis());

//        通典
        cards.add(new ChaosNimius());
        cards.add(new ChaosRapidus());
        cards.add(new LuxRapida());
        cards.add(new LuxConstans());
        cards.add(new CaligoConstans());
        cards.add(new CaligoRapida());
        cards.add(new CaligoRapida());
        cards.add(new CaelumNimium());
        cards.add(new CaelumRapidum());
        cards.add(new TerraNimia());
        cards.add(new TerraRapida());
        cards.add(new HomoRapidus());
        cards.add(new HomoNimius());
        cards.add(new AquaConstans());
        cards.add(new AquaRapida());
        cards.add(new IgnisRapidus());
        cards.add(new IgnisNimius());
        cards.add(new VentusRapidus());
        cards.add(new VentusNimius());
        cards.add(new AurumNimium());
        cards.add(new AurumFidum());
        cards.add(new LignumNimium());
        cards.add(new LignumConstans());
        cards.add(new TonitrusConstans());
        cards.add(new TonitrusRapidus());

//        黎夜竹
        cards.add(new HolyLight());
        cards.add(new Barrier());
        cards.add(new SoothingScripture());
        cards.add(new EmanationOfIre());
        cards.add(new TranquilPrayer());
        cards.add(new EdgeOfSquall());
        cards.add(new RipsoulShrilling());
        cards.add(new JudgementOfSins());
        cards.add(new RealizingCanticle());
        cards.add(new BeinglessMoment());
        cards.add(new SoleAnthem());
        cards.add(new FlayTheEvil());
        cards.add(new PiousPhrase());
        cards.add(new RavingExcoriation());
        cards.add(new BlindDevotion());
        cards.add(new LimpidHeart());
        cards.add(new SanguinaryPrecept());
        cards.add(new FlickeringTip());
        cards.add(new InordinateAdmonition());
        cards.add(new ChasteReflection());
        cards.add(new AvariciousMotto());
        cards.add(new BurnishedRazor());

        cards.add(new PrincipledThievery());
        cards.add(new HeavenDecree());
        cards.add(new CelestialIncarnation());
        cards.add(new Calamity());
        cards.add(new HallowedCasket());
        cards.add(new EnsanguinedFigure());
        cards.add(new CrimsonDeluge());
        cards.add(new Asceticism());
        cards.add(new TranscendSoul());
        cards.add(new Arbitration());
        cards.add(new ProphecyOfDestruction());
        cards.add(new ProphecyOfSalvation());

        cards.add(new Sentence());
        cards.add(new SacredChop());
        cards.add(new SavageSeries());
        cards.add(new CruciformPenance());
        cards.add(new ReapTheSinful());
        cards.add(new Whisk());
        cards.add(new AnnihilatingChoir());
        cards.add(new SpiritImpact());
        cards.add(new CruelSword());
        cards.add(new Execution());
        cards.add(new Enchant());

        cards.add(new March());
        cards.add(new CompileSoul());
        cards.add(new RedEyes());
        cards.add(new Consult());
        cards.add(new Identify());
        cards.add(new MiseriamVoco());
        cards.add(new CorCrucis());
        cards.add(new VoluntasDoloris());
        cards.add(new ViaAfflictionis());
        cards.add(new ExitiumMaeroris());
        cards.add(new WoeIntoCasket());
        cards.add(new EvilOnMe());
        cards.add(new VerdictUponHeart());
        cards.add(new BrokenSanctuary());
        cards.add(new Precasting());
        cards.add(new LiReboot());
        cards.add(new ShionEmbodiment());




        for (CustomCard card : cards) {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);

            if (card instanceof AbstractShionAnastasiaCard)
                an_Cards.add(card);

            if (card instanceof AbstractShionKuroisuCard)
                ku_Cards.add(card);

            if (card instanceof AbstractShionLiyezhuCard)
                li_Cards.add(card);

            if (card instanceof AbstractShionMinamiCard)
                mi_Cards.add(card);

            if (card instanceof AbstractShionCard)
                shi_Cards.add(card);

            if (card instanceof AbstractCodexCard && !(card instanceof ChaosNimius) && !(card instanceof ChaosRapidus))
                codex_Cards.add(card);

        }
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new DimensionSplitterAria(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new KuroisuDetermination(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Croissant(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new OpticalCamouflage(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Sniperscope(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new AnastasiaNecklace(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new BlueGiant(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new BlueSupergiant(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new TheRipple(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new PlacidAqua(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Drapery(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Parocheth(), CardColorEnum.VUP_Shion_LIME);

        BaseMod.addRelicToCustomPool(new Nebula(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new Protostar(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new TrackingBeacon(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new StarQuakes(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new PureHeart(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new WhitePurity(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new SapphireRoseNecklace(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new TheRipple(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new PlacidAqua(), CardColorEnum.WangChuan_LIME);


        BaseMod.addRelicToCustomPool(new MartyrVessel(), CardColorEnum.Liyezhu_LIME);
        BaseMod.addRelicToCustomPool(new HallowedCase(), CardColorEnum.Liyezhu_LIME);

    }


    private Settings.GameLanguage languageSupport() {
        switch (Settings.language) {
            case ZHS:
                return Settings.language;
            case DEU:
                return Settings.language;
//            case JPN:
//                return Settings.language;
            case ZHT:
                return Settings.language;
            default:
                return Settings.GameLanguage.ENG;
        }
    }

    public void receiveEditStrings() {
        Settings.GameLanguage language = languageSupport();
        loadLocStrings(Settings.GameLanguage.ENG);
        if (!language.equals(Settings.GameLanguage.ENG)) {
            loadLocStrings(language);
        }

    }

    private void loadLocStrings(Settings.GameLanguage language) {
        String path = "localization/" + language.toString().toLowerCase() + "/";

        BaseMod.loadCustomStringsFile(EventStrings.class, assetPath(path + "EventStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class, assetPath(path + "UIStrings.json"));
//        BaseMod.loadCustomStringsFile(PotionStrings.class, assetPath(path + "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(CardStrings.class, assetPath(path + "CardStrings.json"));
        BaseMod.loadCustomStringsFile(MonsterStrings.class, assetPath(path + "MonsterStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, assetPath(path + "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, assetPath(path + "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, assetPath(path + "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class, assetPath(path + "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(StanceStrings.class, assetPath(path + "StanceStrings.json"));
//        BaseMod.loadCustomStringsFile(TutorialStrings.class,assetPath(path + "TutorialStrings.json"));
    }


    private void loadLocKeywords(Settings.GameLanguage language) {
        String path = "localization/" + language.toString().toLowerCase() + "/";
        Gson gson = new Gson();
        String json = Gdx.files.internal(assetPath(path + "KeywordStrings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("vup_shion_mod", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditKeywords() {

        Settings.GameLanguage language = languageSupport();

        // Load english first to avoid crashing if translation doesn't exist for something
        loadLocKeywords(Settings.GameLanguage.ENG);
        if (!language.equals(Settings.GameLanguage.ENG)) {
            loadLocKeywords(language);
        }
    }
}
