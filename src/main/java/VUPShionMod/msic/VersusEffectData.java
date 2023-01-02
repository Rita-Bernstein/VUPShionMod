package VUPShionMod.msic;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.skins.SkinManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VersusEffectData {
    public String vsTitleName;
    public String playerClassIcon;
    public String portrait;
    public String playerNameImg;

    public static void addData(String ID, String portrait, String playerNameImg, String playerClassIcon, String vsTitleName) {
        VersusEffectData data = new VersusEffectData();

        if (portrait != null && !portrait.equals("")) {
            data.portrait = portrait;
        } else {
            data.portrait = "VUPShionMod/img/vfx/VersusEffect/portrait/Empty.png";
        }

        if (playerNameImg != null && !playerNameImg.equals("")) {
            data.playerNameImg = playerNameImg;
        } else {
            data.playerNameImg = "VUPShionMod/img/vfx/VersusEffect/name/Empty.png";
        }


        if (playerClassIcon != null && !playerClassIcon.equals("")) {
            data.playerClassIcon = playerClassIcon;
        } else {
            data.playerClassIcon = "VUPShionMod/img/vfx/VersusEffect/classIcon/Empty.png";
        }

        if (vsTitleName != null && !vsTitleName.equals(""))
            data.vsTitleName = vsTitleName;
        else {
            data.vsTitleName = "VUPShionMod/img/vfx/VersusEffect/title/Empty.png";
        }


        VUPShionMod.versusEffectData.put(ID, data);
    }

    public static void addData(String ID, String portrait, String playerNameImg, String playerClassIcon) {
        addData(ID, portrait, playerNameImg, playerClassIcon, "");
    }

    public static String getPlayerID() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) {
            return "Ironclad";
        }

        if (p.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
            return "Silent";
        }

        if (p.chosenClass == AbstractPlayer.PlayerClass.DEFECT) {
            return "Defect";
        }

        if (p.chosenClass == AbstractPlayer.PlayerClass.WATCHER) {
            return "Watcher";
        }

        if (p.chosenClass == AbstractPlayerEnum.VUP_Shion) {
            return SkinManager.getSkin(0).skinId;
        }

        if (p.chosenClass == AbstractPlayerEnum.WangChuan) {
            return SkinManager.getSkin(1).skinId;
        }

        if (p.chosenClass == AbstractPlayerEnum.Liyezhu) {
            return SkinManager.getSkin(2).skinId;
        }

        if (p.chosenClass == AbstractPlayerEnum.EisluRen) {
            return SkinManager.getSkin(3).skinId;
        }

        return "";
    }
}
