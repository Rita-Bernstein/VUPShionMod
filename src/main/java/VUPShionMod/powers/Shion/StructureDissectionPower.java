package VUPShionMod.powers.Shion;


import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.LoseMaxHPAction;
import VUPShionMod.monsters.RitaShop;
import VUPShionMod.monsters.Story.Ouroboros;
import VUPShionMod.monsters.Story.TimePortal;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Monster.RitaShop.DefenceMonsterPower;
import VUPShionMod.powers.Monster.TimePortal.ContortTimePower;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Deca;
import com.megacrit.cardcrawl.monsters.beyond.Donu;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.powers.*;

public class StructureDissectionPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("StructureDissectionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StructureDissectionPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/StructureDissectionPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/StructureDissectionPower36.png")), 0, 0, 36, 36);
        updateDescription();
        this.type = PowerType.DEBUFF;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        this.description += DESCRIPTIONS[1];
        this.description += DESCRIPTIONS[2];
        this.description += DESCRIPTIONS[3];
        this.description += DESCRIPTIONS[4];
        this.description += DESCRIPTIONS[5];
        this.description += DESCRIPTIONS[6];
        this.description += DESCRIPTIONS[7];
        this.description += DESCRIPTIONS[8];
        this.description += DESCRIPTIONS[9];
        this.description += DESCRIPTIONS[10];
        this.description += DESCRIPTIONS[11];


        this.description = String.format(this.description,
                3,
                7,
                12,
                18,
                25,
                33,
                42,
                52,
                64,
                78,
                94
        );

        if (!this.owner.isPlayer) {
            AbstractMonster m = (AbstractMonster) this.owner;
            if (m.type == AbstractMonster.EnemyType.BOSS) {

                if (m.id.equals(BronzeAutomaton.ID) || m.id.equals(TheCollector.ID) || m.id.equals(Deca.ID)) {
                    int alive = 0;

                    if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                        for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                            if (!monster.isDeadOrEscaped()) {
                                alive++;
                            }
                        }

                        if (alive > 1) {
                            switch (m.id) {
                                case BronzeAutomaton.ID:
                                    this.description += String.format(DESCRIPTIONS[13], m.name, CardCrawlGame.languagePack.getMonsterStrings("BronzeOrb").NAME);
                                    return;
                                case TheCollector.ID:
                                    this.description += String.format(DESCRIPTIONS[13], m.name, CardCrawlGame.languagePack.getMonsterStrings("TorchHead").NAME);
                                    return;
                                case Deca.ID:
                                    this.description += String.format(DESCRIPTIONS[13], m.name, CardCrawlGame.languagePack.getMonsterStrings("Donu").NAME);
                                    return;
                            }


                        }
                    }
                }


                if (m.id.equals(Champ.ID) && m.hasPower(MetallicizePower.POWER_ID)) {
                    this.description += String.format(DESCRIPTIONS[12], m.name, MetallicizePower.NAME);
                    return;
                }

                if (m.id.equals(AwakenedOne.ID) && m.hasPower(CuriosityPower.POWER_ID)) {
                    this.description += String.format(DESCRIPTIONS[12], m.name, CuriosityPower.NAME);
                    return;
                }

                if (m.id.equals(TimeEater.ID) && m.hasPower(TimeWarpPower.POWER_ID)) {
                    this.description += String.format(DESCRIPTIONS[12], m.name, TimeWarpPower.NAME);
                    return;
                }

                if (m.id.equals(Donu.ID)) {
                    this.description += String.format(DESCRIPTIONS[13], m.name, CardCrawlGame.languagePack.getMonsterStrings("Donu").NAME);
                    return;
                }


                if (m.id.equals(CorruptHeart.ID) && m.hasPower(InvinciblePower.POWER_ID)) {
                    this.description += String.format(DESCRIPTIONS[12], m.name, InvinciblePower.NAME);
                    return;
                }


                if (m.id.equals(RitaShop.ID) && m.hasPower(DefenceMonsterPower.POWER_ID)) {
                    this.description += String.format(DESCRIPTIONS[12], m.name, DefenceMonsterPower.NAME);
                    return;
                }

                if (m.id.equals(TimePortal.ID) && m.hasPower(ContortTimePower.POWER_ID)) {
                    this.description += String.format(DESCRIPTIONS[12], m.name, ContortTimePower.NAME);
                    return;
                }

                if (m.id.equals(Ouroboros.ID) && m.hasPower(ContortTimePower.POWER_ID)) {
                    this.description += String.format(DESCRIPTIONS[12], m.name, ContortTimePower.NAME);
                    return;
                }

                this.description += String.format(DESCRIPTIONS[14], m.name);
            }

        }

    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        trigger();
    }

    @Override
    public void stackPower(int stackAmount) {
        int previous = this.amount;
        super.stackPower(stackAmount);
        trigger(previous);
    }

    public void trigger() {
        trigger(0);
    }

    public void trigger(int previous) {
        if (previous < 3 && this.amount >= 3)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new VulnerablePower(this.owner, 5, false)));

        if (previous < 7 && this.amount >= 7)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new WeakPower(this.owner, 5, false)));

        if (previous < 12 && this.amount >= 12) {
            addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -9)));
            addToTop(new ApplyPowerAction(this.owner, this.owner, new GainStrengthPower(this.owner, 9)));
        }

        if (previous < 18 && this.amount >= 18)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new SlowPower(this.owner, 0)));

        if (previous < 25 && this.amount >= 25)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1)));

        if (previous < 33 && this.amount >= 33)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -2)));

        if (previous < 42 && this.amount >= 42)
            addToTop(new DamageAction(this.owner, new DamageInfo(null, this.owner.maxHealth / 10, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

        if (previous < 52 && this.amount >= 52)
            addToTop(new LoseMaxHPAction(this.owner, this.owner, this.owner.maxHealth / 20));

        if (previous < 64 && this.amount >= 64)
            addToTop(new LoseMaxHPAction(this.owner, this.owner, this.owner.maxHealth / 20));


        if (previous < 78 && this.amount >= 78)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new FireCalibrationPower(this.owner, 3)));

        if (previous < 94 && this.amount >= 94) {
            if (this.owner instanceof AbstractMonster) {
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));

                if (((AbstractMonster) this.owner).type == AbstractMonster.EnemyType.BOSS) {
                    AbstractMonster m = (AbstractMonster) this.owner;

//                    收藏家，桐人
                    if (m.id.equals(TheCollector.ID) || m.id.equals(BronzeAutomaton.ID)) {
                        int alive = 0;

                        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                                if (!monster.isDeadOrEscaped()) {
                                    alive++;
                                }
                            }

                            if (alive > 1) {
                                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                                    if (!monster.isDeadOrEscaped()) {
                                        if (monster.hasPower(MinionPower.POWER_ID)) {
                                            addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", monster.hb.cX, monster.hb.cY,
                                                    96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                                            addToTop(new AbstractGameAction() {
                                                @Override
                                                public void update() {
                                                    monster.currentHealth = 1;
                                                    monster.healthBarUpdatedEvent();
                                                    isDone = true;
                                                }
                                            });
                                        }
                                    }
                                }

                                return;
                            }
                        }
                    }

