package com.hekotech.flyingbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

import sun.security.provider.SHA;

public class FlyingBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img,bird;
	float bw,bh,sw,sh,bx,by;
	float velocity = 0.0f;
	float gravity = 0.6f;
	int nbees = 3;
	float beesx[] = new float[nbees];
	float beesy[][] = new float[3][nbees];
	ShapeRenderer sr;
	BitmapFont font;
	BitmapFont font1;
	Sound sound;
	int state=0;
	Texture bee1,bee2,bee3;
	float beex,bee1y,bee2y,bee3y;
	Circle c_bird,c_bee1[],c_bee2[],c_bee3[];
	int score=0;
	Boolean flag=true;
	Boolean flag1=true;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("storyboard6.png");
		bird = new Texture("frame-1.png");
		bee1 = new Texture("1.png");
		bee2 = new Texture("2.png");
		bee3 = new Texture("1.png");
		bw=Gdx.graphics.getWidth()/13;
		bh=Gdx.graphics.getHeight()/11;
		sw=Gdx.graphics.getWidth();
		sh=Gdx.graphics.getHeight();
		bx=Gdx.graphics.getWidth()/4;
		by=Gdx.graphics.getHeight();

		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(8);

		font1 = new BitmapFont();
		font1.setColor(Color.CYAN);
		font1.getData().setScale(10);

		c_bird = new Circle();
		c_bee1 = new Circle[nbees];
		c_bee2 = new Circle[nbees];
		c_bee3 = new Circle[nbees];

		sound = Gdx.audio.newSound(Gdx.files.internal("lose.ogg"));


		//sr = new ShapeRenderer();


		for(int i = 0 ; i<nbees ; i++){
			beesx[i] = sw+i*sw/2;
			Random r1 = new Random();
			Random r2 = new Random();
			Random r3 = new Random();

			beesy[0][i] = r1.nextFloat() * sh;
			beesy[1][i] = r2.nextFloat() * sh;
			beesy[2][i] = r3.nextFloat() * sh;

			c_bee1[i] = new Circle();
			c_bee2[i] = new Circle();
			c_bee3[i] = new Circle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(img,0,0,sw,sh);
		batch.draw(bird,bx,by,bw,bh);
		if(state==1){
			if(Gdx.input.justTouched()){
				velocity = -15;
			}
			flag1=true;

			for(int i=0;i<nbees;i++){
				if(beesx[i]<0){
					flag=true;
					beesx[i] = nbees*sw/2;
					Random r1 = new Random();
					Random r2 = new Random();
					Random r3 = new Random();

					beesy[0][i] = r1.nextFloat() * sh;
					beesy[1][i] = r2.nextFloat() * sh;
					beesy[2][i] = r3.nextFloat() * sh;
				}
				font.draw(batch,String.valueOf(score),sw-bw,bh);
				if(bx>beesx[i] && flag){
					score++;
					System.out.println(score);
					flag=false;
				}
				beesx[i] = beesx[i] - 5;
				batch.draw(bee1,beesx[i],beesy[0][i],bw,bh);
				batch.draw(bee2,beesx[i],beesy[1][i],bw,bh);
				batch.draw(bee3,beesx[i],beesy[2][i],bw,bh);
			}
			if(by < bh){
				by = sw/3;
				velocity = 0;
				state=2;
			}
			else {
				velocity = velocity + gravity;
				by = by-velocity;
			}
		}
		else if(state==2){
			font1.draw(batch,"You lost! Tap to screen to try again!",0,sh/2);

			if(flag1) {
				sound.play();
				flag1=false;
			}
			if(Gdx.input.justTouched()){
				bx=Gdx.graphics.getWidth()/4;
				by=Gdx.graphics.getHeight();
				score=0;

				for(int i = 0 ; i<nbees ; i++){
					beesx[i] = sw+i*sw/2;
					Random r1 = new Random();
					Random r2 = new Random();
					Random r3 = new Random();

					beesy[0][i] = r1.nextFloat() * sh;
					beesy[1][i] = r2.nextFloat() * sh;
					beesy[2][i] = r3.nextFloat() * sh;

					c_bee1[i] = new Circle();
					c_bee2[i] = new Circle();
					c_bee3[i] = new Circle();
				}
				state = 1;
			}
		}
		else if(state==0){
			font1.draw(batch,"Tap to screen to start!",0,sh/2);
			if(Gdx.input.justTouched()){
				state=1;
			}
		}

		c_bird.set(bx+bw/2,by+bh/2,bw/2);
		//sr.begin(ShapeRenderer.ShapeType.Filled);
		for(int i=0;i<nbees;i++){
			/*sr.setColor(Color.BLUE);
			sr.circle(beesx[i]+bw/2,beesy[0][i]+bh/2,bw/2);
			sr.circle(beesx[i]+bw/2,beesy[1][i]+bh/2,bw/2);
			sr.circle(beesx[i]+bw/2,beesy[2][i]+bh/2,bw/2);
			sr.circle(bx+bw/2,by+bh/2,bw/2);*/
			c_bee1[i].set(beesx[i]+bw/2,beesy[0][i]+bh/2,bw/2);
			c_bee2[i].set(beesx[i]+bw/2,beesy[1][i]+bh/2,bw/2);
			c_bee3[i].set(beesx[i]+bw/2,beesy[2][i]+bh/2,bw/2);
			if(Intersector.overlaps(c_bird,c_bee1[i]) || Intersector.overlaps(c_bird,c_bee2[i]) || Intersector.overlaps(c_bird,c_bee3[i])){
				state = 2;
			}

		}
		//sr.end();

		batch.end();


	}
	
	@Override
	public void dispose () {

	}
}
