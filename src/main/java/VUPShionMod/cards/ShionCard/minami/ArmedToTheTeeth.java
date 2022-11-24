package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.LoseHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.character.Shion;
import VUPShionMod.powers.Shion.HyperdimensionalLinksPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class ArmedToTheTeeth extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(ArmedToTheTeeth.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami15.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ArmedToTheTeeth() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("SHION_16"));
        addToBot(new VFXAction(new PortraitWindyPetalEffect("ArmedToTheTeeth"),1.0f));
        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
            int amount = p.getPower(HyperdimensionalLinksPower.POWER_ID).amount*2;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount));
            addToBot(new LoseHyperdimensionalLinksAction(true));
        }
    }

    public AbstractCard makeCopy() {
        return new ArmedToTheTeeth();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(1);
        }
    }

}
