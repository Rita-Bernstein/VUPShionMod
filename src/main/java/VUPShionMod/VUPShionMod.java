package VUPShionMod;

import VUPShionMod.cards.optionCards.*;
import VUPShionMod.cards.shion.*;
import VUPShionMod.cards.anastasia.*;
import VUPShionMod.cards.kuroisu.*;
import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.helpers.SecondaryMagicVariable;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.relics.DimensionSplitterAria;
import basemod.BaseMod;

import basemod.ModPanel;
import basemod.abstracts.CustomCard;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.*;


@SpireInitializer
public class VUPShionMod implements
        PostInitializeSubscriber,
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        AddAudioSubscriber,
        EditKeywordsSubscriber,
        EditStringsSubscriber {

    public static final String MODNAME = "VUPShionMod";
    public static final String AUTHOR = "Rita";
    public static final String DESCRIPTION = "";
    public static final Color Shion_Color = new Color(0.418F, 0.230F, 0.566F, 1.0F);
    public static final Logger logger = LogManager.getLogger(VUPShionMod.class.getSimpleName());
    public static String MOD_ID = "VUPShionMod";
    public static Properties VUPShionDefaults = new Properties();
    public static List<CustomCard> shion_Cards = new ArrayList<>();
    public static ArrayList<AbstractGameEffect> effectsQueue = new ArrayList<>();


    public VUPShionMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(CardColorEnum.VUP_Shion_LIME,
                Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color,
                assetPath("img/cardui/Shion/512/bg_attack_lime.png"),
                assetPath("img/cardui/Shion/512/bg_skill_lime.png"),
                assetPath("img/cardui/Shion/512/bg_power_lime.png"),
                assetPath("img/cardui/Shion/512/card_lime_orb.png"),
                assetPath("img/cardui/Shion/1024/bg_attack_lime.png"),
                assetPath("img/cardui/Shion/1024/bg_skill_lime.png"),
                assetPath("img/cardui/Shion/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb.png"),
                assetPath("img/cardui/Shion/512/card_lime_small_orb.png"));

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

    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "VUPShionMod_settings", VUPShionDefaults);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "VUPShionMod_settings", VUPShionDefaults);
            config.load();

        } catch (Exception e) {
            e.printStackTrace();
            clearSettings();
        }
    }

    public static void clearSettings() {
        saveSettings();
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
    }

    @Override
    public void receiveAddAudio() {
        for (int i=1;i<=18;i++) {
            BaseMod.addAudio("SHION_" + i, assetPath("audio/sfx/shion" + i + ".wav"));
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
        cards.add(new Cannonry());
        cards.add(new Defend_Shion());


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


//        anastasia
        cards.add(new FinFunnelUpgrade());

        cards.add(new DimensionSplitterUpgrade());
        cards.add(new InvestigationFinFunnelUpgrade());
        cards.add(new GravityFinFunnelUpgrade());
        cards.add(new PursuitFinFunnelUpgrade());

        for (CustomCard card : cards) {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
        }
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new DimensionSplitterAria(), CardColorEnum.VUP_Shion_LIME);
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
