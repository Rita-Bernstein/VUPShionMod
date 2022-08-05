package VUPShionMod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class WingShieldIcon {

    private int count = 0;
    private int index;
    private Color color = Color.WHITE.cpy();

    private float cX;
    private float cY;
    private float scale = 0.55f;

    private int refundCharge = 0;


    private float lightTimer = -0.1f;

    private float disappearTimer = 0.0f;

    public WingShieldIcon(int index) {
        this.index = index;
    }

    public void update(float x, float y, float scale) {
        this.cX = x;
        this.cY = y;
        this.scale = scale;


        if (this.disappearTimer > 0.0f) {
            this.disappearTimer -= Gdx.graphics.getDeltaTime();
            if (this.disappearTimer <= 0.0f)
                this.disappearTimer = 0.0f;
        }

        if (this.disappearTimer < 0.0f) {
            this.disappearTimer += Gdx.graphics.getDeltaTime();
            if (this.disappearTimer >= 0.0f)
                this.disappearTimer = 0.0f;
        }


        if (this.disappearTimer == 0.0f && this.refundCharge > 0) {
            if (this.refundCharge == 1)
                this.lightTimer += Gdx.graphics.getDeltaTime() * 2.0f;
            else
                this.lightTimer += Gdx.graphics.getDeltaTime() * 6.0f;

        } else
            this.lightTimer = -0.1f;
    }


    public void show() {
        this.disappearTimer = -0.2f;
    }

    public void hide() {
        this.disappearTimer = 0.2f;
    }

    public void setRefundCharge(int refundCharge) {
        this.refundCharge = refundCharge;
    }

    public void setCount(int count) {
        this.count = count;
        if (this.count > 4)
            this.count = 4;
        if (this.count < 0) {
            this.count = 0;
        }

    }

    public void reset() {
        this.count = 0;
        this.refundCharge = 0;
        this.lightTimer = -0.1f;
        this.disappearTimer = 0.0f;
    }

    public int getCount() {
        return this.count;
    }

    public void render(SpriteBatch sb, Texture[] icons) {
        if (icons.length <= 0) return;

        this.color.a = 1.0f;
        sb.setColor(this.color);

//===隐藏，也就是失去层数的时候
        if (this.disappearTimer > 0.0f) {
            if (icons[this.count] != null) {
                sb.draw(icons[this.count], this.cX - 80.0f, this.cY,
                        80.0f, 0.0f, 160, 320,
                        this.scale * Settings.scale, this.scale * Settings.scale,
                        -this.index * 360.0f / 7.0f,
                        0, 0, 160, 320, false, false);
            }

            this.color.a = this.disappearTimer * 5.0f;
            sb.setColor(this.color);

            if (this.count < 4)
                if (icons[this.count + 1] != null) {
                    sb.draw(icons[this.count + 1], this.cX - 80.0f, this.cY,
                            80.0f, 0.0f, 160, 320,
                            this.scale * Settings.scale, this.scale * Settings.scale,
                            -this.index * 360.0f / 7.0f,
                            0, 0, 160, 320, false, false);
                }

            return;
        }

        if (this.disappearTimer < 0.0f) {
            if (this.count > 1)
                if (icons[this.count - 1] != null) {
                    sb.draw(icons[this.count - 1], this.cX - 80.0f, this.cY,
                            80.0f, 0.0f, 160, 320,
                            this.scale * Settings.scale, this.scale * Settings.scale,
                            -this.index * 360.0f / 7.0f,
                            0, 0, 160, 320, false, false);
                }

            this.color.a = (0.2f + this.disappearTimer) * 5.0f;
            sb.setColor(this.color);


            if (icons[this.count] != null) {
                sb.draw(icons[this.count], this.cX - 80.0f, this.cY,
                        80.0f, 0.0f, 160, 320,
                        this.scale * Settings.scale, this.scale * Settings.scale,
                        -this.index * 360.0f / 7.0f,
                        0, 0, 160, 320, false, false);
            }

            return;
        }


        if (this.refundCharge > 0) {
            sb.setColor(this.color);
            if (icons[this.count] != null) {
                sb.draw(icons[this.count], this.cX - 80.0f, this.cY,
                        80.0f, 0.0f, 160, 320,
                        this.scale * Settings.scale, this.scale * Settings.scale,
                        -this.index * 360.0f / 7.0f,
                        0, 0, 160, 320, false, false);
            }


            if (this.lightTimer < 0.0f) {
                this.color.a = (0.1f + this.lightTimer) * 10.0f;
                this.lightTimer += Gdx.graphics.getDeltaTime();
            } else {
                if (refundCharge == 1) {
                    this.color.a = ((float) Math.cos(this.lightTimer) + 1.0f) * 0.4f + 0.2f;
                } else {
                    this.color.a = ((float) Math.cos(this.lightTimer) + 1.0f) * 0.25f + 0.5f;
                }
            }

            sb.setColor(this.color);
            if (this.count < 4)
                if (icons[this.count + 1] != null) {
                    sb.draw(icons[this.count + 1], this.cX - 80.0f, this.cY,
                            80.0f, 0.0f, 160, 320,
                            this.scale * Settings.scale, this.scale * Settings.scale,
                            -this.index * 360.0f / 7.0f,
                            0, 0, 160, 320, false, false);
                }
            return;
        }

        if (icons[this.count] != null) {
            sb.draw(icons[this.count], this.cX - 80.0f, this.cY,
                    80.0f, 0.0f, 160, 320,
                    this.scale * Settings.scale, this.scale * Settings.scale,
                    -this.index * 360.0f / 7.0f,
                    0, 0, 160, 320, false, false);
        }
    }


}
