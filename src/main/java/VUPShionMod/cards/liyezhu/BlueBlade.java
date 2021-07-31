package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.MarkOfThePaleBlueCrossPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class BlueBlade extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("BlueBlade");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami14.png");//TODO lyz04.png
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 0;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public BlueBlade() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new CleaveEffect()));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(monster, p, new MarkOfThePaleBlueCrossPower(monster, this.baseMagicNumber)));
            }
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int[] damage = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
                for (int i=0;i<damage.length;i++) {
                    int tmp = 0;
                    if (AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hasPower(MarkOfThePaleBlueCrossPower.POWER_ID)) {
                        tmp += AbstractDungeon.getCurrRoom().monsters.monsters.get(i).getPower(MarkOfThePaleBlueCrossPower.POWER_ID).amount * BlueBlade.this.baseSecondaryM;
                    }
                    damage[i] = tmp;
                }
                addToBot(new DamageAllEnemiesAction(p, damage, BlueBlade.this.damageTypeForTurn, AttackEffect.SLASH_DIAGONAL));
                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        addToBot(new RemoveSpecificPowerAction(monster, p, MarkOfThePaleBlueCrossPower.POWER_ID));
                    }
                }
                isDone = true;
            }
        });
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeSecondM(1);
        }
    }
}
