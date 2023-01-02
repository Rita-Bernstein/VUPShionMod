package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.prayers.AbstractPrayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AbstractShionPower extends TwoAmountPower {
    public static TextureAtlas shionAtlas;

    public static void initialize() {
        shionAtlas = new TextureAtlas(Gdx.files.internal("VUPShionMod/img/powers/ShionPowers.atlas"));
    }

    protected void loadShionRegion(String fileName) {
        this.region48 = AbstractShionPower.shionAtlas.findRegion("48/" + fileName);
        this.region128 = AbstractShionPower.shionAtlas.findRegion("128/" + fileName);
    }

    public void onTriggerLoaded() {
    }

    public void onTriggerMagiamObruor(AbstractPower power) {
    }


    public void onShuffle() {
    }

    public void monsterAfterOnAttack(DamageInfo info, AbstractMonster m, int damageAmount) {
    }

    public int onEnemyAttackedPreBlock(DamageInfo info, AbstractMonster m, int damageAmount) {
        return damageAmount;
    }

    public int onAttackedPreBlock(DamageInfo info, AbstractCreature owner, int damageAmount) {
        return damageAmount;
    }

    public void onStackPower(AbstractPower power,int preAmount) {
    }

    public void onNumSpecificTrigger(int amount) {
    }


    public void onDuelSin() {
    }

    public void onCreatePrayer(AbstractPrayer prayer) {
    }

    public void preEndOfRound() {
    }


    public void onTriggerFinFunnel(AbstractFinFunnel finFunnel, AbstractCreature target) {
    }

    public void onAddShieldCharge(int amount) {
    }

    public void onAddShieldRefund(int amount) {
    }

    public void onLoseShieldCharge(int amount) {
    }

    public void onLoseEnergy(int e,int energyUsed) {
    }

    public void onPlayerMinionDeath() {
    }

    public int onLoseBlock(int amount) {
        return amount;
    }
}
