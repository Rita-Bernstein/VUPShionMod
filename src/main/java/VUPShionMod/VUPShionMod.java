package VUPShionMod;

import VUPShionMod.cards.*;
import VUPShionMod.cards.anastasia.*;
import VUPShionMod.cards.kuroisu.*;
import VUPShionMod.cards.liyezhu.*;
import VUPShionMod.cards.minami.*;
import VUPShionMod.cards.optionCards.GravityFinFunnelUpgrade;
import VUPShionMod.cards.optionCards.InvestigationFinFunnelUpgrade;
import VUPShionMod.cards.optionCards.PursuitFinFunnelUpgrade;
import VUPShionMod.cards.shion.*;
import VUPShionMod.cards.tempCards.QuickAttack;
import VUPShionMod.cards.tempCards.QuickDefend;
import VUPShionMod.cards.tempCards.QuickScreen;
import VUPShionMod.character.Shion;
import VUPShionMod.events.BreakAppointment;
import VUPShionMod.events.CroissantEvent;
import VUPShionMod.events.LostEquipment;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.helpers.SecondaryMagicVariable;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.LoseFinFunnelUpgradePower;
import VUPShionMod.powers.TempFinFunnelUpgradePower;
import VUPShionMod.relics.*;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomCard;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        StartActSubscriber{

    public static final String MODNAME = "VUPShionMod";
    public static final String AUTHOR = "Rita";
    public static final String DESCRIPTION = "";
    public static final Color Shion_Color = new Color(0.418F, 0.230F, 0.566F, 1.0F);
    public static final Logger logger = LogManager.getLogger(VUPShionMod.class.getSimpleName());
    public static String MOD_ID = "VUPShionMod";
    public static Properties VUPShionDefaults = new Properties();
    public static ArrayList<AbstractGameEffect> effectsQueue = new ArrayList<>();
//    public static AbstractFinFunnel.FinFunnelSaver finFunnelSaver;

    public static int gravityFinFunnelLevel = 1;
    public static int investigationFinFunnelLevel = 1;
    public static int pursuitFinFunnelLevel = 1;

    public static List<CustomCard> an_Cards = new ArrayList<>();
    public static List<CustomCard> ku_Cards = new ArrayList<>();
    public static List<CustomCard> li_Cards = new ArrayList<>();
    public static List<CustomCard> mi_Cards = new ArrayList<>();
    public static List<CustomCard> shi_Cards = new ArrayList<>();

    public static boolean useSimpleOrb = false;

    public static ModLabeledToggleButton useSimpleOrbSwitch;

    public VUPShionMod() {
        BaseMod.subscribe(this);

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

    }


    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionDefaults);
            config.setBool("useSimpleOrb", useSimpleOrb);

            System.out.println("==============reskin存入数据");

            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("RingOfDestiny", "settings", VUPShionDefaults);
            config.load();
            useSimpleOrb = config.getBool("useSimpleOrb");

        } catch (Exception e) {
            e.printStackTrace();
            clearSettings();
        }
    }

    public static void clearSettings() {
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
        Texture badgeTexture = new Texture(assetPath("/img/badge.png"));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        useSimpleOrbSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("ModSettings")).TEXT[0], 400.0f, 720.0f - 0 * 50.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, useSimpleOrb, settingsPanel,
                (label) -> {
                }, (button) -> {
            useSimpleOrb = button.enabled;
            saveSettings();
        });


        settingsPanel.addUIElement(useSimpleOrbSwitch);

//        finFunnelSaver = new AbstractFinFunnel.FinFunnelSaver();

        BaseMod.addEvent(new AddEventParams.Builder(CroissantEvent.ID, CroissantEvent.class) //Event ID//
                //Event Character//
                .playerClass(AbstractPlayerEnum.VUP_Shion)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(LostEquipment.ID, LostEquipment.class) //Event ID//
                //Event Character//
                .playerClass(AbstractPlayerEnum.VUP_Shion)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(BreakAppointment.ID, BreakAppointment.class) //Event ID//
                //Event Character//
                .playerClass(AbstractPlayerEnum.VUP_Shion)
                .create());
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
                config.setInt("gravityFinFunnelLevel", gravityFinFunnelLevel);
                config.setInt("investigationFinFunnelLevel", investigationFinFunnelLevel);
                config.setInt("pursuitFinFunnelLevel", pursuitFinFunnelLevel);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        BaseMod.addCharacter(new Shion(Shion.charStrings.NAMES[1], AbstractPlayerEnum.VUP_Shion), assetPath("characters/Shion/Button.png"), assetPath("characters/Shion/portrait.png"), AbstractPlayerEnum.VUP_Shion);
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


//        克洛伊斯
        cards.add(new TimeBacktracking());
        cards.add(new TimeSlack());
        cards.add(new TimeStop());
        cards.add(new TimeBomb());
        cards.add(new HourHand());
        cards.add(new MinuteHand());
        cards.add(new SecondHand());
        cards.add(new CrackOfTime());
        cards.add(new ReleaseFormKuroisu());
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
        cards.add(new ReleaseFormMinami());
        cards.add(new AnestheticReagent());
        cards.add(new EnhancedSupport());
        cards.add(new BattlefieldHeritage());
        cards.add(new GravityLoading());
        cards.add(new FirePower());
        cards.add(new ArmedToTheTeeth());


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
        cards.add(new AnastasiaPlan());
        cards.add(new AttackOrderAlpha());
        cards.add(new AttackOrderBeta());
        cards.add(new AttackOrderDelta());
        cards.add(new AttackOrderGamma());
        cards.add(new EnergyReserve());
        cards.add(new LockOn());
        cards.add(new Reboot());
        cards.add(new TeamWork());


        cards.add(new InvestigationFinFunnelUpgrade());
        cards.add(new GravityFinFunnelUpgrade());
        cards.add(new PursuitFinFunnelUpgrade());

        for (CustomCard card : cards) {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);

            if (card instanceof AbstractAnastasiaCard)
                an_Cards.add(card);

            if (card instanceof AbstractKuroisuCard)
                ku_Cards.add(card);

            if (card instanceof AbstractLiyezhuCard)
                li_Cards.add(card);

            if (card instanceof AbstractMinamiCard)
                mi_Cards.add(card);

            if (card instanceof AbstractShionCard)
                shi_Cards.add(card);

        }
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new DimensionSplitterAria(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new KuroisuDetermination(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Croissant(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new OpticalCamouflage(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Sniperscope(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.removeRelic(new LizardTail());
        BaseMod.addRelic(new LizardTail() {
            @Override
            public boolean canSpawn() {
                return !(AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion);
            }
        }, RelicType.SHARED);
    }


    private Settings.GameLanguage languageSupport() {
        switch (Settings.language) {
            case ZHS:
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
