package VUPShionMod.relics.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.monsters.Story.Ouroboros;
import VUPShionMod.monsters.Story.PlagaAMundoMinion;
import VUPShionMod.monsters.Story.TimePortal;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.stances.PrayerStance;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;

public class TimeReversalBullet extends AbstractShionRelic implements OnPlayerDeathRelic {
    public static final String ID = VUPShionMod.makeID(TimeReversalBullet.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/TimeReversalBullet.png";
    private static final String OUTLINE_PATH = "img/relics/outline/TimeReversalBullet.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public TimeReversalBullet() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void setCounter(int counter) {
        if (counter == -2) {
            usedUp();
            this.counter = -2;
        }
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer p, DamageInfo info) {
        boolean canTrigger = true;
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic.relicId.equals(LizardTail.ID) && relic.counter != -2) {
                canTrigger = false;
                break;
            }
        }

        if (AbstractDungeon.player.hasPotion(FairyPotion.POTION_ID))
            canTrigger = false;


        if (canTrigger && this.counter != -2) {
            flash();
            AbstractDungeon.player.currentHealth = 1;
            AbstractDungeon.player.healthBarUpdatedEvent();
            setCounter(-2);
            return false;
        } else {
            return true;
        }
    }
}
