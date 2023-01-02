package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

public class Chopper extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(Chopper.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/Chopper.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = -1;

    public Chopper(int upgrade) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
        this.isMultiDamage = true;
        this.timesUpgraded = upgrade;
    }

    public Chopper() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
            addToBot(new LoseWingShieldAction(this.secondaryM));

        Consumer<Integer> actionConsumer = effect -> {
            for (int i = 0; i < magicNumber; i++) {
                int[] finalMultiDamage = DamageInfo.createDamageMatrix(this.baseDamage, false);
                for (int k = 0; k < finalMultiDamage.length; k++)
                    finalMultiDamage[k] *= effect;


                addToTop(new DamageAllEnemiesAction(p, finalMultiDamage,
                        DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
            }
        };

        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
            if (WingShield.getWingShield().getCount() < this.secondaryM) {
                cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:WingShield").TEXT[2];
                return false;
            }

        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();

        upgradeMagicNumber(2);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Chopper(this.timesUpgraded);
    }
}
