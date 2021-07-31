package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.MarkOfThePaleBlueCrossPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class SantaCroce extends AbstractVUPShionCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("SantaCroce");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz09.png"); //TODO lyz08.png
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public SantaCroce() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 8;
        this.baseMagicNumber = this.magicNumber = 1;
        this.baseSecondaryM = this.secondaryM = 1;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.isBranchUpgrade()) {
                this.name = cardStrings.EXTENDED_DESCRIPTION[0];
                this.initializeTitle();
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                this.upgradeSecondM(1);
            } else {
                this.upgradeDamage(4);
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            }
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToTop(new ApplyPowerAction(monster, p, new MarkOfThePaleBlueCrossPower(monster, this.baseMagicNumber)));
                if (this.isBranchUpgrade()) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (monster.hasPower(MarkOfThePaleBlueCrossPower.POWER_ID)) {
                                int mul = monster.getPower(MarkOfThePaleBlueCrossPower.POWER_ID).amount;
                                addToBot(new GainBlockAction(p, mul * SantaCroce.this.secondaryM));
                            }
                            isDone = true;
                        }
                    });
                } else {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (monster.hasPower(MarkOfThePaleBlueCrossPower.POWER_ID)) {
                                int mul = monster.getPower(MarkOfThePaleBlueCrossPower.POWER_ID).amount;
                                addToBot(new HealAction(p, p, mul * SantaCroce.this.secondaryM));
                            }
                            isDone = true;
                        }
                    });
                }
                addToBot(new RemoveSpecificPowerAction(monster, p, MarkOfThePaleBlueCrossPower.POWER_ID));
            }
        }
    }
}
