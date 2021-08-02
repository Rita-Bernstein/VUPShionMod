package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class KuroisuDetermination extends CustomRelic implements OnPlayerDeathRelic {
    public static final String ID = VUPShionMod.makeID("KuroisuDetermination");
    public static final String IMG_PATH = "img/relics/KuroisuDetermination.png";
    private static final String OUTLINE_PATH = "img/relics/outline/KuroisuDetermination.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));


    public KuroisuDetermination() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
        getUpdatedDescription();
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void atTurnStart() {
        super.atTurnStart();

    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        if(this.counter == -1 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            setCounter(-2);
        }
        return false;
    }

    @Override
    public void setCounter(int setCounter) {
        if (setCounter == -2) {
            usedUp();
            this.counter = -2;
        }
    }
}
