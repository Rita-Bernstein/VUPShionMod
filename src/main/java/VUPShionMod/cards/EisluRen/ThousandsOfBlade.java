package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.stances.SpiralBladeStance;
import VUPShionMod.stances.ThousandsOfBladeStance;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThousandsOfBlade extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(ThousandsOfBlade.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ThousandsOfBlade.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public ThousandsOfBlade() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.selfRetain =true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(ThousandsOfBladeStance.STANCE_ID));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
