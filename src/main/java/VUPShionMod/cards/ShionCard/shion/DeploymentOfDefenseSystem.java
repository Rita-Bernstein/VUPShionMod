package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.powers.Shion.DeploymentOfDefenseSystemPower;
import VUPShionMod.powers.Shion.DeploymentOfDefenseSystemPower2;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

public class DeploymentOfDefenseSystem extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(DeploymentOfDefenseSystem.class.getSimpleName());
    public static final String IMG =  VUPShionMod.assetPath("img/cards/ShionCard/shion/zy04.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public DeploymentOfDefenseSystem() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("SHION_9"));
        addToBot(new SFXAction("RAGE"));
        addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.ORANGE, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.0F));
        addToBot(new ApplyPowerAction(p, p, new DeploymentOfDefenseSystemPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DeploymentOfDefenseSystemPower2(p, this.secondaryM)));
    }
}
