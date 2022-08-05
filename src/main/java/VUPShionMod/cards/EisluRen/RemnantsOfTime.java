package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.GameStatsPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

public class RemnantsOfTime extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(RemnantsOfTime.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 4;

    public RemnantsOfTime() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 1;
        this.magicNumber = this.baseMagicNumber = 3;
        this.selfRetain = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = AbstractDungeon.cardRng.random(1, 3);
        calculateCardDamage(m);

        for (int i = 0; i < this.magicNumber; i++)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void onRetained() {
        updateCost(-1);
        upgradeMagicNumber(1);
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
