package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.ui.ShionMainMenuPanelButton;
import VUPShionMod.util.SaveHelper;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue.Save;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import com.megacrit.cardcrawl.screens.mainMenu.MenuPanelScreen;

public class ShionBasePatches {
    @SpireEnum
    public static MenuButton.ClickResult ShionBase;
    @SpireEnum
    public static MenuPanelScreen.PanelScreen ShionBaseScreen;

    //    初始加主菜单按钮
    @SpirePatch(
            clz = MainMenuScreen.class,
            method = "setMainMenuButtons"
    )
    public static class SetMainMenuButtonsPatch {
        @SpireInsertPatch(rloc = 22, localvars = {"index"})
        public static void Insert(MainMenuScreen _instance, @ByRef int[] index) {
                _instance.buttons.add(new MenuButton(ShionBasePatches.ShionBase, index[0]++));
        }
    }

    //放弃游戏后刷新按钮
    @SpirePatch(
            clz = MainMenuScreen.class,
            method = "update"
    )
    public static class MainMenuScreenUpdatePatch {
        @SpireInsertPatch(rloc = 13)
        public static void Insert(MainMenuScreen _instance) {
                _instance.buttons.add(new MenuButton(ShionBasePatches.ShionBase, _instance.buttons.size()));
        }
    }

    //    初始化名字
    @SpirePatch(
            clz = MenuButton.class,
            method = "setLabel"
    )
    public static class MenuButtonSetLabelPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(MenuButton _instance) {
            if (_instance.result == ShionBasePatches.ShionBase) {
                ReflectionHacks.setPrivate(_instance, MenuButton.class, "label",
                        CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ShionBasePatches.class.getSimpleName())).TEXT[0]);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    //按钮效果-打开紫音基地
    @SpirePatch(
            clz = MenuButton.class,
            method = "buttonEffect"
    )
    public static class MenuButtonButtonEffectPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(MenuButton _instance) {
            if (_instance.result == ShionBasePatches.ShionBase) {
                CardCrawlGame.mainMenuScreen.panelScreen.open(ShionBasePatches.ShionBaseScreen);
                return SpireReturn.Return();
            }

            if (_instance.result == MenuButton.ClickResult.PLAY) {
                SaveHelper.isTrainingMod = false;
                SaveHelper.saveSettings();
            }

            return SpireReturn.Continue();
        }
    }

    //初始化界面按钮
    @SpirePatch(
            clz = MenuPanelScreen.class,
            method = "initializePanels"
    )
    public static class MenuPanelScreenPatch {
        @SpireInsertPatch(rloc = 1)
        public static SpireReturn<Void> Insert(MenuPanelScreen _instance) {
            MenuPanelScreen.PanelScreen screen = ReflectionHacks.getPrivate(_instance, MenuPanelScreen.class, "screen");

            if (screen == ShionBasePatches.ShionBaseScreen) {
                _instance.panels.add(new ShionMainMenuPanelButton(ShionMainMenuPanelButton.Play_ShionBase,
                        SkinManager.getSkin(3, 0).unlock ? MainMenuPanelButton.PanelColor.BLUE : MainMenuPanelButton.PanelColor.GRAY,
                        Settings.WIDTH / 2.0F - 450.0F * Settings.scale, Settings.HEIGHT / 2.0F, 0));


                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }


}
