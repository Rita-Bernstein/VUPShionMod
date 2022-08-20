package VUPShionMod.patches;

import VUPShionMod.msic.Shield;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldPatches {
    @SpirePatch(
            clz = AbstractCreature.class,
            method = SpirePatch.CLASS
    )
    public static class AddFields {
        public static SpireField<Shield> shield = new SpireField<>(() -> new Shield());
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "updateHealthBar"
    )
    public static class PatchUpdate {
        public static void Postfix(AbstractCreature _instance) {
            ShieldPatches.AddFields.shield.get(_instance).update();
        }
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderHealth"
    )
    public static class PatchRenderHealth {
        @SpireInsertPatch(rloc = 16)
        public static void Insert(AbstractCreature _instance, SpriteBatch sb,
                                  float ___hbYOffset, float ___blockOffset, float ___blockScale) {
            ShieldPatches.AddFields.shield.get(_instance).render(sb,_instance,
                    _instance.hb.cX - _instance.hb.width / 2.0F,
                    _instance.hb.cY - _instance.hb.height / 2.0F + ___hbYOffset,
                    ___blockOffset, ___blockScale);
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class PatchAbsMonster {
        @SpireInsertPatch(rloc = 22 , localvars = {"damageAmount"})
        public static void Insert(AbstractMonster _instance, DamageInfo info,@ByRef int[] damageAmount) {
            damageAmount[0] = ShieldPatches.AddFields.shield.get(_instance).decrementBlock(info, damageAmount[0], _instance);
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class PatchAbsPlayer {
        @SpireInsertPatch(rloc = 17, localvars = {"damageAmount"})
        public static void Insert(AbstractPlayer _instance, DamageInfo info,@ByRef int[] damageAmount) {
            damageAmount[0] = ShieldPatches.AddFields.shield.get(_instance).decrementBlock(info, damageAmount[0], _instance);
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class PatchPlayerOnVictory {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _instance) {
            ShieldPatches.AddFields.shield.get(_instance).loseBlock();
        }
    }

}
