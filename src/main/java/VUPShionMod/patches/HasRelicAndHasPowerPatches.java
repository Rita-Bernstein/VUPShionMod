package VUPShionMod.patches;

import VUPShionMod.relics.Sniperscope;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PaperFrog;


public class  HasRelicAndHasPowerPatches {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "hasRelic",
            paramtypez = {String.class}

    )
    public static class HasRelicPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Boolean> Insert(AbstractPlayer _instance,String targetID) {
            for(AbstractRelic r : _instance.relics){
                if(r.relicId.equals(Sniperscope.ID) && targetID.equals(PaperFrog.ID))
                    return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

}


