package VUPShionMod.patches;

import VUPShionMod.util.Shield;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;

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
            ShieldPatches.AddFields.shield.get(_instance).render(sb,
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
        @SpireInsertPatch(rloc = 22)
        public static void Insert(AbstractMonster _instance, DamageInfo info, int ___damageAmount) {
            ___damageAmount = ShieldPatches.AddFields.shield.get(_instance).decrementBlock(info, ___damageAmount, _instance);
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class PatchAbsPlayer {
        @SpireInsertPatch(rloc = 17)
        public static void Insert(AbstractPlayer _instance, DamageInfo info, int ___damageAmount) {
            ___damageAmount = ShieldPatches.AddFields.shield.get(_instance).decrementBlock(info, ___damageAmount, _instance);
        }
    }

}