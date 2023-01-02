package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Lure extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(Lure.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami07.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Lure() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractPlayerPatches.AddFields.finFunnelManager.get(p).finFunnelList.isEmpty())
            for (AbstractMonster mo : (AbstractDungeon.getMonsters()).monsters)
                for (AbstractFinFunnel funnel : FinFunnelManager.getFinFunnelList()) {
                    if (!mo.isDeadOrEscaped())
                        funnel.onPursuitEnemy(mo, this.magicNumber);
                }
    }

    public AbstractCard makeCopy() {
        return new Lure();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);

        }
    }
}
