package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.BrokenSanctuary2Power;
import VUPShionMod.powers.Liyezhu.BrokenSanctuaryPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class BrokenSanctuary extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(BrokenSanctuary.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/BrokenSanctuary.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public BrokenSanctuary() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.selfRetain = true;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("STANCE_ENTER_CALM"));
        addToBot(new VFXAction(new BorderFlashEffect(Color.SKY, true)));

        addToBot(new ApplyPowerAction(p, p, new BrokenSanctuaryPower(p)));
        addToBot(new ApplyPowerAction(p, p, new BrokenSanctuary2Power(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}