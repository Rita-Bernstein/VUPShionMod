package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.PlayTmpCardAction;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.minami.SetupFinFunnel;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.powers.Shion.ConcordPower;
import VUPShionMod.powers.Shion.FireCalibrationPower;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.ui.SynchroOption;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;

import java.util.ArrayList;

public class ConcordCompanion extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(ConcordCompanion.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/ConcordCompanion.png";
    private static final String OUTLINE_PATH = "img/relics/outline/ConcordCompanion.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public ConcordCompanion() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new GainHyperdimensionalLinksAction(6));
        addToBot(new GainEnergyAction(2));


        AbstractCard c = null;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card instanceof SetupFinFunnel) {
                c = card.makeStatEquivalentCopy();
                break;
            }
        }

        if (c != null)
            addToBot(new PlayTmpCardAction(c));


    }
}
