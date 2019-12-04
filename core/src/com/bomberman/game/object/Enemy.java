package com.bomberman.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {

    public boolean move_left = true;
    float speed ;
    Sprite sprite;
    Texture texture;
    float x,y,width,height;
    public boolean move_Left(boolean move_left){
        return this.move_left = move_left;
    }

    /**
     * crate sprite that has texture from hero.png
     * and set size by 64,64 and recive position by x,y
     */
    public Enemy(float x,float y) {
        //Constructor

        texture = new Texture(Gdx.files.internal("enemy.png"));
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(64, 64);
        speed = 250f;
    }

    /**
     * set this sprite to x,y position
     */
    public void setPosition(float x,float y) {
        //Set position of object
        sprite.setPosition(x, y);
    }


    /**
     * make sprite move
     */
    public void Move(float delta){
        if(move_Left(move_left) == true){
            sprite.setX(sprite.getX()+(speed*delta));
            if(sprite.getX() > Gdx.graphics.getWidth() ){
                //move_left = false;
                this.move_Left(false);
            }
        }else if (move_Left(move_left) == false){
            sprite.setX(sprite.getX()-(speed*delta));
            if(sprite.getX() < 0 ){
                //move_left = true;
                this.move_Left(true);
            }
        }
    }


    /**
     * @return rectangle of sprite for check collision
     */
    public Rectangle rectangle(){
        Rectangle r = new Rectangle(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        return  r;
    }

    public float getY() {
        y = sprite.getY();
        return y;
    }
    public float getX() {
        x = sprite.getX();
        return x;
    }

    public float getHeight() {
        float height = sprite.getHeight();
        return height;
    }

    public float getWidth() {
        float width = sprite.getWidth();
        return width;
    }


    /**
     * draw sprite
     */
    public void Draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

}