//                    第一勇士
                    if (m.id.equals(Champ.ID) && m.hasPower(MetallicizePower.POWER_ID)) {
                        addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", m.hb.cX, m.hb.cY,
                                96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, MetallicizePower.POWER_ID));
                        return;
                    }

                    if (m.id.equals(AwakenedOne.ID) && m.hasPower(CuriosityPower.POWER_ID)) {
                        addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", m.hb.cX, m.hb.cY,
                                96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, CuriosityPower.POWER_ID));
                        return;
                    }

                    if (m.id.equals(TimeEater.ID) && m.hasPower(TimeWarpPower.POWER_ID)) {
                        addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", m.hb.cX, m.hb.cY,
                                96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, TimeWarpPower.POWER_ID));
                        return;
                    }

                    if (m.id.equals(Deca.ID)) {
                        for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                            if (!monster.isDeadOrEscaped()) {
                                if (monster.name.equals(Donu.ID)) {
                                    addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", monster.hb.cX, monster.hb.cY,
                                            96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                                    addToTop(new AbstractGameAction() {
                                        @Override
                                        public void update() {
                                            monster.currentHealth = 1;
                                            monster.healthBarUpdatedEvent();
                                            isDone = true;
                                        }
                                    });
                                    return;
                                }
                            }
                        }
                    }

                    if (m.id.equals(Donu.ID)) {
                        addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", m.hb.cX, m.hb.cY,
                                96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                        addToTop(new AbstractGameAction() {
                            @Override
                            public void update() {
                                m.currentHealth = 1;
                                m.healthBarUpdatedEvent();
                                isDone = true;
                            }
                        });
                        return;
                    }


                    if (m.id.equals(CorruptHeart.ID) && m.hasPower(InvinciblePower.POWER_ID)) {
                        addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", m.hb.cX, m.hb.cY,
                                96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, InvinciblePower.POWER_ID));
                        return;
                    }


                    if (m.id.equals(RitaShop.ID) && m.hasPower(DefenceMonsterPower.POWER_ID)) {
                        addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", m.hb.cX, m.hb.cY,
                                96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                        addToTop(new RemoveSpecificPowerAction(m, AbstractDungeon.player, DefenceMonsterPower.POWER_ID));
                    }

                    if (m.id.equals(TimePortal.ID) && m.hasPower(ContortTimePower.POWER_ID)) {
                        addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", m.hb.cX, m.hb.cY,
                                96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                        addToTop(new RemoveSpecificPowerAction(m, AbstractDungeon.player, ContortTimePower.POWER_ID));
                    }

                    if (m.id.equals(Ouroboros.ID) && m.hasPower(ContortTimePower.POWER_ID)) {
                        addToTop(new VFXAction(new AbstractAtlasGameEffect("Analysis complete", m.hb.cX, m.hb.cY,
                                96.0f, 54.0f, 5.0f * Settings.scale, 2, false)));
                        addToTop(new RemoveSpecificPowerAction(m, AbstractDungeon.player, ContortTimePower.POWER_ID));
                    }


                    addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -3)));

                } else
                    addToTop(new StunMonsterAction((AbstractMonster) this.owner, this.owner, 1));
            }
        }

    }

}
