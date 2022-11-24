package VUPShionMod.cards.ShionCard.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionKuroisuCard;
import VUPShionMod.character.Shion;
import VUPShionMod.powers.Shion.ReleaseFormKuroisuPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ReleaseFormKuroisu extends AbstractShionKuroisuCard {
    public static final String ID = VUPShionMod.makeID(ReleaseFormKuroisu.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/kuroisu/kuroisu09.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormKuroisu() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("SHION_" + (32 + MathUtils.random(1))));
        addToBot(new VFXAction(new PortraitWindyPetalEffect("Kuroisu"),1.0f));
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormKuroisuPower(p, this.magicNumber), 0));
    }

    public AbstractCard makeCopy() {
        return new ReleaseFormKuroisu();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }


}
