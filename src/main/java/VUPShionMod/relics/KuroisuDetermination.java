package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class KuroisuDetermination extends CustomRelic implements OnPlayerDeathRelic {
    public static final String ID = VUPShionMod.makeID("KuroisuDetermination");
    public static final String IMG_PATH = "img/relics/KuroisuDetermination.png";
    private static final String OUTLINE_PATH = "img/relics/outline/KuroisuDetermination.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    private ArrayList<Integer> playerHP;
    private ArrayList<ArrayList<AbstractPower>> powers;

    public KuroisuDetermination() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
        getUpdatedDescription();
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        playerHP.clear();
        powers.clear();
    }

    @Override
    public void atTurnStart() {
        if (!this.usedUp) {
            if (GameActionManager.turn <= 3) {
                playerHP.add(AbstractDungeon.player.currentHealth);
                powers.add(AbstractDungeon.player.powers);
            } else {
                playerHP.remove(0);
                playerHP.add(AbstractDungeon.player.currentHealth);
                powers.remove(0);
                powers.add(AbstractDungeon.player.powers);
            }
        }
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        if (this.counter == -1) {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            setCounter(-2);

            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AbstractDungeon.player.currentHealth = playerHP.get(0);
                AbstractDungeon.player.powers = powers.get(0);
            } else {
                int healAmt = AbstractDungeon.player.maxHealth / 2;
                if (healAmt < 1)   healAmt = 1;
                AbstractDungeon.player.heal(healAmt, true);
            }

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
