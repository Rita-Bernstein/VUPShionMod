package VUPShionMod.patches;


import VUPShionMod.stances.*;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;


public class StancePatch {
    @SpirePatch(
            clz = AbstractStance.class,
            method = "getStanceFromName"
    )
    public static class DefensiveMode_StancePatch {
        @SpirePrefixPatch
        public static SpireReturn<AbstractStance> returnStance(String name) {
            if (name.equals(PrayerStance.STANCE_ID)) {
                return SpireReturn.Return(new PrayerStance());
            }
            if (name.equals(JudgeStance.STANCE_ID)) {
                return SpireReturn.Return(new JudgeStance());
            }

            if (name.equals(SpiritStance.STANCE_ID)) {
                return SpireReturn.Return(new SpiritStance());
            }

            if (name.equals(SpiralBladeStance.STANCE_ID)) {
                return SpireReturn.Return(new SpiralBladeStance());
            }

            if (name.equals(ThousandsOfBladeStance.STANCE_ID)) {
                return SpireReturn.Return(new ThousandsOfBladeStance());
            }

            if (name.equals(LotusOfWarStance.STANCE_ID)) {
                return SpireReturn.Return(new LotusOfWarStance());
            }

            if (name.equals(RuinGuardianStance.STANCE_ID)) {
                return SpireReturn.Return(new RuinGuardianStance());
            }

            if (name.equals(LightArmorStance.STANCE_ID)) {
                return SpireReturn.Return(new LightArmorStance());
            }


            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = ChangeStanceAction.class,
            method = "update"
    )
    public static class ChangeStanceActionPatch {
        @SpireInsertPatch(rloc = 7)
        public static SpireReturn<Void> Insert(ChangeStanceAction _instance) {
            AbstractStance oldStance = AbstractDungeon.player.stance;
            String id = ReflectionHacks.getPrivate(_instance, ChangeStanceAction.class, "id");
            if (id != null)
                if (oldStance.ID.equals(SpiritStance.STANCE_ID) && id.equals(JudgeStance.STANCE_ID)) {
                    ReflectionHacks.setPrivate(_instance, ChangeStanceAction.class, "id", SpiritStance.STANCE_ID);
                }

            return SpireReturn.Continue();
        }
    }

//    @SpirePatch(
//            clz = StanceAuraEffect.class,
//            method = SpirePatch.CONSTRUCTOR,
//            paramtypez = {String.class}
//    )
//    public static class StanceAuraEffectPatch {
//        @SpirePostfixPatch
//        public static SpireReturn<Void> Postfix(StanceAuraEffect _instance, String stanceId) {
//            if (stanceId.equals("DefensiveMode")) {
//                try{
//                    Field colorField = _instance.getClass().getSuperclass().getDeclaredField("color");
//                    colorField.setAccessible(true);
//                    colorField.set(_instance, new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.6F, 0.7F), MathUtils.random(0.5F, 0.55F), 0.0F));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return SpireReturn.Continue();
//        }
//    }
}



