package VUPShionMod.patches;


import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

@SpirePatch(
        clz = TempMusic.class,
        method = "getSong"
)
public class TempMusicPatch {

    @SpirePostfixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        if (key.equals("VUPShionMod:Boss_Phase1")) {
            return SpireReturn.Return(MainMusic.newMusic("VUPShionMod/audio/bgm/Boss_Phase1.ogg"));
        }
        if (key.equals("VUPShionMod:Boss_Phase2")) {
            return SpireReturn.Return(MainMusic.newMusic("VUPShionMod/audio/bgm/Boss_Phase2.ogg"));
        }

        return SpireReturn.Continue();
    }
}
