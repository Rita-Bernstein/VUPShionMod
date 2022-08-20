package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.prayers.AbstractPrayer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AbstractShionPower extends TwoAmountPower {
    public void setImage(String bigImageName, String smallImageName) {
        String path = VUPShionMod.assetPath("img/powers/");

        String path128 = path + bigImageName;
        String path48 = path + smallImageName;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    public void setTinyImage(String imgName) {
        String path = VUPShionMod.assetPath("img/powers/");
        this.img = ImageMaster.loadImage(path + imgName);
    }

    public void onTriggerLoaded(){}

    public void onTriggerMagiamObruor(AbstractPower power){}


    public void onShuffle(){}

    public void monsterAfterOnAttack(DamageInfo info, AbstractMonster m,int damageAmount){
    }

    public int monsterAttackPreBlock(DamageInfo info, AbstractMonster m,int damageAmount){
        return damageAmount;
    }

    public void onStackPower(AbstractPower power){

    }

    public void onNumSpecificTrigger(int amount){
    }


    public void onDuelSin(){
    }

    public void onCreatePrayer(AbstractPrayer prayer){
    }

    public void preEndOfRound(){}


    public void onTriggerFinFunnel(AbstractFinFunnel finFunnel,AbstractCreature target){}

    public void onAddShieldCharge(int amount){}

    public void onAddShieldRefund(int amount){}

    public void onLoseShieldCharge(int amount){}

    public void onLoseEnergy(int e){}

    public void onPlayerMinionDeath(){}

    public int onLoseBlock(int amount){return amount;}
}
