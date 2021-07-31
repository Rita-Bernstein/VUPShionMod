package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class Croissant extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("Croissant");
    public static final String IMG_PATH = "img/relics/croissant.png";
    private static final String OUTLINE_PATH = "img/relics/outline/croissant.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public Croissant() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
        getUpdatedDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.heal(30);
    }
}
