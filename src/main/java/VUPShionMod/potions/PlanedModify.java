package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.List;

public class PlanedModify extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(PlanedModify.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.RARE;


    public PlanedModify() {
        super(POTION_ID, PlanedModify.class.getSimpleName() + ".png", RARITY);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = VUPShionMod.ShionPotion_Color;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = String.format(DESCRIPTIONS[0], this.potency);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractFinFunnel finFunnel = FinFunnelManager.getSelectedFinFunnel();
                    if (finFunnel != null)
                        finFunnel.upgradeLevel(potency);
                    isDone = true;
                }
            });
        }
    }


    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PlanedModify();
    }


}