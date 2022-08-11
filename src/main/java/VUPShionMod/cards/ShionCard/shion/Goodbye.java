package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.PlayTmpCardAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.cards.ShionCard.anastasia.TeamWork;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Goodbye extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(Goodbye.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy18.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public Goodbye() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 3;
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
        addToBot(new SFXAction("SHION_12"));
      /*  addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> tmp = new ArrayList<>();
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.type == CardType.ATTACK) {
                        tmp.add(c);
                    }
                }

                if (!tmp.isEmpty()) {
                    if (tmp.size() == 1) {
                        for (int i = 0; i < magicNumber; i++)
                            addToTop(new PlayTmpCardAction(tmp.get(0)));
                    }else {
                        addToTop(new PlayTmpCardAction(tmp.get(AbstractDungeon.miscRng.random(tmp.size()-1))));
                    }
                }

//                for(int i = 0; i < p.hand.size(); i++) {
//                    addToTop(new DamageAction(m, new DamageInfo(p, Goodbye.this.damage, Goodbye.this.damageTypeForTurn), AttackEffect.NONE));
//                    if (m != null)
//                    addToTop(new VFXAction(new AbstractAtlasGameEffect("Sparks 069 Impact Explosion Radial", m.hb.cX, m.hb.cY + 0.0f * Settings.scale,
//                            125.0f, 125.0f, 2.0f * Settings.scale, 2, false)));
//                    addToTop(new SFXAction("BLUNT_FAST"));
//                }

                for (int i = 0; i < p.hand.size(); i++) {
                    if (Settings.FAST_MODE) {
                        addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
                    } else {
                        addToTop(new ExhaustAction(1, true, true));
                    }
                }
                isDone = true;
            }
        });*/

        addToBot(new ExhaustAction(BaseMod.MAX_HAND_SIZE, true, false, false, Settings.ACTION_DUR_XFAST));

        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new PlayTmpCardAction(AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK)));
        }
    }

}
