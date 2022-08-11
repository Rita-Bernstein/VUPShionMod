package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.SummonElfAction;
import VUPShionMod.minions.ElfMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class ElfSublimation extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(ElfSublimation.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 5;

    public ElfSublimation() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 30;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SummonElfAction(new ElfMinion(2)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!MinionGroup.areMinionsBasicallyDead()) {
                    if (upgraded)
                        addToTop(new ApplyPowerAction(MinionGroup.getCurrentMinion(), p, new BufferPower(MinionGroup.getCurrentMinion(), 1)));
                    addToTop(new ApplyPowerAction(MinionGroup.getCurrentMinion(), p, new IntangiblePlayerPower(MinionGroup.getCurrentMinion(), 1)));
                }
                isDone = true;
            }
        });
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c instanceof ElfEnhance)
            updateCost(-2);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeBaseCost(4);
        }
    }
}
