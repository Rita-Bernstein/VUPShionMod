package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.RestOption;

public class AbyssalCrux extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(AbyssalCrux.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/AbyssalCrux.png";
    private static final String OUTLINE_PATH = "img/relics/outline/AbyssalCrux.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public AbyssalCrux() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public boolean canUseCampfireOption(AbstractCampfireOption option) {
        if (option instanceof RestOption && option.getClass().getName().equals(RestOption.class.getName())) {
            ((RestOption) option).updateUsability(false);
            return false;
        }
        return true;
    }


    public void wasHPLost(int damageAmount) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                damageAmount > 0) {
            flash();
            addToTop(new HealAction(AbstractDungeon.player,AbstractDungeon.player,damageAmount*2));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
}
